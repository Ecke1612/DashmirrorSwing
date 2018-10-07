package gui.api.weather;


import gui.api.StoreObject;

import java.awt.*;
import java.io.Serializable;

public class StoreWeatherObject extends StoreObject implements Serializable{
    private String city = "Emden";
    private int updateCircle = 15;
    private Point pos = new Point(100,100);
    private double scale = 1.0;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
