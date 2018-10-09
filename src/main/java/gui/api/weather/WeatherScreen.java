package gui.api.weather;

import data_structure.FileHandler;
import gui.Gui_Holder;
import gui.ParentController;
import gui.api.weather.filestructure.WeatherObject;
import gui.guiControlls.SettingsReturnObject;
import gui.guiControlls.ShowSettings;
import main.App;

import javax.swing.*;
import java.awt.*;


public class WeatherScreen extends ParentController {

    private GetWeatherData weatherData;
    private StoreWeatherObject storeObj = new StoreWeatherObject();

    private JLabel label_city = new JLabel();
    private JLabel label_temp = new JLabel();
    private JLabel weather_label = new JLabel();
    private JLabel label_wind = new JLabel();
    private JLabel label_wind_interpretation = new JLabel();
    private JPanel hbox_Forecast = new JPanel();
    private JSeparator sep = new JSeparator();


    public WeatherScreen() {
        WIDTH = 180;
        HEIGHT = 220;
        name = "weather";
        storePath = "data/store/weather_";
        long timerCircle = 1000 * 60 * storeObj.getUpdateCircle();
        setTimer(timerCircle, timerCircle);
    }

    public void update() {
        WeatherObject wobj = weatherData.getWeather();
        weather_label.setIcon(resizeImage(new ImageIcon(App.fileHolder.getWeatherIcon(wobj.getCurrentIcon())), 64,64));
        label_city.setText(storeObj.getCity());
        label_temp.setText(wobj.getCurrTemperature());
        label_wind.setText("Wind: " + wobj.getCurrWind() + " kmh");
        label_wind_interpretation.setText(wobj.getWindInWords());
        hbox_Forecast.removeAll();
        for(int i = 0; i < 3; i++) {
            hbox_Forecast.add(forecastGuiElement(wobj, i));
            hbox_Forecast.add(Box.createRigidArea(new Dimension(10,0)));
        }
    }

