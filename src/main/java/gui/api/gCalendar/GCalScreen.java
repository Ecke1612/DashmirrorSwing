package gui.api.gCalendar;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import data_structure.FileHandler;
import gui.Gui_Holder;
import gui.MainScreen;
import gui.ParentController;
import gui.guiControlls.SettingsReturnObject;
import gui.guiControlls.ShowSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;

public class GCalScreen extends ParentController {


    private JPanel center_panel = new JPanel();
    private GCalStoreObject storeObj = new GCalStoreObject();
    private JPanel panel_pageEnd = new JPanel();
    private JLabel label_title = new JLabel();


    public GCalScreen() {
        WIDTH = 280;
        HEIGHT = 300;
        storePath = "data/store/gcal_";
        name = "gcal";

        long timerCircle = 1000 * 60 * storeObj.getUpdateCircle();
        setTimer(timerCircle, timerCircle);
    }

    @Override
    public void update() {
        GetGcalData gCal = new GetGcalData();
        center_panel.removeAll();
        try {
            List<Event> items = gCal.getCalendarData(storeObj.getMaxResult());
            if(items != null) {
                System.out.println("size: " + items.size());
                if (items.isEmpty()) {
                    System.out.println("No upcoming events found.");
                } else {
                    System.out.println("Upcoming events");
                    for (Event event : items) {
                        DateTime start = event.getStart().getDateTime();
                        if (start == null) {
                            start = event.getStart().getDate();
                        }
                        //System.out.printf("%s (%s)\n", event.getSummary(), start);
                        center_panel.add(rowPane(event.getSummary(), start));
                        center_panel.add(Box.createRigidArea(new Dimension(0,10)));
                    }
                }
            } else {
                System.out.println("no data arrived");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        label_title.setText(storeObj.getName() + "'s Google Kalendar");
    }

    @Override
    public void loadObject() {
        if(FileHandler.fileExist(storePath + index)) {
            storeObj = (GCalStoreObject) FileHandler.loadObjects(storePath + index);
        }
        panel.setLocation(storeObj.getPos());
        initGui();
        update();
        save();
    }

    @Override
    public void save() {
        storeObj.setPos(panel.getLocation());
        FileHandler.writeObject(storeObj, storePath + index);
    }

    @Override
    public void initGui() {
        //panel_top.setBounds(0,0,WIDTH,55);

        panel.setSize((int)(WIDTH * storeObj.getScale()), (int) (HEIGHT * storeObj.getScale()));

        center_panel.setLayout(new BoxLayout(center_panel, BoxLayout.PAGE_AXIS));
        center_panel.setOpaque(false);

        label_title.setText(storeObj.getName() + "'s Google Kalendar");
        label_title.setForeground(Color.white);
        label_title.setFont(Gui_Holder.getPrimaryFont(14,false, storeObj.getScale()));

        JLabel label_dateNow = new JLabel(getActualDate());
        label_dateNow.setForeground(Color.white);
        label_dateNow.setFont(Gui_Holder.getPrimaryFont(22,true, storeObj.getScale()));

        JSeparator separator = new JSeparator();

        panel.add(center_panel, BorderLayout.CENTER);

        panel_top.add(label_title, BorderLayout.CENTER);

        panel_pageEnd.setOpaque(false);
        panel_pageEnd.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel_pageEnd.setLayout(new BoxLayout(panel_pageEnd, BoxLayout.Y_AXIS));
        panel_pageEnd.add(label_dateNow);
        panel_pageEnd.add(separator);
        panel_pageEnd.add(Box.createRigidArea(new Dimension(0,10)));

        panel_top.add(panel_pageEnd, BorderLayout.PAGE_END);

        panel.revalidate();
        panel.repaint();
    }

    private JPanel rowPane(String summery, DateTime start) {
        LocalDateTime date = convertDateTime(start);
        LocalTime range = getTimeRange(start);

        JPanel rowPane = new JPanel();
        rowPane.setLayout(new BoxLayout(rowPane, BoxLayout.X_AXIS));
        rowPane.setOpaque(false);
        rowPane.setBounds(new Rectangle(500, 25));

        JPanel vbox1 = new JPanel();
        vbox1.setLayout(new BoxLayout(vbox1, BoxLayout.Y_AXIS));
        vbox1.setOpaque(false);

        JPanel vbox2 = new JPanel();
        vbox2.setLayout(new BoxLayout(vbox2, BoxLayout.Y_AXIS));
        vbox2.setOpaque(false);

        JLabel label_day = new JLabel();
        JLabel label_dayofweek = new JLabel();
        JLabel label_subject = new JLabel();
        JLabel label_range = new JLabel();

        label_day.setText(String.valueOf(date.getDayOfMonth()));
        label_dayofweek.setText(date.getDayOfWeek().toString().substring(0,3));
        label_subject.setText(summery);


        label_day.setForeground(Color.white);
        label_day.setFont(Gui_Holder.getPrimaryFont(18,true, storeObj.getScale()));

        label_dayofweek.setForeground(Color.white);
        label_dayofweek.setFont(Gui_Holder.getPrimaryFont(15,false, storeObj.getScale()));


        label_subject.setForeground(Color.white);
        label_subject.setFont(Gui_Holder.getPrimaryFont(18,true, storeObj.getScale()));

        label_range.setForeground(Color.white);
        label_range.setFont(Gui_Holder.getPrimaryFont(15,false, storeObj.getScale()));


        if(range == null) {
            String optZeroM = "";
            String optZeroH = "";
            if (date.getMinute() < 10) optZeroM = "0";
            if (date.getHour() < 10) optZeroH = "0";
            label_range.setText(optZeroH + String.valueOf(date.getHour()) + ":" + optZeroM + String.valueOf(date.getMinute()));
        }else {
            int rangeInMinutes = (range.getHour() * 60) + range.getMinute();
            LocalTime timeRange = date.toLocalTime().plus(rangeInMinutes, ChronoUnit.MINUTES);

            String optZeroM = "";
            String optZeroH = "";
            if(date.getHour() < 10) optZeroH = "0";
            if (date.getMinute() < 10) optZeroM = "0";
            label_range.setText(optZeroH + String.valueOf(date.getHour()) + ":" + optZeroM + String.valueOf(date.getMinute())
                    + " - " + timeRange.getHour() + ":" + optZeroM + timeRange.getMinute());
        }

        vbox1.add(label_day);
        vbox1.add(label_dayofweek);

        vbox2.add(label_subject);
        vbox2.add(label_range);

        rowPane.add(vbox1);
        rowPane.add(Box.createRigidArea(new Dimension(15,0)));
        rowPane.add(vbox2);
        rowPane.add(Box.createHorizontalGlue());

        return rowPane;
    }

    private LocalDateTime convertDateTime(DateTime gDate) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTimeInMillis(gDate.getValue());
        LocalDateTime date = toLocalDateTime(cal);
        return date;
    }

    public static LocalDateTime toLocalDateTime(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        TimeZone tz = calendar.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zid);
    }

