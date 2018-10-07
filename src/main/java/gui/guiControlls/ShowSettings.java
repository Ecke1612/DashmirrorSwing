package gui.guiControlls;


import javax.swing.*;
import java.awt.*;

public class ShowSettings {

    public ShowSettings() {

    }

    public SettingsReturnObject createInputRow(String labelname, String preInput) {
        JPanel hbox = new JPanel();
        hbox.setLayout(new BoxLayout(hbox, BoxLayout.X_AXIS));
        JLabel label_city = new JLabel(labelname);
        JTextField input = new JTextField(preInput);
        hbox.add(label_city);
        hbox.add(input);
        SettingsReturnObject obj = new SettingsReturnObject();
        obj.setPane(hbox);
        obj.setTextField(input);
        return obj;
    }

    public SettingsReturnObject getConfirmButton() {
        JPanel hbox = new JPanel();
        hbox.setLayout(new BoxLayout(hbox, BoxLayout.X_AXIS));
        JButton abort = new JButton("Abbrechen");
        JButton save = new JButton("speichern");
        JButton update = new JButton("update now");
        hbox.add(abort);
        hbox.add(save);
        hbox.add(Box.createRigidArea(new Dimension(5,0)));
        hbox.add(update);
        SettingsReturnObject obj = new SettingsReturnObject();
        obj.setPane(hbox);
        obj.setBtn1(abort);
        obj.setBtn2(save);
        obj.setBtn3(update);
        return obj;
    }

    public SettingsReturnObject getCheckBoxRow(String name, Boolean checked) {
        JPanel hbox = new JPanel();
        hbox.setLayout(new BoxLayout(hbox, BoxLayout.X_AXIS));
        JCheckBox ch = new JCheckBox(name);
        if(checked) ch.setSelected(true);
        else ch.setSelected(false);
        hbox.add(ch);
        SettingsReturnObject obj = new SettingsReturnObject();
        obj.setPane(hbox);
        obj.setCheckBox(ch);
        return obj;
    }


}
