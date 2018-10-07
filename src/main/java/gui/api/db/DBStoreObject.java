package gui.api.db;

import gui.api.StoreObject;

import java.awt.*;
import java.io.Serializable;

public class DBStoreObject extends StoreObject implements Serializable {

    private int updateCircle = 5;
    private Point pos = new Point(100,100);
    private double scale = 1.0;
    private int maxResults = 2;
    private String startCity = "Emden";
    private String stopCity = "Oldenburg(Oldb)";

    public int getUpdateCircle() {
        return updateCircle;
    }

    public void setUpdateCircle(int updateCircle) {
        this.updateCircle = updateCircle;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    @Override
    public Point getPos() {
        return pos;
    }

    @Override
    public void setPos(Point pos) {
        this.pos = pos;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getStopCity() {
        return stopCity;
    }

    public void setStopCity(String stopCity) {
        this.stopCity = stopCity;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }
}
