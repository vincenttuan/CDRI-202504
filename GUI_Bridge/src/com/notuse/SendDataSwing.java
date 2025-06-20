package com.notuse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.sun.jna.*;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.WinDef.*;

public class SendDataSwing {
    public static final int WM_COPYDATA = 0x004A;

    public static class COPYDATASTRUCT extends Structure {
        public Pointer dwData;
        public int cbData;
        public Pointer lpData;
        public COPYDATASTRUCT() {}
        @Override
        protected java.util.List<String> getFieldOrder() {
            return java.util.Arrays.asList("dwData", "cbData", "lpData");
        }
    }

    public static boolean sendData(String destWindowTitle, String msg) {
        HWND targetHwnd = User32.INSTANCE.FindWindow(null, destWindowTitle);
        if (targetHwnd == null) {
            JOptionPane.showMessageDialog(null, "找不到視窗: " + destWindowTitle, "錯誤", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        byte[] msgBytes = (msg + "\0").getBytes();
        Memory memory = new Memory(msgBytes.length);
        memory.write(0, msgBytes, 0, msgBytes.length);

        COPYDATASTRUCT cds = new COPYDATASTRUCT();
        cds.dwData = Pointer.NULL;
        cds.cbData = msgBytes.length;
        cds.lpData = memory;
        cds.write();

        WinDef.LRESULT lResult = User32.INSTANCE.SendMessage(
            targetHwnd,
            WM_COPYDATA,
            new WinDef.WPARAM(0),
            new WinDef.LPARAM(Pointer.nativeValue(cds.getPointer()))
        );
        int result = lResult.intValue();
        return result != 0;
    }

    public static void main2(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("WM_COPYDATA 傳送端");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel lblTitle = new JLabel("目標視窗標題:");
            JTextField txtTitle = new JTextField("GUIReceiver", 20);

            JLabel lblMsg = new JLabel("訊息內容:");
            JTextField txtMsg = new JTextField("1101", 20);

            JButton btnSend = new JButton("發送");

            gbc.gridx = 0; gbc.gridy = 0;
            panel.add(lblTitle, gbc);
            gbc.gridx = 1; gbc.gridy = 0;
            panel.add(txtTitle, gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            panel.add(lblMsg, gbc);
            gbc.gridx = 1; gbc.gridy = 1;
            panel.add(txtMsg, gbc);

            gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
            panel.add(btnSend, gbc);

            frame.setContentPane(panel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            btnSend.addActionListener(e -> {
                String windowTitle = txtTitle.getText().trim();
                String message = txtMsg.getText().trim();
                boolean ok = sendData(windowTitle, message);
                JOptionPane.showMessageDialog(frame, ok ? "訊息已送出！" : "發送失敗！");
            });
        });
    }
}
