package com;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.jna.*;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinUser;

public class Main {
    public static final int WM_COPYDATA = 0x004A;
    public static String windowTitle;
    // 使用唯一類名避免衝突（加入時間戳）
    public static String windowClass;

    // WM_COPYDATA 使用的數據結構
    public static class COPYDATASTRUCT extends Structure {
        public BaseTSD.ULONG_PTR dwData;
        public NativeLong cbData;
        public Pointer lpData;
        public COPYDATASTRUCT() {}
        public COPYDATASTRUCT(Pointer p) {
            super(p);
            read();
        }
        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("dwData", "cbData", "lpData");
        }
    }

    private JTextArea textArea;
    private JFrame frame;
    private WinUser.WindowProc wndProcRef;
    private volatile Thread nativeThread;
    private volatile boolean running = false;
    private HWND hwnd; // 保存窗口句柄用於清理
    private HMODULE hInst; // 保存模塊句柄用於清理

    // 健康監控：追蹤最後收到消息的時間
    private final AtomicLong lastMsgTime = new AtomicLong(System.currentTimeMillis());

    // 創建Swing介面
    private void createAndShowGUI() {
        frame = new JFrame(windowTitle);
        textArea = new JTextArea(18, 50);
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(textArea);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // 啟動原生消息接收線程
    private void startNativeReceiver() {
        running = true;
        nativeThread = new Thread(() -> {
            //System.out.println("啟動 startNativeReceiver, Thread active count = " + Thread.activeCount() );
            windowClass = "GUIReceiverClass_" + System.currentTimeMillis();
            try {
                // 獲取當前模塊句柄
                hInst = Kernel32.INSTANCE.GetModuleHandle(null);

                // 定義窗口處理函數
                wndProcRef = new WinUser.WindowProc() {
                    @Override
                    public LRESULT callback(HWND hwnd, int uMsg, WPARAM wParam, LPARAM lParam) {
                        try {
                            if (uMsg == WM_COPYDATA) {
                                // 更新最後消息時間
                                lastMsgTime.set(System.currentTimeMillis());
                                COPYDATASTRUCT cds = new COPYDATASTRUCT(new Pointer(lParam.longValue()));
                                String msg = cds.lpData.getString(0);
                                
                                // 在Swing線程更新UI
                                SwingUtilities.invokeLater(() -> {
                                    String oldText = textArea.getText();
                                    textArea.setText("接收:" + msg + "\n" + oldText);
                                    //System.out.println("接收:" + msg);
                                    
                                    Pattern pattern = Pattern.compile("PARAM=(\\d+)\\.");
                                    Matcher matcher = pattern.matcher(msg);
                                    String symbol;
                                    if (matcher.find()) {
                                    	oldText = textArea.getText();
                                    	symbol = matcher.group(1);
                                        textArea.setText("代號:" + symbol + "\n" + oldText);
                                        try {
                                        	for(int i=1;i<10;i++) {
    	                                    	int no = i;
    	                                    	final String urlString = (PropertiesConfig.PROP.getProperty("client.url" + no));
    	                                    	if(urlString == null || urlString.isEmpty()) {
    	                                    		break;
    	                                    	}
    	                                    	final String urlPath = urlString + "?symbol=" + symbol;
    	                                        oldText = textArea.getText();
    	                                        textArea.setText("傳送:" + urlPath + "\n" + oldText);
    	                                        Scanner scanner = null;
    	                                        try {
    	        		                            URL url = new URL(urlPath);
    	        		                            scanner = new Scanner(url.openStream());
    	        		                            String result = scanner.useDelimiter("\\A").next();
    	        		                            textArea.setText("成功:" + urlPath + " (" + result + ")\n" + textArea.getText());
    	        		                            
    	        		                        } catch (Exception e) {
    	        		                        	textArea.setText("失敗:" + urlPath + " (" + e.getMessage() + ")\n" + textArea.getText());
    	        		                            //e.printStackTrace();
    	        		                        } finally {
    												if(scanner != null) {
    													scanner.close();
    												}
    											}
                                        	}
                                            
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            oldText = textArea.getText();
                                            textArea.setText("失敗:" + e.getMessage() + "\n" + oldText);
                                        }
                                    } else {
                                    	oldText = textArea.getText();
                                    	textArea.setText("格式錯誤!未找到股票代號\n" + oldText);
                                    }
                                    
                                    
                                    textArea.setCaretPosition(0);
                                });
                                return new LRESULT(1); // 表示已處理
                            }
                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                        // 默認消息處理
                        return User32.INSTANCE.DefWindowProc(hwnd, uMsg, wParam, lParam);
                    }
                };

                // 註冊窗口類
                WinUser.WNDCLASSEX wndClass = new WinUser.WNDCLASSEX();
                wndClass.cbSize = wndClass.size();
                wndClass.lpfnWndProc = wndProcRef;
                wndClass.hInstance = hInst;
                wndClass.lpszClassName = windowClass;
                User32.INSTANCE.RegisterClassEx(wndClass);

                // 創建隱藏窗口
                hwnd = User32.INSTANCE.CreateWindowEx(
                        0, windowClass, windowTitle,
                        0, 0, 0, 0, 0,
                        null, null, hInst, null);

                if (hwnd == null) {
                    System.err.println("隱藏視窗建立失敗");
                    return;
                }

                //System.out.println("WM_COPYDATA Swing Receiver ready. 視窗名: " + windowTitle);

                // 消息循環
                WinUser.MSG msg = new WinUser.MSG();
                while (running && User32.INSTANCE.GetMessage(msg, null, 0, 0) != 0) {
                    User32.INSTANCE.TranslateMessage(msg);
                    User32.INSTANCE.DispatchMessage(msg);
                }
                
                // 線程結束前清理資源
                //System.out.println("執行清理程序...");
                if (hwnd != null) {
                    User32.INSTANCE.DestroyWindow(hwnd);
                }
                User32.INSTANCE.UnregisterClass(windowClass, hInst);
                //System.out.println("Thread 已結束");
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }, "WM_COPYDATA-Receiver");
        nativeThread.setDaemon(true);
        nativeThread.start();
    }

    // 安全停止接收線程
    private void stopNativeReceiver() {
        running = false; // 設置停止標誌
        
        // 發送WM_QUIT消息終止消息循環
        if (hwnd != null) {
            User32.INSTANCE.PostMessage(hwnd, WinUser.WM_QUIT, null, null);
        }
        
        // 等待線程終止
        if (nativeThread != null) {
            try {
                nativeThread.join(2000); // 等待2秒
                if (nativeThread.isAlive()) {
                    //System.err.println("警告：接收線程未正常終止，將強制中斷");
                    nativeThread.interrupt();
                    nativeThread.join(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // 健康監控線程
    private void startHealthMonitor() {
        Thread monitorThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000); // 每3秒檢查一次
                } catch (InterruptedException e) {
                    break;
                }
                
                // 檢查線程狀態和消息時間
                boolean threadAlive = nativeThread != null && nativeThread.isAlive();
                long now = System.currentTimeMillis();
                long last = lastMsgTime.get();
                
                // 需要重啟的條件：線程死亡或5秒無消息
                if (!threadAlive || (threadAlive && now - last > 5000)) {
                    //System.err.println("偵測到異常，重啟接收線程...");
                    
                    // 先停止舊線程
                    stopNativeReceiver();
                    
                    // 啟動新線程
                    startNativeReceiver();
                }
            }
        }, "HealthMonitor");
        
        monitorThread.setDaemon(true);
        monitorThread.start();
    }

    public static void main(String[] args) {
    	PropertiesConfig.makePropertiesConfig();
    	windowTitle = PropertiesConfig.PROP.getProperty("window.name", "FAPFrameMgr");
        Main app = new Main();
        SwingUtilities.invokeLater(app::createAndShowGUI);
        app.startNativeReceiver();
        app.startHealthMonitor();
    }
}
