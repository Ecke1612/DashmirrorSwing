package gui;

import data_structure.FileHandler;
import gui.api.clock.ClockScreen;
import gui.api.db.DBScreen;
import gui.api.gCalendar.GCalScreen;
import gui.api.gnews.GNewsScreen;
import gui.api.tvprogram.TVProgramScreen;
import gui.api.weather.WeatherScreen;
import main.App;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class MainScreen {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private boolean fullscreen = false;

    public int weatherindex = 0;
    public int gcalendarindex = 0;
    public int gnewsindex = 0;
    public int clockindex = 0;
    public int dbindex = 0;
    public int tvindex = 0;

    private final JFrame frame = new JFrame("Dashmirror");
    private JPanel topPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private GraphicsDevice vc;

    public MainScreen() {
        if(!FileHandler.fileExist("data")) FileHandler.createDir("data");
        if(!FileHandler.fileExist("data/store")) FileHandler.createDir("data/store");
        try {
            loadStoredData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        vc = env.getDefaultScreenDevice();
    }

    public void loadStoredData() {
        File[] fileArray = FileHandler.getFileInDir("data/store");
        if(fileArray.length != 0) {
            for(File f : fileArray) {
                String[] name = f.getName().split("_");
                String name1 = name[0];
                addWidget(true, f.getName(), name1);
            }
        }
    }


    private void addWidget(boolean load, String fullname, String name) {
        ParentController screen = null;
        System.out.println("name " + name + "; fullname: " + fullname);
        switch(name) {
            case "weather":
                screen = new WeatherScreen();
                break;
            case "gcal":
                screen = new GCalScreen();
                break;
            case "gnews":
                screen = new GNewsScreen();
                break;
            case "clock":
                screen = new ClockScreen();
                break;
            case "db":
                screen = new DBScreen();
                break;
            case "tv":
                screen = new TVProgramScreen();
                break;
            default:
                screen = new WeatherScreen();
                break;
        }
        screen.setIndex(getRightIndex(screen.getName()));
        if(load) FileHandler.renameFile("data/store/" + fullname, "data/store/" + name + "_" + getRightIndex(screen.getName()));
        screen.standardGui(this);
        screen.loadObject();
        JPanel panel = screen.getPanel();
        panel.setLocation(screen.getStoreObject().getPos().x, screen.getStoreObject().getPos().y);
        increaseRightIndex(screen.getName());
        centerPanel.add(panel);
        centerPanel.revalidate();
        centerPanel.repaint();
    }


    public void initGui() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH,HEIGHT);
        frame.setLayout(new BorderLayout());
        frame.setBackground(Color.BLACK);
        frame.setTitle("Dashmirror " + Main.externalBuild);

        //JButton btn_add = new JButton();
        //btn_add.setIcon(new ImageIcon(App.fileHolder.getIcon_add()));
        //styleButton(btn_add);

        JButton btn_fullscreen = new JButton();
        btn_fullscreen.setIcon(new ImageIcon(App.fileHolder.getIcon_fullscreen()));
        styleButton(btn_fullscreen);

        frame.getContentPane().setLayout(new BorderLayout(10,10));
        frame.getContentPane().setBackground(null);

        topPanel.setLayout(new BorderLayout());
        //topPanel.add(btn_add, BorderLayout.WEST);
        topPanel.add(btn_fullscreen, BorderLayout.EAST);
        topPanel.setOpaque(false);
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);

        centerPanel.setLayout(null);
        centerPanel.setOpaque(false);

        frame.getContentPane().add(centerPanel, BorderLayout.CENTER);

        JPopupMenu popup = new JPopupMenu();
        JMenuItem item_weather = new JMenuItem("Wetter API");
        item_weather.addActionListener(e -> {
            addWidget(false,"", "weather");
            popup.setVisible(false);
        });

        JMenuItem item_gcal = new JMenuItem("Google Kalendar");
        item_gcal.addActionListener(e -> {
            addWidget(false, "","gcal");
            popup.setVisible(false);
        });

        JMenuItem item_gnews = new JMenuItem("Google News");
        item_gnews.addActionListener(e -> {
            addWidget(false, "", "gnews");
            popup.setVisible(false);
        });

        JMenuItem item_clock = new JMenuItem("Uhr");
        item_clock.addActionListener(e -> {
            addWidget(false, "", "clock");
            popup.setVisible(false);
        });

        JMenuItem item_db = new JMenuItem("DB");
        item_db.addActionListener(e -> {
            addWidget(false, "", "db");
            popup.setVisible(false);
        });

        JMenuItem item_tv = new JMenuItem("TV Programm");
        item_tv.addActionListener(e -> {
            addWidget(false, "", "tv");
            popup.setVisible(false);
        });

        popup.add(item_weather);
        popup.add(item_gcal);
        popup.add(item_gnews);
        popup.add(item_clock);
        popup.add(item_db);
        popup.add(item_tv);


        frame.setVisible(true);

        btn_fullscreen.addActionListener(e -> {
            if(!fullscreen) {
                try {
                    frame.setResizable(false);
                    vc.setFullScreenWindow(frame);
                    fullscreen = true;
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                vc.setFullScreenWindow(null);
                fullscreen = false;
            }
        });

        centerPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1){
                    if(popup.isVisible()) popup.setVisible(false);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON3){
                    popup.setLocation(e.getXOnScreen(), e.getYOnScreen());
                    popup.setVisible(true);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void styleButton(JButton btn) {
        btn.setMargin(new Insets(0,0,0,0));
        btn.setBackground(null);
        btn.setBorder(null);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setForeground(Color.white);
    }



    public JPanel getCenterPanel() {
        return centerPanel;
    }

    private void increaseRightIndex(String name) {
        switch(name) {
            case "weather":
                weatherindex++;
                break;
            case "gcal":
                gcalendarindex++;
                break;
            case "gnews":
                gnewsindex++;
                break;
            case "clock":
                clockindex++;
                break;
            case "db" :
                dbindex++;
                break;
            case "tv" :
                tvindex++;
                break;
        }
    }

    private int getRightIndex(String name) {
        switch(name) {
            case "weather":
                System.out.println("wetterindex: " + weatherindex);
                return weatherindex;
            case "gcal":
                return gcalendarindex;
            case "gnews":
                return gnewsindex;
            case "clock":
                return clockindex;
            case "db":
                return dbindex;
            case "tv":
                return tvindex;
            default:
                return 0;
        }
    }
}
