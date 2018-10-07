package gui.guiControlls;

import javax.swing.*;

public class SettingsReturnObject {

    private JPanel pane = new JPanel();
    private JTextField textField = new JTextField();
    private JButton btn1 = new JButton();
    private JButton btn2 = new JButton();
    private JButton btn3 = new JButton();
    private JCheckBox checkBox = new JCheckBox();

    public JPanel getPane() {
        return pane;
    }

    public JButton getBtn3() {
        return btn3;
    }

    public JCheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(JCheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public void setBtn3(JButton btn3) {
        this.btn3 = btn3;
    }

    public void setPane(JPanel pane) {
        this.pane = pane;
    }

    public JTextField getTextField() {
        return textField;
    }

    public void setTextField(JTextField textField) {
        this.textField = textField;
    }

    public JButton getBtn1() {
        return btn1;
    }

    public void setBtn1(JButton btn1) {
        this.btn1 = btn1;
    }

    public JButton getBtn2() {
        return btn2;
    }

    public void setBtn2(JButton btn2) {
        this.btn2 = btn2;
    }
}
