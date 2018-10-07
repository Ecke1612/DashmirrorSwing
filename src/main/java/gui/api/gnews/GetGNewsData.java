package gui.api.gnews;

import gui.api.gnews.filestructure.GNewsDataObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class GetGNewsData {

    private String key = "16c49b91e1e64f6b994be4465def927d";
    private String apiurl = "https://newsapi.org/v2/top-headlines?";
    private String country = "country=de&";

    private ArrayList<GNewsDataObject> newsDataObjects = new ArrayList<>();

    public ArrayList<GNewsDataObject> getNews() {
        URL url = null;
        try {
            url = new URL(apiurl + country + "apiKey=" + key);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.connect();
            String inline = "";
            int responscode = con.getResponseCode();
            if(responscode !=200) {
                throw new RuntimeException("HttpResponseCode:"+responscode);
            } else {
                Scanner sc = new Scanner(url.openStream());
                while(sc.hasNext()){
                    inline +=sc.nextLine();
                }
                //System.out.println(inline);
                sc.close();
            }

            JSONParser parser = new JSONParser();

            JSONObject jobj = (JSONObject)parser.parse(inline);
            JSONArray articleArray = (JSONArray) jobj.get("articles");
            System.out.println();

            int totalResults = Integer.parseInt(jobj.get("totalResults").toString());
            for(int i = 0; i < totalResults; i++) {
                JSONObject articleObj = (JSONObject) articleArray.get(i);
                JSONObject sourceObj = (JSONObject) articleObj.get("source");
                String source = "";
                if(testfornull(sourceObj.get("name"))) {
                    source = sourceObj.get("name").toString();
                }
                String title = "";
                if(testfornull(articleObj.get("title"))) {
                    title = articleObj.get("title").toString();
                }
                String description = "";
                if(testfornull(articleObj.get("description"))){
                    description = articleObj.get("description").toString();
                }
                String urltoarticle = "";
                if(testfornull(articleObj.get("url"))) {
                    urltoarticle = articleObj.get("url").toString();
                }
                String urlToImage = "";
                if(testfornull(articleObj.get("urlToImage"))) {
                    urlToImage = articleObj.get("urlToImage").toString();
                }
                String content = "";
                if(testfornull(articleObj.get("content"))) {
                    content = articleObj.get("content").toString();
                }
                newsDataObjects.add(new GNewsDataObject(source, title, description, urltoarticle, urlToImage, content));
            }
            con.disconnect();
            return newsDataObjects;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean testfornull(Object jobj) {
        if(jobj == null) return false;
        else return true;
    }

}
