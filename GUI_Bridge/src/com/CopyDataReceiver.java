package com;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.sun.jna.*;
import com.sun.jna.platform.win32.*;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public class CopyDataReceiver {
    public static final int WM_COPYDATA = 0x004A;
    public static final int WM_SETTEXT = 0x000C; // 加上這一行
    public static final int ES_AUTOHSCROLL = 0x0080;
    
    public interface MyUser32 extends StdCallLibrary {
        MyUser32 INSTANCE = Native.load("user32", MyUser32.class, W32APIOptions.DEFAULT_OPTIONS);
        LRESULT SendMessage(HWND hWnd, int Msg, WPARAM wParam, String lParam);
    }
    
    // 靜態變數保存 Edit 控制項的 HWND
    public static HWND hEdit = null;

    public static class COPYDATASTRUCT extends Structure {
        public Pointer dwData;
        public int cbData;
        public Pointer lpData;
        public COPYDATASTRUCT() {}
        public COPYDATASTRUCT(long peer) {
            super(new Pointer(peer));
            read();
        }
        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("dwData", "cbData", "lpData");
        }
    }

    public static class WindowProc implements WinUser.WindowProc {
        @Override
        public WinDef.LRESULT callback(WinDef.HWND hwnd, int uMsg, WinDef.WPARAM wParam, WinDef.LPARAM lParam) {
            if (uMsg == WM_COPYDATA) {
                COPYDATASTRUCT cds = new COPYDATASTRUCT(lParam.longValue());
                String msg = cds.lpData.getString(0);
                // 顯示收到的資訊在 Edit 控制項
                MyUser32.INSTANCE.SendMessage(hEdit, WM_SETTEXT, new WinDef.WPARAM(0), msg);
                System.out.println("收到訊息: " + msg);
                // 發送一個 HTTP 到 http://localhost:80?
                try {
                	String urlPath = "http://localhost:55001/?symbol=" + msg;
                    URL url = new URL(urlPath);
                    String respText = new Scanner(url.openStream()).useDelimiter("\\A").next();
                    System.out.println("GUI回復訊息: " + respText);
				} catch (Exception e) {
					e.printStackTrace();
				}
                
                
                return new WinDef.LRESULT(1);
            } else if (uMsg == WinUser.WM_CLOSE) {
                User32.INSTANCE.DestroyWindow(hwnd);
                return new WinDef.LRESULT(0);
            } else if (uMsg == WinUser.WM_DESTROY) {
                User32.INSTANCE.PostQuitMessage(0);
                return new WinDef.LRESULT(0);
            }
            return User32.INSTANCE.DefWindowProc(hwnd, uMsg, wParam, lParam);
        }
    }

    public static void main(String[] args) {
        final String windowClass = "MyJavaReceiver";
        WinDef.HMODULE hInst = Kernel32.INSTANCE.GetModuleHandle(null);

        // 註冊窗口類
        WinUser.WNDCLASSEX wndClass = new WinUser.WNDCLASSEX();
        wndClass.cbSize = wndClass.size();
        wndClass.lpfnWndProc = new WindowProc();
        wndClass.hInstance = hInst;
        wndClass.lpszClassName = windowClass;
        User32.INSTANCE.RegisterClassEx(wndClass);

        // 建立有標題欄和 X 的視窗
        int style = WinUser.WS_OVERLAPPEDWINDOW;
        HWND hwnd = User32.INSTANCE.CreateWindowEx(
            0, windowClass, "GUIReceiver",
            style, 100, 100, 400, 200,
            null, null, hInst, null);

        if (hwnd == null) {
            System.err.println("視窗建立失敗");
            return;
        }

        // 建立單行 EDIT 控制項
        hEdit = User32.INSTANCE.CreateWindowEx(
            0, "EDIT", "",
            WinUser.WS_CHILD | WinUser.WS_VISIBLE | ES_AUTOHSCROLL,
            20, 80, 340, 30, hwnd, null, hInst, null
        );

        // 顯示並更新視窗
        User32.INSTANCE.ShowWindow(hwnd, WinUser.SW_SHOWNORMAL);
        User32.INSTANCE.UpdateWindow(hwnd);

        System.out.println("Java 視窗建立完成, 標題: GUIReceiver");

        // 消息循環
        WinUser.MSG msg = new WinUser.MSG();
        while (User32.INSTANCE.GetMessage(msg, null, 0, 0) != 0) {
            User32.INSTANCE.TranslateMessage(msg);
            User32.INSTANCE.DispatchMessage(msg);
        }
        System.out.println("程式結束");
    }
}
