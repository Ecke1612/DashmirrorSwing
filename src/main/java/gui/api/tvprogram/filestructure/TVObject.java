package gui.api.tvprogram.filestructure;

public class TVObject {

    private String time;
    private String channel;
    private String title;

    public TVObject(String time, String channel, String title) {
        this.time = time;
        this.channel = channel;
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public String getChannel() {
        return channel;
    }

    public String getTitle() {
        return title;
    }
}
