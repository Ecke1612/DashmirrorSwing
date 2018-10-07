package gui.api.db;

import data_structure.FileHandler;
import gui.Gui_Holder;
import gui.ParentController;
import gui.api.StoreObject;
import gui.api.db.filestructure.DBDataObject;
import gui.guiControlls.SettingsReturnObject;
import gui.guiControlls.ShowSettings;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DBScreen extends ParentController {

    private DBStoreObject storeObj = new DBStoreObject();
    private GetDBData getDBData;

    private JPanel main_vbox = new JPanel();
    private JLabel label_title = new JLabel("");

    public DBScreen() {
        WIDTH = 280;
        HEIGHT = 260;
        name ="db";
        storePath ="data/store/db_";
        long timerCircle = 1000 * 60 * storeObj.getUpdateCircle();

        setTimer(timerCircle, timerCircle);
}

    @Override
    public void update() {
       ArrayList<DBDataObject> dbDataObjects = getDBData.getData(storeObj.getStartCity(), storeObj.getStopCity());
       main_vbox.removeAll();
        for(DBDataObject d : dbDataObjects) {
            main_vbox.add(createRow(d));
            main_vbox.add(Box.createRigidArea(new Dimension(0,(int)(10 * storeObj.getScale()))));
        }
        label_title.setText("DB: von " + storeObj.getStartCity() + " nach " + storeObj.getStopCity());

    }

    @Override
    public void save() {
        storeObj.setPos(panel.getLocation());
        FileHandler.writeObject(storeObj, storePath + index);
    }

    @Override
    public void initGui() {
        panel.setSize((int)(WIDTH * storeObj.getScale()), (int) (HEIGHT * storeObj.getScale()));

        label_title.setForeground(Color.white);
        label_title.setFont(Gui_Holder.getPrimaryFont(14,false, storeObj.getScale()));

        panel_top.add(label_title, BorderLayout.CENTER);

        main_vbox.setLayout(new BoxLayout(main_vbox, BoxLayout.Y_AXIS));
        main_vbox.setOpaque(false);

        panel.add(main_vbox, BorderLayout.CENTER);

        panel.revalidate();
        panel.repaint();
    }

    private JPanel createRow(DBDataObject dbobj) {
        JPanel hbox_main = new JPanel();
        hbox_main.setLayout(new BoxLayout(hbox_main, BoxLayout.X_AXIS));
        hbox_main.setOpaque(false);

        JPanel vbox1 = new JPanel();
        vbox1.setLayout(new BoxLayout(vbox1, BoxLayout.Y_AXIS));
        vbox1.setOpaque(false);

        JPanel vbox2 = new JPanel();
        vbox2.setLayout(new BoxLayout(vbox2, BoxLayout.Y_AXIS));
        vbox2.setOpaque(false);

        JPanel vbox3 = new JPanel();
        vbox3.setLayout(new BoxLayout(vbox3, BoxLayout.Y_AXIS));
        vbox3.setOpaque(false);

        JPanel vbox4 = new JPanel();
        vbox4.setLayout(new BoxLayout(vbox4, BoxLayout.Y_AXIS));
        vbox4.setOpaque(false);

        int size = 20;

        JLabel label_abfahrt = new JLabel(dbobj.getAbfahrt());
        label_abfahrt.setForeground(Color.white);
        label_abfahrt.setFont(Gui_Holder.getPrimaryFont(size,false, storeObj.getScale()));

        JLabel label_ankunft = new JLabel(dbobj.getAnkunft());
        label_ankunft.setForeground(Color.white);
        label_ankunft.setFont(Gui_Holder.getPrimaryFont(size,false, storeObj.getScale()));

        JLabel label_echteAbfahrt = new JLabel(dbobj.getEchteAbfahrtzeit());
        if(dbobj.getAbfahrt().equals(dbobj.getEchteAbfahrtzeit())) label_echteAbfahrt.setForeground(Color.GREEN);
        else label_echteAbfahrt.setForeground(Color.RED);
        label_echteAbfahrt.setFont(Gui_Holder.getPrimaryFont(18,false, storeObj.getScale()));

        JLabel label_umstieg = new JLabel(dbobj.getUmstieg());
        label_umstieg.setForeground(Color.white);
        label_umstieg.setFont(Gui_Holder.getPrimaryFont(size,false, storeObj.getScale()));

        JLabel label_dauer = new JLabel(dbobj.getDauer());
        label_dauer.setForeground(Color.white);
        label_dauer.setFont(Gui_Holder.getPrimaryFont(size,false, storeObj.getScale()));

        JLabel label_zug = new JLabel(dbobj.getZug());
        label_zug.setForeground(Color.white);
        label_zug.setFont(Gui_Holder.getPrimaryFont(size,false, storeObj.getScale()));
        JLabel label_preis = new JLabel(dbobj.getPrice() + " €");
        label_preis.setForeground(Color.white);
        label_preis.setFont(Gui_Holder.getPrimaryFont(size,false, storeObj.getScale()));

        vbox1.add(label_abfahrt);
        vbox1.add(label_ankunft);

        vbox2.add(label_echteAbfahrt);

        vbox3.add(label_umstieg);
        vbox3.add(label_dauer);

        vbox4.add(label_zug);
        vbox4.add(label_preis);

        hbox_main.add(vbox1);
        hbox_main.add(Box.createRigidArea(new Dimension((int)(25 * storeObj.getScale()),0)));
        hbox_main.add(vbox2);
        hbox_main.add(Box.createRigidArea(new Dimension((int)(25 * storeObj.getScale()),0)));
        hbox_main.add(Box.createHorizontalGlue());
        hbox_main.add(vbox3);
        hbox_main.add(Box.createRigidArea(new Dimension((int)(25 * storeObj.getScale()),0)));
        hbox_main.add(vbox4);
        hbox_main.add(Box.createHorizontalGlue());

        return hbox_main;
    }

    @Override
    public void loadObject() {
        if(FileHandler.fileExist(storePath + index)) {
            storeObj = (DBStoreObject) FileHandler.loadObjects(storePath + index);
        }
        panel.setLocation(storeObj.getPos());
        getDBData = new GetDBData();
        initGui();
        update();
        save();
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void showSettings() {
        ShowSettings settings = new ShowSettings();
        double oldscale = storeObj.getScale();

        JFrame jFrame_Settings = new JFrame();
        JPanel vboxMain = new JPanel();
        vboxMain.setLayout(new BoxLayout(vboxMain, BoxLayout.Y_AXIS));

        jFrame_Settings.add(vboxMain);

        SettingsReturnObject startcityRow = settings.createInputRow("Start Hbf", storeObj.getStartCity());
        SettingsReturnObject stopcityRow = settings.createInputRow("Stopp Hbf", storeObj.getStopCity());
        SettingsReturnObject sizeRow = settings.createInputRow("Größe", Double.toString(storeObj.getScale()));
        SettingsReturnObject updateRow = settings.createInputRow("update Circle", Integer.toString(storeObj.getUpdateCircle()));
        SettingsReturnObject confirmRow = settings.getConfirmButton();
        vboxMain.add(startcityRow.getPane());
        vboxMain.add(stopcityRow.getPane());
        vboxMain.add(sizeRow.getPane());
        vboxMain.add(updateRow.getPane());
        vboxMain.add(confirmRow.getPane());

        confirmRow.getBtn1().addActionListener(e -> jFrame_Settings.dispose());

        confirmRow.getBtn2().addActionListener(e -> {
                storeObj.setStartCity(startcityRow.getTextField().getText());
                storeObj.setStopCity(stopcityRow.getTextField().getText());
                storeObj.setUpdateCircle(Integer.parseInt(updateRow.getTextField().getText()));
                if(Double.parseDouble(sizeRow.getTextField().getText()) != oldscale) {
                    storeObj.setScale(Double.parseDouble(sizeRow.getTextField().getText()));
                    initGui();
                }
                save();
                update();
                jFrame_Settings.dispose();
        });

        confirmRow.getBtn3().addActionListener(e -> {
            save();
            update();
            jFrame_Settings.dispose();
        });

        jFrame_Settings.pack();
        jFrame_Settings.setVisible(true);
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
