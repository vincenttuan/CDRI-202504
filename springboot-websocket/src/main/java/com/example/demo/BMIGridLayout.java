package com.example.demo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BMIGridLayout extends JFrame {
    private JTextField weightField;
    private JTextField heightField;
    private JLabel resultLabel;

    public BMIGridLayout() {
        setTitle("BMI 計算器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(340, 200);
        setLocationRelativeTo(null);

        // 建立主面板，設定 padding
        JPanel mainPanel = new JPanel(new GridLayout(4, 2, 10, 10)); // (int rows, int columns, int horizontalGap, int verticalGap)
        mainPanel.setBorder(new EmptyBorder(15, 20, 15, 20)); // 上、左、下、右 padding

        // 第一列
        mainPanel.add(new JLabel("體重（公斤）："));
        weightField = new JTextField(8);
        mainPanel.add(weightField);

        // 第二列
        mainPanel.add(new JLabel("身高（公尺）："));
        heightField = new JTextField(8);
        mainPanel.add(heightField);

        // 第三列
        JButton calcButton = new JButton("計算");
        mainPanel.add(calcButton);

        resultLabel = new JLabel("BMI：");
        mainPanel.add(resultLabel);

        // 第四列（空白，讓版面更美觀）
        mainPanel.add(new JLabel(""));
        mainPanel.add(new JLabel(""));

        // 加入主面板到 JFrame
        setContentPane(mainPanel);

        calcButton.addActionListener(e -> calculateBMI());
    }

    private void calculateBMI() {
        try {
            double weight = Double.parseDouble(weightField.getText());
            double height = Double.parseDouble(heightField.getText());
            if (height <= 0) throw new NumberFormatException();
            double bmi = weight / (height * height);
            String status;
            if (bmi < 18.5) status = "體重過輕";
            else if (bmi < 24) status = "正常";
            else if (bmi < 27) status = "過重";
            else status = "肥胖";
            resultLabel.setText(String.format("BMI：%.2f，%s", bmi, status));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "請輸入正確的數字", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BMIGridLayout().setVisible(true));
    }
}
