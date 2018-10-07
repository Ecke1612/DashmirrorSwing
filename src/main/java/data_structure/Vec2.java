package data_structure;

import java.io.Serializable;

public class Vec2 implements Serializable {
    private int xi;
    private int yi;
    private double xd;
    private double yd;

    public Vec2(int xi, int yi) {
        this.xi = xi;
        this.yi = yi;
        xd = xi;
        yd = yi;
    }

    public Vec2(double xd, double yd) {
        this.xd = xd;
        this.yd = yd;
        xi = (int) xd;
        yi = (int) yd;
    }

    public int getXi() {
        return xi;
    }

    public int getYi() {
        return yi;
    }

    public double getXd() {
        return xd;
    }

    public double getYd() {
        return yd;
    }

    public void setXi(int xi) {
        this.xi = xi;
        xd = xi;
    }

    public void setYi(int yi) {
        this.yi = yi;
        yd = yi;
    }

    public void setXd(double xd) {
        this.xd = xd;
        xi = (int) xd;
    }

    public void setYd(double yd) {
        this.yd = yd;
        yi = (int) yd;
    }
}
