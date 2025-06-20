package com;

import javax.swing.*;

import org.w3c.dom.ls.LSOutput;

import java.awt.*;
import java.net.URI;
import java.net.http.*;
import java.util.*;
import com.sun.jna.*;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinUser;

public class CopyDataReceiverSwing2 {
    private static final int WM_COPYDATA = 0x004A;
    private static String windowTitle;
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(java.time.Duration.ofSeconds(5))
            .build();

    // JNA 結構體
    public static class COPYDATASTRUCT extends Structure {
        public Pointer dwData;
        public int cbData;
        public Pointer lpData;

        public COPYDATASTRUCT() {}

        public COPYDATASTRUCT(Pointer p) {
            super(p);
            read();
        }

        @Override
        protected java.util.List<String> getFieldOrder() {
            return java.util.Arrays.asList("dwData", "cbData", "lpData");
        }
    }

    // Swing UI
    private JTextArea textArea;
    private JFrame frame;

    // 建立 GUI
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

    // 啟動 native WM_COPYDATA 接收視窗
    private void startNativeReceiver() {
        new Thread(() -> {
            final String windowClass = "SwingCopyDataReceiver";
            HMODULE hInst = Kernel32.INSTANCE.GetModuleHandle(null);

            // 註冊 Win32 視窗類別
            WinUser.WNDCLASSEX wndClass = new WinUser.WNDCLASSEX();
            wndClass.cbSize = wndClass.size();
            wndClass.lpfnWndProc = new WinUser.WindowProc() {
                @Override
                public LRESULT callback(HWND hwnd, int uMsg, WPARAM wParam, LPARAM lParam) {
                	System.out.println(uMsg);
                	SwingUtilities.invokeLater(() -> {
                        String oldText = textArea.getText();
                        textArea.setText("uMsg: " + uMsg + "\n" + oldText);
                        textArea.setCaretPosition(0);
                    });
                	if (uMsg == WM_COPYDATA) {
                        COPYDATASTRUCT cds = new COPYDATASTRUCT(new Pointer(lParam.longValue()));
                        String msg = cds.lpData.getString(0);
                        
                        // 先即時更新 UI（收到的訊息）
                        SwingUtilities.invokeLater(() -> {
                            String oldText = textArea.getText();
                            textArea.setText("接收: " + msg + "\n" + oldText);
                            textArea.setCaretPosition(0);
                        });
                        
                        // 依序取出 client.url1~client.url9，有值就送
                        ArrayList<String> urlList = new ArrayList<>();
                        for (int i = 1; i <= 9; i++) {
                            String url = PropertiesConfig.PROP.getProperty("client.url" + i, "").trim();
                            if (!url.isEmpty()) {
                                urlList.add(url);
                            }
                        }
                        System.out.println(urlList);
                        // 每個 URL 都同步送出
                        for (String baseUrl : urlList) {
                            String fullUrl = baseUrl + (baseUrl.contains("?") ? "&" : "?") + "symbol=" + msg;
                            SwingUtilities.invokeLater(() -> {
                                String oldText = textArea.getText();
                                textArea.setText("傳送: " + fullUrl + "\n" + oldText);
                                textArea.setCaretPosition(0);
                            });
                            /*
                            HttpRequest request = HttpRequest.newBuilder()
                                    .uri(URI.create(fullUrl))
                                    .timeout(java.time.Duration.ofSeconds(10))
                                    .GET()
                                    .build();
                            try {
                                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                                String respText = response.body();
                                SwingUtilities.invokeLater(() -> {
                                    String oldText = textArea.getText();
                                    textArea.setText("回應(" + baseUrl + "): " + respText + "\n" + oldText);
                                    textArea.setCaretPosition(0);
                                });
                            } catch (Exception e) {
                                SwingUtilities.invokeLater(() -> {
                                    String oldText = textArea.getText();
                                    textArea.setText("失敗(" + baseUrl + "): " + e.getMessage() + "\n" + oldText);
                                    textArea.setCaretPosition(0);
                                });
                            }
                            */
                        }
                        
                        return new LRESULT(1);
                    }
                    return User32.INSTANCE.DefWindowProc(hwnd, uMsg, wParam, lParam);
                }
            };
            wndClass.hInstance = hInst;
            wndClass.lpszClassName = windowClass;
            User32.INSTANCE.RegisterClassEx(wndClass);

            // 建立隱藏視窗（只用來收消息，不顯示）
            HWND hwnd = User32.INSTANCE.CreateWindowEx(
                    0, windowClass, windowTitle,
                    0, 0, 0, 0, 0,
                    null, null, hInst, null);

            if (hwnd == null) {
                System.err.println("隱藏視窗建立失敗");
                return;
            }

            System.out.println("WM_COPYDATA Swing Receiver ready. 視窗名: " + windowTitle);

            // 消息循環
            WinUser.MSG msg = new WinUser.MSG();
            while (User32.INSTANCE.GetMessage(msg, null, 0, 0) != 0) {
                User32.INSTANCE.TranslateMessage(msg);
                User32.INSTANCE.DispatchMessage(msg);
            }
        }).start();
    }

    public static void main(String[] args) {
        PropertiesConfig.makePropertiesConfig();
        windowTitle = PropertiesConfig.PROP.getProperty("window.name");
        CopyDataReceiverSwing2 app = new CopyDataReceiverSwing2();
        SwingUtilities.invokeLater(app::createAndShowGUI);
        app.startNativeReceiver();
    }
}
