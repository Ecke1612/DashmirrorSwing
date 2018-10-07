package gui;

import data_structure.FileHandler;
import gui.api.StoreObject;
import gui.guiControlls.MouseMotionEvent;
import main.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public abstract class ParentController {

    public JPanel panel = new JPanel();
    public JPanel panel_top = new JPanel();
    public int WIDTH;
    public int HEIGHT;
    private Timer timer;
    public String storePath;
    public int index;
    public String name;

    abstract public void update();

    abstract public void save();

    public void setTimer(long delay, long loop) {
        timer = new Timer("updateTimer");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, delay, loop);
    }

    public void standardGui(MainScreen mainScreen) {
        panel.setSize(WIDTH,HEIGHT);
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);

        panel_top.setLayout(new BorderLayout());
        //panel_top.setBounds(0,0,WIDTH, 15);
        panel_top.setOpaque(false);

        JButton btn_settings = new JButton();
        JButton btn_delete = new JButton("X");

        MouseMotionEvent mouseMotionEvent = new MouseMotionEvent(this);
        panel.addMouseMotionListener(mouseMotionEvent);
        panel.addMouseListener(mouseMotionEvent);
        //panel.setBounds(0,0,WIDTH,HEIGHT);


        btn_settings.setIcon(new ImageIcon(App.fileHolder.getHam_button()));
        //btn_settings.setBounds(0,0,20,20);
        styleButton(btn_settings);

        //btn_delete.setBounds(WIDTH - 16, 0,16,16);
        styleButton(btn_delete);

        btn_settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSettings();
            }
        });

        btn_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.cancel();
                mainScreen.weatherindex--;
                mainScreen.getCenterPanel().remove(panel);
                mainScreen.getCenterPanel().revalidate();
                mainScreen.getCenterPanel().repaint();
                FileHandler.deleteFile(storePath + index);
            }
        });

        panel.add(panel_top, BorderLayout.NORTH);

        panel_top.add(btn_settings, BorderLayout.LINE_START);
        panel_top.add(btn_delete, BorderLayout.LINE_END);

        //initGui();
    }

    public void delete(){

    }

    abstract public void initGui();

    abstract public void loadObject();

    abstract public JPanel getPanel();

    abstract public void showSettings();

    abstract public int getIndex();

    abstract public void setIndex(int index);

    abstract public StoreObject getStoreObject();

    abstract public int getWIDTH();

    abstract public int getHEIGHT();

    abstract public String getName();

    public void styleButton(JButton btn) {
        btn.setMargin(new Insets(0,0,0,0));
        btn.setBackground(null);
        btn.setBorder(null);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setForeground(Color.white);
    }

    public void styleLabel(JLabel label) {
        label.setBackground(null);
        label.setBorder(null);
    }
}
