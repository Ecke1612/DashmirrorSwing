package gui.api.weather;

import gui.api.weather.filestructure.WeatherObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;

public class GetWeatherData implements Serializable {

    private String currWeaterURL = "http://api.openweathermap.org/data/2.5/";
    private final String key = "4e46f87c528cdc9447f329001b9d171a";
    private String currWeather = "weather";
    private String forecastWeather = "forecast";
    private WeatherObject wobj = new WeatherObject();


    public GetWeatherData(String city) {
        wobj.setCity(city);
    }

    public WeatherObject getWeather() {
        checkCurrentWeather("weather");
        checkCurrentWeather("forecast");
        return wobj;
    }

    private void checkCurrentWeather(String lookfor) {

        URL url = null;
        try {
            url = new URL( currWeaterURL + lookfor + "?q=" + wobj.getCity() + "&appid=" + key + "&units=metric");
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
            con.disconnect();

            if(lookfor.equals(currWeather)) analyseCurrWeatherData(inline);
            else if(lookfor.equals(forecastWeather)) analyseForcastData(inline);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void analyseCurrWeatherData(String inline) {
        JSONParser parser = new JSONParser();

        JSONObject jobj = null;
        try {
            jobj = (JSONObject)parser.parse(inline);
            JSONObject mainObj = (JSONObject) jobj.get("main");
            JSONArray weatherArray = (JSONArray) jobj.get("weather");
            JSONObject weatherObj = (JSONObject) weatherArray.get(0);
            JSONObject windObj = (JSONObject) jobj.get("wind");

            wobj.setCurrTemperature(mainObj.get("temp").toString());
            wobj.setCurrWind(windObj.get("speed").toString());
            wobj.setCurrentIcon(weatherObj.get("icon").toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private void analyseForcastData(String inline) {

        JSONParser parser = new JSONParser();
        JSONObject jobj = null;
        try {
            for(int i = 1; i < 4; i++) {
                jobj = (JSONObject) parser.parse(inline);
                JSONArray mainArray = (JSONArray) jobj.get("list");
                JSONObject firstObject = (JSONObject) mainArray.get(i);
                JSONObject mainObj = (JSONObject) firstObject.get("main");
                JSONArray weatherArray = (JSONArray) firstObject.get("weather");
                JSONObject weatherObj = (JSONObject) weatherArray.get(0);

                wobj.setTimestampByIndex(firstObject.get("dt_txt").toString(), i-1);
                wobj.setForecastTempByIndex(mainObj.get("temp").toString(), i-1);
                wobj.setForecastIconByIndex(weatherObj.get("icon").toString(), i-1);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
