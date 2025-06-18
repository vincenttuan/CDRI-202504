package com;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Scanner;

import com.sun.jna.*;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public class CopyDataReceiverSwing {
    public static final int WM_COPYDATA = 0x004A;
    public static final String windowTitle = "GUIReceiver";
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
                    if (uMsg == WM_COPYDATA) {
                        // 取得 COPYDATASTRUCT
                        COPYDATASTRUCT cds = new COPYDATASTRUCT(new Pointer(lParam.longValue()));
                        String msg = cds.lpData.getString(0);

                        // 更新 Swing UI（新資料在最上面）
                        SwingUtilities.invokeLater(() -> {
                            String oldText = textArea.getText();
                            textArea.setText("接收:" + msg + "\n" + oldText);
                            System.out.println("接收:" + msg);
                            try {
                            	String urlPath = "http://localhost:55001/?symbol=" + msg;
                            	oldText = textArea.getText();
                                textArea.setText("傳送:" + urlPath + "\n" + oldText);
                                URL url = new URL(urlPath);
                                String respText = new Scanner(url.openStream()).useDelimiter("\\A").next();
                                System.out.println("GUI 回復訊息: " + respText);
                                oldText = textArea.getText();
                                textArea.setText("回應:" + respText + "\n" + oldText);
                                
            				} catch (Exception e) {
            					e.printStackTrace();
            					oldText = textArea.getText();
                                textArea.setText("失敗:" + e.getMessage() + "\n" + oldText);
            				}
                            textArea.setCaretPosition(0);

                        });
                        
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
        CopyDataReceiverSwing app = new CopyDataReceiverSwing();
        SwingUtilities.invokeLater(app::createAndShowGUI);
        app.startNativeReceiver();
    }
}
