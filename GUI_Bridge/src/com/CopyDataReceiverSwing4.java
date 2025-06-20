package com;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

import com.sun.jna.*;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinUser;

public class CopyDataReceiverSwing4 {
    public static final int WM_COPYDATA = 0x004A;
    public static final String windowTitle = "GUIReceiver";
    public static final String windowClass = "GUIReceiverClass_" + System.currentTimeMillis();

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

    // 健康監控
    private final AtomicLong lastMsgTime = new AtomicLong(System.currentTimeMillis());

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

    private void startNativeReceiver() {
        running = true;
        nativeThread = new Thread(() -> {
        	System.out.println("啟動 startNativeReceiver");
            try {
                HMODULE hInst = Kernel32.INSTANCE.GetModuleHandle(null);

                wndProcRef = new WinUser.WindowProc() {
                    @Override
                    public LRESULT callback(HWND hwnd, int uMsg, WPARAM wParam, LPARAM lParam) {
                        try {
                            if (uMsg == WM_COPYDATA) {
                                lastMsgTime.set(System.currentTimeMillis());
                                COPYDATASTRUCT cds = new COPYDATASTRUCT(new Pointer(lParam.longValue()));
                                String msg = cds.lpData.getString(0);
                                SwingUtilities.invokeLater(() -> {
                                    String oldText = textArea.getText();
                                    textArea.setText("接收:" + msg + "\n" + oldText);
                                    System.out.println("接收:" + msg);
                                    try {
                                        String urlPath = "http://localhost:55001/?symbol=" + msg;
                                        oldText = textArea.getText();
                                        textArea.setText("傳送:" + urlPath + "\n" + oldText);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        oldText = textArea.getText();
                                        textArea.setText("失敗:" + e.getMessage() + "\n" + oldText);
                                    }
                                    textArea.setCaretPosition(0);
                                });
                                return new LRESULT(1);
                            }
                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                        return User32.INSTANCE.DefWindowProc(hwnd, uMsg, wParam, lParam);
                    }
                };

                WinUser.WNDCLASSEX wndClass = new WinUser.WNDCLASSEX();
                wndClass.cbSize = wndClass.size();
                wndClass.lpfnWndProc = wndProcRef;
                wndClass.hInstance = hInst;
                wndClass.lpszClassName = windowClass;
                User32.INSTANCE.RegisterClassEx(wndClass);

                HWND hwnd = User32.INSTANCE.CreateWindowEx(
                        0, windowClass, windowTitle,
                        0, 0, 0, 0, 0,
                        null, null, hInst, null);

                if (hwnd == null) {
                    System.err.println("隱藏視窗建立失敗");
                    return;
                }

                System.out.println("WM_COPYDATA Swing Receiver ready. 視窗名: " + windowTitle);

                WinUser.MSG msg = new WinUser.MSG();
                while (running && User32.INSTANCE.GetMessage(msg, null, 0, 0) != 0) {
                    User32.INSTANCE.TranslateMessage(msg);
                    User32.INSTANCE.DispatchMessage(msg);
                }
                System.out.println("Thread 已結束");
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }, "WM_COPYDATA-Receiver");
        nativeThread.setDaemon(true);
        nativeThread.start();
    }

    // 健康監控 thread
    private void startHealthMonitor() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    break;
                }
                boolean threadAlive = nativeThread != null && nativeThread.isAlive();
                long now = System.currentTimeMillis();
                long last = lastMsgTime.get();
                if (!threadAlive || now - last > 5000) { // 5秒沒收到訊息或 thread 死掉
                    System.err.println("Receiver thread 死掉或阻塞，重啟...");
                    running = false;
                    try {
                        if (nativeThread != null) nativeThread.join(2000);
                    } catch (InterruptedException e) {}
                    startNativeReceiver();
                }
            }
        }, "HealthMonitor").start();
    }

    public static void main(String[] args) {
        CopyDataReceiverSwing4 app = new CopyDataReceiverSwing4();
        SwingUtilities.invokeLater(app::createAndShowGUI);
        app.startNativeReceiver();
        app.startHealthMonitor();
    }
}
