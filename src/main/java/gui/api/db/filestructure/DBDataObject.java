package gui.api.db.filestructure;

public class DBDataObject {

    private String abfahrt;
    private String ankunft;
    private String umstieg;
    private String dauer;
    private String zug;
    private String price;
    private String echteAbfahrtzeit;

    public DBDataObject() {

    }

    public String getEchteAbfahrtzeit() {
        return echteAbfahrtzeit;
    }

    public void setEchteAbfahrtzeit(String echteAbfahrtzeit) {
        this.echteAbfahrtzeit = echteAbfahrtzeit;
    }

    public void setAbfahrt(String abfahrt) {
        this.abfahrt = abfahrt;
    }

    public void setAnkunft(String ankunft) {
        this.ankunft = ankunft;
    }

    public void setUmstieg(String umstieg) {
        this.umstieg = umstieg;
    }

    public void setDauer(String dauer) {
        this.dauer = dauer;
    }

    public void setZug(String zug) {
        this.zug = zug;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAbfahrt() {
        return abfahrt;
    }

    public String getAnkunft() {
        return ankunft;
    }

    public String getUmstieg() {
        return umstieg;
    }

    public String getDauer() {
        return dauer;
    }

    public String getZug() {
        return zug;
    }

    public String getPrice() {
        return price;
    }
}
