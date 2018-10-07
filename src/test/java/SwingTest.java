import gui.Gui_Holder;
import main.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingTest {

    private JFrame frame = new JFrame();
    private JPanel panelOnFrameCenter = new JPanel();
    private JPanel panel = new JPanel();
    private JPanel panel_top = new JPanel();
    private int WIDTH = 180;
    private int HEIGHT = 200;

    public SwingTest() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLayout(new BorderLayout());
        frame.setBackground(Color.BLACK);
        frame.setTitle("Dashmirror test");

        panelOnFrameCenter.setLayout(null);
        panelOnFrameCenter.setBackground(Color.yellow);

        standardGui();
        initGui();

        frame.add(panelOnFrameCenter, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void initGui() {
        JPanel vboxMain = new JPanel();
        vboxMain.setLayout(new BoxLayout(vboxMain, BoxLayout.Y_AXIS));

        JPanel hbox_top = new JPanel();
        hbox_top.setLayout(new BoxLayout(hbox_top, BoxLayout.X_AXIS));

        panelOnFrameCenter.add(panel);

        JLabel weather_label = new JLabel();
        JLabel label_temp = new JLabel();

        ImageIcon weatherImg = new ImageIcon(App.fileHolder.getWeatherIcon("01d"));

        Image image = weatherImg.getImage(); // transform it
        Image newimg = image.getScaledInstance(64, 64,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        weatherImg = new ImageIcon(newimg);  // transform it back

        weather_label.setIcon(weatherImg);
        weather_label.setBounds(0,25,64,64);
        styleLabel(weather_label);

        label_temp.setText("25°C");
        label_temp.setFont(Gui_Holder.getPrimaryFont(38,true));
        label_temp.setForeground(Color.white);
        label_temp.setBounds(64, 30, 150,30);

        hbox_top.add(weather_label);
        hbox_top.add(Box.createRigidArea(new Dimension(15,0)));
        hbox_top.add(label_temp);

        vboxMain.add(hbox_top);

        panel.add(vboxMain);
    }

    private void standardGui() {
        panel.setSize(WIDTH,HEIGHT);
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.cyan);
        //panel.setOpaque(false);


        panel_top.setLayout(new BorderLayout());
        panel_top.setBounds(0,0,WIDTH, 15);
        panel_top.setOpaque(false);

        JButton btn_settings = new JButton();
        JButton btn_delete = new JButton("X");


        panel.setBounds(200,200,WIDTH,HEIGHT);
        //panel.setBackground(null);


        btn_settings.setIcon(new ImageIcon(App.fileHolder.getHam_button()));
        btn_settings.setBounds(0,0,20,20);


        btn_delete.setBounds(WIDTH - 16, 0,16,16);


        btn_settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        btn_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        panel.add(panel_top, BorderLayout.NORTH);

        panel_top.add(btn_settings, BorderLayout.LINE_START);
        panel_top.add(btn_delete, BorderLayout.LINE_END);
    }

    public void styleLabel(JLabel label) {
        label.setBackground(null);
        label.setBorder(null);
    }

}
