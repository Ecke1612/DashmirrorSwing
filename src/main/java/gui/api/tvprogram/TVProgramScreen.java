package gui.api.tvprogram;

import data_structure.FileHandler;
import gui.Gui_Holder;
import gui.ParentController;
import gui.api.StoreObject;
import gui.api.tvprogram.filestructure.TVObject;
import gui.guiControlls.SettingsReturnObject;
import gui.guiControlls.ShowSettings;

import javax.swing.*;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.util.ArrayList;

public class TVProgramScreen extends ParentController {

    private GetTVData getTVData = new GetTVData();
    private StoreTVProgram storeObj = new StoreTVProgram();

    private JPanel main_vbox = new JPanel();
    private JPanel grid = new JPanel();
    private ArrayList<TVObject> tvDataObjects;

    public TVProgramScreen() {
        WIDTH = 400;
        HEIGHT = 50;
        name = "tv";
        storePath = "data/store/tv_";
        long timerCircle = 1000 * 60 * storeObj.getUpdateCircle();
        setTimer(timerCircle, timerCircle);
    }

    @Override
    public void update() {
        tvDataObjects = getTVData.getData();
        main_vbox.removeAll();
        int i = 0;
        for(TVObject tv : tvDataObjects) {
            if(storeObj.getChannels()[i]) {
                main_vbox.add(channelRow(tv));
                main_vbox.add(Box.createRigidArea(new Dimension(0,5)));
            }
            i++;
        }
    }

    @Override
    public void save() {
        storeObj.setPos(panel.getLocation());
        FileHandler.writeObject(storeObj, storePath + index);
    }

    @Override
    public void initGui() {
        HEIGHT = 50 + (getActiveChannelsCount() * 44);
        panel.setSize((int)(WIDTH * storeObj.getScale()), (int) (HEIGHT * storeObj.getScale()));

        main_vbox.setLayout(new BoxLayout(main_vbox, BoxLayout.Y_AXIS));
        main_vbox.setOpaque(false);

        panel.add(main_vbox, BorderLayout.CENTER);

        panel.revalidate();
        panel.repaint();
    }

    private JPanel channelRow(TVObject tvObj) {
        JPanel vbox = new JPanel();
        vbox.setLayout(new BoxLayout(vbox, BoxLayout.Y_AXIS));
        vbox.setOpaque(false);

        JPanel hbox1 = new JPanel();
        hbox1.setLayout(new BoxLayout(hbox1, BoxLayout.X_AXIS));
        hbox1.setOpaque(false);

        JPanel hbox2 = new JPanel();
        hbox2.setLayout(new BoxLayout(hbox2, BoxLayout.X_AXIS));
        hbox2.setOpaque(false);

        int size = 18;
        JLabel label_channel = new JLabel(tvObj.getChannel());
        label_channel.setForeground(Color.white);
        label_channel.setFont(Gui_Holder.getPrimaryFont(14,false, storeObj.getScale()));

        JLabel label_time = new JLabel(tvObj.getTime() + ": ");
        label_time.setForeground(Color.white);
        label_time.setFont(Gui_Holder.getPrimaryFont(size,false, storeObj.getScale()));

        JLabel label_title = new JLabel(tvObj.getTitle());
        label_title.setForeground(Color.white);
        label_title.setFont(Gui_Holder.getPrimaryFont(size,false, storeObj.getScale()));

        hbox1.add(label_channel);
        hbox1.add(Box.createHorizontalGlue());
        vbox.add(hbox1);
        hbox2.add(label_time);
        hbox2.add(Box.createRigidArea(new Dimension(6,0)));
        hbox2.add(label_title);
        hbox2.add(Box.createHorizontalGlue());
        vbox.add(hbox2);

        return vbox;
    }

    @Override
    public void loadObject() {
        if(FileHandler.fileExist(storePath + index)) {
            storeObj = (StoreTVProgram) FileHandler.loadObjects(storePath + index);
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
    public void showSettings() {
        ShowSettings settings = new ShowSettings();
        double oldscale = storeObj.getScale();

        JFrame jFrame_Settings = new JFrame();
        JPanel vboxMain = new JPanel();
        vboxMain.setLayout(new BoxLayout(vboxMain, BoxLayout.Y_AXIS));

        jFrame_Settings.add(vboxMain);

        SettingsReturnObject sizeRow = settings.createInputRow("Größe", Double.toString(storeObj.getScale()));
        SettingsReturnObject updateRow = settings.createInputRow("update Circle", Integer.toString(storeObj.getUpdateCircle()));
        SettingsReturnObject confirmRow = settings.getConfirmButton();

        vboxMain.add(sizeRow.getPane());
        vboxMain.add(updateRow.getPane());
        vboxMain.add(confirmRow.getPane());

        ArrayList<SettingsReturnObject> channelCheckbox = new ArrayList<>();
        int index = 0;
        for(TVObject tv : tvDataObjects) {
            SettingsReturnObject tvRow = settings.getCheckBoxRow(tv.getChannel(), storeObj.getChannels()[index]);
            channelCheckbox.add(tvRow);
            vboxMain.add(tvRow.getPane());
            index++;
        }

        confirmRow.getBtn1().addActionListener(e -> {
            jFrame_Settings.dispose();
        });

        confirmRow.getBtn2().addActionListener(e -> {
            storeObj.setUpdateCircle(Integer.parseInt(updateRow.getTextField().getText()));
            int index1 = 0;
            for(SettingsReturnObject s : channelCheckbox) {
                storeObj.setChannelsByIndex(s.getCheckBox().isSelected(), index1);
                index1++;
            }
            if(Double.parseDouble(sizeRow.getTextField().getText()) != oldscale) {
                storeObj.setScale(Double.parseDouble(sizeRow.getTextField().getText()));

            }
            initGui();
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
        jFrame_Settings.setAlwaysOnTop(true);
    }

    private int getActiveChannelsCount() {
        int count = 0;
        grid.removeAll();
        for(int i = 0; i < storeObj.getChannels().length; i++) {
            if(storeObj.getChannels()[i]) {
                count++;
            }
        }
        return count;
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
