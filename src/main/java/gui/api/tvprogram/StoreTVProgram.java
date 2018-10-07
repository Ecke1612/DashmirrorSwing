package gui.api.tvprogram;

import gui.api.StoreObject;

import java.awt.*;
import java.io.Serializable;

public class StoreTVProgram extends StoreObject implements Serializable {

    private int updateCircle = 30;
    private Point pos = new Point(100,100);
    private double scale = 1.0;
    private boolean[] channels = new boolean[20];

    public StoreTVProgram() {
        for(int i = 0; i < 20; i++) {
            channels[i] = false;
        }
    }

    public boolean[] getChannels() {
        return channels;
    }

    public void setChannelsByIndex(boolean channels, int index) {
        this.channels[index] = channels;
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