    @Override
    public void showSettings() {
        ShowSettings settings = new ShowSettings();
        double oldscale = storeObj.getScale();

        JFrame jFrame_Settings = new JFrame();
        JPanel vboxMain = new JPanel();
        vboxMain.setLayout(new BoxLayout(vboxMain, BoxLayout.Y_AXIS));

        jFrame_Settings.add(vboxMain);

        SettingsReturnObject cityRow = settings.createInputRow("Stadt", storeObj.getCity());
        SettingsReturnObject sizeRow = settings.createInputRow("Größe", Double.toString(storeObj.getScale()));
        SettingsReturnObject updateRow = settings.createInputRow("update Circle", Integer.toString(storeObj.getUpdateCircle()));
        SettingsReturnObject confirmRow = settings.getConfirmButton();
        vboxMain.add(cityRow.getPane());
        vboxMain.add(sizeRow.getPane());
        vboxMain.add(updateRow.getPane());
        vboxMain.add(confirmRow.getPane());

        confirmRow.getBtn1().addActionListener(e -> {
                jFrame_Settings.dispose();
        });

        confirmRow.getBtn2().addActionListener(e -> {
                storeObj.setCity(cityRow.getTextField().getText());
                storeObj.setUpdateCircle(Integer.parseInt(updateRow.getTextField().getText()));
                if(Double.parseDouble(sizeRow.getTextField().getText()) != oldscale) {
                    storeObj.setScale(Double.parseDouble(sizeRow.getTextField().getText()));
                    initGui();
                }
                weatherData = new GetWeatherData(storeObj.getCity());
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

    @Override
    public void save() {
        storeObj.setPos(panel.getLocation());
        FileHandler.writeObject(storeObj, storePath + index);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    public void loadObject() {
        if(FileHandler.fileExist(storePath + index)) {
            storeObj = (StoreWeatherObject) FileHandler.loadObjects(storePath + index);
        }
        panel.setLocation(storeObj.getPos());
        weatherData = new GetWeatherData(storeObj.getCity());
        initGui();
        update();
        save();
    }

    public void initGui() {
        WeatherObject wobj = weatherData.getWeather();

        panel.setSize((int)(WIDTH * storeObj.getScale()), (int) (HEIGHT * storeObj.getScale()));
        //panel.setPreferredSize(new Dimension((int)(WIDTH * storeObj.getScale()), (int) (HEIGHT * storeObj.getScale())));

        JPanel vbox_main = new JPanel();
        vbox_main.setOpaque(false);
        vbox_main.setAlignmentX(Component.CENTER_ALIGNMENT);
        vbox_main.setLayout(new BoxLayout(vbox_main, BoxLayout.Y_AXIS));

        JPanel hbox_top = new JPanel();
        hbox_top.setOpaque(false);
        hbox_top.setLayout(new BoxLayout(hbox_top, BoxLayout.X_AXIS));

        JPanel hbox_bottom = new JPanel();
        hbox_bottom.setOpaque(false);
        hbox_bottom.setAlignmentX(Component.CENTER_ALIGNMENT);
        hbox_bottom.setLayout(new BoxLayout(hbox_bottom, BoxLayout.Y_AXIS));

        label_city.setText(storeObj.getCity());
        label_city.setForeground(Color.white);
        label_city.setFont(Gui_Holder.getPrimaryFont(15,false, storeObj.getScale()));

        weather_label.setIcon(resizeImage(new ImageIcon(App.fileHolder.getWeatherIcon(wobj.getCurrentIcon())), 64,64));

        label_temp.setText(wobj.getCurrTemperature());
        label_temp.setFont(Gui_Holder.getPrimaryFont(38,true, storeObj.getScale()));
        label_temp.setForeground(Color.white);

        label_wind.setText("Wind: " + wobj.getCurrWind() + " kmh");
        label_wind.setFont(Gui_Holder.getPrimaryFont(18,false, storeObj.getScale()));
        label_wind.setForeground(Color.white);

        label_wind_interpretation.setText(wobj.getWindInWords());
        label_wind_interpretation.setFont(Gui_Holder.getPrimaryFont(16,false, storeObj.getScale()));
        label_wind_interpretation.setForeground(Color.white);

        panel_top.add(label_city, BorderLayout.CENTER);

        hbox_top.add(weather_label);
        hbox_top.add(Box.createRigidArea(new Dimension(15,0)));
        hbox_top.add(label_temp);

        hbox_bottom.add(label_wind);
        hbox_bottom.add(Box.createRigidArea(new Dimension(0,5)));
        hbox_bottom.add(label_wind_interpretation);

        vbox_main.add(hbox_top);
        vbox_main.add(Box.createRigidArea(new Dimension(0,5)));
        vbox_main.add(hbox_bottom);
        vbox_main.add(Box.createRigidArea(new Dimension(0,2)));
        vbox_main.add(sep);
        vbox_main.add(Box.createRigidArea(new Dimension(0,4)));


        hbox_Forecast.setLayout(new BoxLayout(hbox_Forecast, BoxLayout.X_AXIS));
        hbox_Forecast.setOpaque(false);

        for(int i = 0; i < 3; i++) {
            hbox_Forecast.add(forecastGuiElement(wobj, i));
            hbox_Forecast.add(Box.createRigidArea(new Dimension(6,0)));
        }

        panel.add(vbox_main, BorderLayout.CENTER);
        panel.add(hbox_Forecast, BorderLayout.SOUTH);

        panel.revalidate();
        panel.repaint();
    }

    private JPanel forecastGuiElement(WeatherObject wobj, int index) {
        JPanel vbox = new JPanel();
        vbox.setLayout(new BoxLayout(vbox, BoxLayout.Y_AXIS));
        vbox.setOpaque(false);

        JLabel label_time = new JLabel(wobj.getTimeByIndex(index));
        label_time.setFont(Gui_Holder.getPrimaryFont(15,false, storeObj.getScale()));
        label_time.setForeground(Color.white);

        JLabel icon = new JLabel();
        icon.setIcon(resizeImage(new ImageIcon(App.fileHolder.getWeatherIcon(wobj.getForecastIconbyIndex(index))), 32,32));

        JLabel label_temp = new JLabel(wobj.getForecastTempByIndex(index));
        label_temp.setFont(Gui_Holder.getPrimaryFont(14,false, storeObj.getScale()));
        label_temp.setForeground(Color.white);

        vbox.add(label_time);
        vbox.add(Box.createHorizontalGlue());
        vbox.add(icon);
        vbox.add(Box.createHorizontalGlue());
        vbox.add(label_temp);

        return vbox;
    }

    private ImageIcon resizeImage(ImageIcon icon, int w, int h) {
        w = (int) (w * storeObj.getScale());
        h = (int) (h * storeObj.getScale());
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        return new ImageIcon(newimg);  // transform it back
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
    public void setIndex(int index) {
        this.index = index;
    }
    @Override
    public int getIndex() {
        return index;
    }
    @Override
    public StoreWeatherObject getStoreObject() {
        return storeObj;
    }
    @Override
    public String getName() {
        return name;
    }
}
