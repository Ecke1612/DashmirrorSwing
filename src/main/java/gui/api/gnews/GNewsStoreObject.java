package gui.api.gnews;

import gui.api.StoreObject;

import java.awt.*;
import java.io.Serializable;

public class GNewsStoreObject extends StoreObject implements Serializable {

    private int updateCircle = 15;
    private Point pos = new Point(100,100);
    private double scale = 1.0;

    public int getUpdateCircle() {
        return updateCircle;
    }

    public void setUpdateCircle(int updateCircle) {
        this.updateCircle = updateCircle;
    }

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
