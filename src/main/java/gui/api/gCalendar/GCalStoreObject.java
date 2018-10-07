package gui.api.gCalendar;

import gui.api.StoreObject;

import java.awt.*;
import java.io.Serializable;

public class GCalStoreObject extends StoreObject implements Serializable {
    private String name = "Eike";
    private int maxResult = 5;
    private int updateCircle = 5;
    private Point pos = new Point(100,100);
    private double scale = 1.0;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

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
