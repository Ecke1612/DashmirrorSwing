package gui.api.clock;

import gui.api.StoreObject;

import java.awt.*;
import java.io.Serializable;

public class ClockStoreObject extends StoreObject implements Serializable {

    private Point pos = new Point(100,100);
    private double scale = 1.0;

    @Override
    public Point getPos() {
        return pos;
    }

    @Override
    public void setPos(Point pos) {
        this.pos = pos;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }
}
