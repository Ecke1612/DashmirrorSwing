package gui.api.db;

import gui.api.db.filestructure.DBDataObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class GetDBData {


    public GetDBData(){

    }

    public ArrayList<DBDataObject> getData(String start, String stop){
        ArrayList<DBDataObject> dbObjects = new ArrayList<>();
        String dburl = "http://mobile.bahn.de/bin/query.exe/dox?S=" + start + "&Z=" + stop + "&timeSel=depart&start=1";

        try {
            Document doc = Jsoup.connect(dburl).get();//Get data from input location
            Element table = doc.select("table.ovTable.clicktable").first();
            Elements tr = table.select("tr.scheduledCon");

            Elements time = tr.select("td.overview.timelink");
            Elements realTime = tr.select("td.overview.tprt");
            //Elements dauer = tr.select("td.overview");
            Elements trains = tr.select("td.overview.iphonepfeil");



            for(int i = 0; i < time.size(); i++) {
                DBDataObject dbobj = new DBDataObject();
                String[] col1 = (time.get(i).text()).split(" ");

                dbobj.setAbfahrt(col1[0]);
                dbobj.setAnkunft(col1[1]);

                String[] col2 = (realTime.get(i).text()).split(" ");
                dbobj.setEchteAbfahrtzeit(col2[0]);

                String[] col3 = (realTime.next().get(i).text()).split(" ");
                dbobj.setUmstieg(col3[0]);
                dbobj.setDauer(col3[1]);

                String[] col4 = (trains.get(i).text()).split(" ");
                dbobj.setZug(col4[0]);
                for(String s : col4) {
                    double test = 0.0;
                    try {
                        String temp = s.replace(',', '.');
                        test = Double.parseDouble(temp);
                    } catch (Exception e) {
                    }
                    dbobj.setPrice(Double.toString(test));
                }

                dbObjects.add(dbobj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbObjects;
    }
}
