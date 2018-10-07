package gui.api.tvprogram;


import gui.api.tvprogram.filestructure.TVObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class GetTVData {

    public ArrayList<TVObject> getData() {
        ArrayList<TVObject> tvObjects = new ArrayList<>();
        try {
            String url = "https://www.tvspielfilm.de/tv-programm/rss/jetzt.xml";

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new URL(url).openStream());

            //Normalize the XML Structure; It's just too important !!
            doc.getDocumentElement().normalize();
            NodeList lList = doc.getElementsByTagName("item");

            for (int temp = 0; temp < lList.getLength(); temp++) {
                Node node =lList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    String tvrow = eElement.getElementsByTagName("title").item(0).getTextContent();
                    String[] split = tvrow.split(" \\| ");
                    tvObjects.add(new TVObject(split[0], split[1], split[2]));
                    //System.out.println(split[0] + " " +  split[1] + " " + split[2]);
                }
            }
            return tvObjects;

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }
}
