package gui.api.clock;

import data_structure.FileHandler;
import gui.Gui_Holder;
import gui.ParentController;
import gui.api.StoreObject;
import gui.guiControlls.SettingsReturnObject;
import gui.guiControlls.ShowSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;


public class ClockScreen extends ParentController {

    private ClockStoreObject storeObj = new ClockStoreObject();

    private JLabel label_hour = new JLabel();
    private JLabel label_min = new JLabel();
    private JLabel label_sec = new JLabel();
    private JLabel label_sep = new JLabel(":");

    public ClockScreen() {
        WIDTH = 200;
        HEIGHT = 100;
        storePath = "data/store/clock_";
        name = "clock";
        setTimer(0,100);
    }

    @Override
    public void update() {
        LocalTime t = LocalTime.now();
        String optZeroH = "";
        String optZeroM = "";
        String optZeroS = "";
        if(t.getHour() < 10) optZeroH = "0";
        if(t.getMinute() < 10) optZeroM = "0";
        if(t.getSecond() < 10) optZeroS = "0";

        label_hour.setText(optZeroH + String.valueOf(t.getHour()));
        label_min.setText(optZeroM + String.valueOf(t.getMinute()));
        label_sec.setText(optZeroS + String.valueOf(t.getSecond()));
    }

    @Override
    public void save() {
        storeObj.setPos(panel.getLocation());
        FileHandler.writeObject(storeObj, storePath + index);
    }

    @Override
    public void initGui() {

        panel.setSize((int)(WIDTH * storeObj.getScale()), (int) (HEIGHT * storeObj.getScale()));

        JPanel panel_center = new JPanel();
        panel_center.setLayout(new FlowLayout());
        panel_center.setBounds(0,15,WIDTH, HEIGHT);
        panel_center.setOpaque(false);

        int size = 48;
        label_hour.setForeground(Color.white);
        label_hour.setFont(Gui_Holder.getPrimaryFont(size,true, storeObj.getScale()));
        //label_hour.setBounds(0,0,WIDTH,70);

        label_sep.setForeground(Color.white);
        //label_sep.setBounds(25,0,100,20);
        label_sep.setFont(Gui_Holder.getPrimaryFont(size,true, storeObj.getScale()));

        label_min.setForeground(Color.white);
        label_min.setFont(Gui_Holder.getPrimaryFont(size,true, storeObj.getScale()));
        //label_min.setBounds(50,0,100,20);

        label_sec.setForeground(Color.white);
        label_sec.setFont(Gui_Holder.getPrimaryFont(18,false, storeObj.getScale()));
        //label_sec.setBounds(75,0,100,20);

        panel.add(panel_center, BorderLayout.CENTER);

        panel_center.add(label_hour);
        panel_center.add(label_sep);
        panel_center.add(label_min);
        panel_center.add(label_sec);

        panel.revalidate();
        panel.repaint();
    }

    @Override
    public void showSettings() {
        ShowSettings settings = new ShowSettings();
        double oldscale = storeObj.getScale();

        JFrame jFrame_Settings = new JFrame();
        JPanel vboxMain = new JPanel();
        vboxMain.setLayout(new BoxLayout(vboxMain, BoxLayout.Y_AXIS));

        jFrame_Settings.add(vboxMain);

        SettingsReturnObject sizeRow = settings.createInputRow("Größe", Double.toString(storeObj.getScale()));
        SettingsReturnObject confirmRow = settings.getConfirmButton();
        vboxMain.add(sizeRow.getPane());
        vboxMain.add(confirmRow.getPane());

        confirmRow.getBtn1().addActionListener(e -> jFrame_Settings.dispose());

        confirmRow.getBtn2().addActionListener(e -> {
                if(Double.parseDouble(sizeRow.getTextField().getText()) != oldscale) {
                    storeObj.setScale(Double.parseDouble(sizeRow.getTextField().getText()));
                    initGui();
                }
                save();
                update();
                jFrame_Settings.dispose();
        });

        confirmRow.getBtn3().addActionListener(e -> {
            jFrame_Settings.dispose();
        });

        jFrame_Settings.pack();
        jFrame_Settings.setVisible(true);
    }

    @Override
    public void loadObject() {
        if(FileHandler.fileExist(storePath + index)) {
            storeObj = (ClockStoreObject) FileHandler.loadObjects(storePath + index);
        }
        panel.setLocation(storeObj.getPos());
        initGui();
        update();
        save();
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public StoreObject getStoreObject() {
        return storeObj;
    }

    @Override
    public int getWIDTH() {
        return WIDTH;
    }

    @Override
    public int getHEIGHT() {
        return HEIGHT;
    }

    @Override
    public String getName() {
        return name;
    }
}
