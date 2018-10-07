package gui.guiControlls;

import gui.ParentController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseMotionEvent extends MouseAdapter {

    private JPanel panel;

    private Point location;
    private MouseEvent pressed;
    private ParentController controller;

    public MouseMotionEvent(ParentController controller) {
        this.controller = controller;
        this.panel = controller.getPanel();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Component component = panel;
        location = component.getLocation(location);
        int x = location.x - pressed.getX() + e.getX();
        int y = location.y - pressed.getY() + e.getY();
        component.setLocation(x,y);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = e;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        controller.save();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
