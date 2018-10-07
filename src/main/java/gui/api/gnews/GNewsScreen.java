package gui.api.gnews;

import data_structure.FileHandler;
import gui.Gui_Holder;
import gui.ParentController;
import gui.api.StoreObject;
import gui.api.gnews.filestructure.GNewsDataObject;
import gui.guiControlls.SettingsReturnObject;
import gui.guiControlls.ShowSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GNewsScreen extends ParentController {

    private GNewsStoreObject storeObj = new GNewsStoreObject();
    private GetGNewsData getGNewsData;
    private ArrayList<GNewsDataObject> newsDataObjects = new ArrayList<>();
    private Timer inner_timer;
    private int innerCicleCounter = 0;

    private JLabel label_news1 = new JLabel("", SwingConstants.CENTER);
    private JLabel label_news2 = new JLabel("", SwingConstants.CENTER);
    private JLabel label_news3 = new JLabel("", SwingConstants.CENTER);


    public GNewsScreen() {
        WIDTH = 800;
        HEIGHT = 90;
        name = "gnews";
        storePath = "data/store/gnews_";
        long timerCircle = 1000 * 60 * storeObj.getUpdateCircle();
        setTimer(timerCircle, timerCircle);

        long innerTimeCircle = 1000 * 10;
        inner_timer = new Timer("updateInnerTimer");
        inner_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                inner_update();
            }
        }, innerTimeCircle*2, innerTimeCircle);
    }

    @Override
    public void update() {
        newsDataObjects = getGNewsData.getNews();
        label_news1.setText(newsDataObjects.get(innerCicleCounter).getTitle());
        label_news2.setText(newsDataObjects.get(innerCicleCounter + 1).getTitle());
        label_news3.setText(newsDataObjects.get(innerCicleCounter + 2).getTitle());
    }

    private void inner_update() {
        label_news1.setText(newsDataObjects.get((innerCicleCounter % newsDataObjects.size())).getTitle());
        label_news2.setText(newsDataObjects.get(((innerCicleCounter + 1) % newsDataObjects.size())).getTitle());
        label_news3.setText(newsDataObjects.get(((innerCicleCounter + 2)% newsDataObjects.size())).getTitle());
        innerCicleCounter++;
    }

    @Override
    public void save() {
        storeObj.setPos(panel.getLocation());
        FileHandler.writeObject(storeObj, storePath + index);
    }

    @Override
    public void initGui() {
        newsDataObjects = getGNewsData.getNews();

        panel.setSize((int)(WIDTH * storeObj.getScale()), (int) (HEIGHT * storeObj.getScale()));

        JPanel panel_center = new JPanel();
        panel_center.setLayout(new GridLayout(3,0,0,0));
        panel_center.setOpaque(false);

        panel_center.setAlignmentX(Component.CENTER_ALIGNMENT);

        label_news1.setText(newsDataObjects.get(0).getTitle());
        label_news1.setForeground(Color.GRAY);
        label_news1.setFont(Gui_Holder.getPrimaryFont(16,false, storeObj.getScale()));

        label_news2.setText(newsDataObjects.get(1).getTitle());
        label_news2.setForeground(Color.white);
        label_news2.setFont(Gui_Holder.getPrimaryFont(20,false, storeObj.getScale()));
        label_news2.setMaximumSize(new Dimension(WIDTH, 5));


        label_news3.setText(newsDataObjects.get(1).getTitle());
        label_news3.setForeground(Color.GRAY);
        label_news3.setFont(Gui_Holder.getPrimaryFont(16,false, storeObj.getScale()));

        panel_center.add(label_news1);
        panel_center.add(label_news2);
        panel_center.add(label_news3);

        panel.add(panel_center, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }

    @Override
    public void loadObject() {
        if(FileHandler.fileExist(storePath + index)) {
            storeObj = (GNewsStoreObject) FileHandler.loadObjects(storePath + index);
        }
        panel.setLocation(storeObj.getPos());
        getGNewsData = new GetGNewsData();
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

        confirmRow.getBtn1().addActionListener(e -> jFrame_Settings.dispose());

        confirmRow.getBtn2().addActionListener(e -> {
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
