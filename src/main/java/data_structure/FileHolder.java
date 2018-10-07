package data_structure;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class FileHolder {

    private Image icon_add;
    private Image icon_fullscreen;
    private Image ham_button;

    public FileHolder() {
        try {
            icon_add = ImageIO.read(getClass().getResource("/icons/add_button_32.png"));
            icon_fullscreen = ImageIO.read(getClass().getResource("/icons/fullscreen_16.png"));
            ham_button = ImageIO.read(getClass().getResource("/icons/ham_button_20.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image getIcon_add() {
        return icon_add;
    }

    public Image getIcon_fullscreen() {
        return icon_fullscreen;
    }

    public Image getHam_button() {
        return ham_button;
    }

    public Image getWeatherIcon(String file) {
        try {
            return ImageIO.read(getClass().getResource("/img/weather/256/" + file + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
