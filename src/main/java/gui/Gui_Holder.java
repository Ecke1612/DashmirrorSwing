package gui;

import java.awt.*;

public class Gui_Holder {

    public static Font getPrimaryFont(int size, boolean bold, double scale) {
        size = (int) (size  * scale);
        if(bold) return new Font("Tahoma", Font.BOLD, size);
        else return new Font("Tahoma", Font.PLAIN, size);
    }

    public static Font getPrimaryFont(int size, boolean bold) {
        if(bold) return new Font("Tahoma", Font.BOLD, size);
        else return new Font("Tahoma", Font.PLAIN, size);
    }

    /*
    public static Font getMicrosoftNewTaiLue(int size, boolean bold) {
        if(bold) return new Font("Microsoft New Tai Lue", Font.BOLD, size);
        else return new Font("Microsoft New Tai Lue", Font.PLAIN, size);
    }*/

}