    private LocalTime getTimeRange(DateTime start) {
        try {
            String range = (start.toString()).substring(start.toString().lastIndexOf("+") + 1);
            LocalTime timeRange = LocalTime.parse(range);
            return timeRange;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public GCalStoreObject getStoreObject() {
        return storeObj;
    }

    @Override
    public void showSettings() {
        ShowSettings settings = new ShowSettings();
        double oldscale = storeObj.getScale();

        JFrame jFrame_Settings = new JFrame();
        JPanel vboxMain = new JPanel();
        vboxMain.setLayout(new BoxLayout(vboxMain, BoxLayout.Y_AXIS));

        jFrame_Settings.add(vboxMain);

        SettingsReturnObject nameRow = settings.createInputRow("Name", storeObj.getName());
        SettingsReturnObject maxResultsRow = settings.createInputRow("Anzahl Ereignisse", Integer.toString(storeObj.getMaxResult()));
        SettingsReturnObject sizeRow = settings.createInputRow("Größe", Double.toString(storeObj.getScale()));
        SettingsReturnObject updateRow = settings.createInputRow("update Circle", Integer.toString(storeObj.getUpdateCircle()));
        SettingsReturnObject confirmRow = settings.getConfirmButton();

        vboxMain.add(nameRow.getPane());
        vboxMain.add(maxResultsRow.getPane());
        vboxMain.add(sizeRow.getPane());
        vboxMain.add(updateRow.getPane());
        vboxMain.add(confirmRow.getPane());

        confirmRow.getBtn1().addActionListener(e -> jFrame_Settings.dispose());

        confirmRow.getBtn2().addActionListener(e -> {
                storeObj.setName(nameRow.getTextField().getText());
                storeObj.setMaxResult(Integer.parseInt(maxResultsRow.getTextField().getText()));
                storeObj.setUpdateCircle(Integer.parseInt(updateRow.getTextField().getText()));
                if(Double.parseDouble(sizeRow.getTextField().getText()) != oldscale) {
                    storeObj.setScale(Double.parseDouble(sizeRow.getTextField().getText()));
                    center_panel.removeAll();
                    panel_pageEnd.removeAll();
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
        jFrame_Settings.setAlwaysOnTop(true);
    }

    private String getActualDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        return dateFormat.format(date);
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
