package gui.api.weather.filestructure;

import java.text.DecimalFormat;

public class WeatherObject {

    private String city;
    private String currTemperature = "";
    private String currWind = "";
    private String currentIcon = "";

    private String[] timestamp = new String[3];
    private String[] forecastTemp = new String[3];
    private String[] forecastIcon = new String[3];

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCurrTemperature() {
        String[] split = currTemperature.split("\\.");
        return split[0] + "°C";
    }

    public void setCurrTemperature(String currTemperature) {
        this.currTemperature = currTemperature;
    }

    public String getCurrWind() {
        double dwind = Double.parseDouble(currWind);
        dwind = dwind * 3.6;
        return roundDoubleToString(dwind);
    }

    public void setCurrWind(String currWind) {
        this.currWind = currWind;
    }

    public String getCurrentIcon() {
        return currentIcon;
    }

    public void setCurrentIcon(String currentIcon) {
        this.currentIcon = currentIcon;
    }

    public String getWindInWords() {
        String sentence = "nicht erfasst";
        double w = Double.parseDouble(currWind);
        if(w < 0.2) {
            sentence = "Stille";
        }else if(w >= 0.2 && w < 5.5) {
            sentence = "schwacher Wind";
        }else if(w >= 5.5 && w < 7.9) {
            sentence = "mäßiger Wind";
        }else if(w >= 7.9 && w < 10.7) {
            sentence = "frischer Wind";
        }else if(w >= 10.7 && w < 17.1) {
            sentence = "starker Wind";
        }else if(w >= 17.1 && w < 24.4) {
            sentence = "Sturm";
        }else if(w >= 24.4) {
            sentence = "schwerer Sturm";
        }
        return sentence;
    }

    private String roundDoubleToString(double value) {
        DecimalFormat df = new DecimalFormat("####0.00");
        return df.format(value);
    }

    public void setTimestampByIndex(String timestamp, int index) {
        this.timestamp[index] = timestamp;
    }

    public String getForecastTempByIndex(int index) {
        String[] split = forecastTemp[index].split("\\.");
        return split[0] + "°C";
    }

    public void setForecastTempByIndex(String forecastTemp, int index) {
        this.forecastTemp[index] = forecastTemp;
    }

    public String getForecastIconbyIndex(int index) {
        return forecastIcon[index];
    }

    public void setForecastIconByIndex(String forecastIcon, int index) {
        this.forecastIcon[index] = forecastIcon;
    }

    public String getTimeByIndex(int index) {
        String[] s = timestamp[index].split(" ");
        String[] s1 = s[1].split(":");
        return s1[0] + ":" + s1[1];
    }
}
