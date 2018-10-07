package gui.api.gnews.filestructure;

public class GNewsDataObject {

    private String source;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String content;

    public GNewsDataObject(String source, String title, String description, String url, String urlToImage, String content) {
        this.source = source;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getContent() {
        return content;
    }
}
