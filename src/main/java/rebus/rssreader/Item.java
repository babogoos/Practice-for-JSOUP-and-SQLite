package rebus.rssreader;

/**
 * Created by d-eye on 2017/3/3.
 */

public class Item {
    private long id;
    private String date;
    private String daynight;
    private String temperature;
    private String weather;
    public Item(long id,String date, String daynight, String temperature, String weather) {
        this.id = id;
        this.date = date;
        this.daynight = daynight;
        this.temperature = temperature;
        this.weather = weather;
    }
    public Item(){

    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDaynight() {
        return daynight;
    }

    public void setDaynight(String daynight) {
        this.daynight = daynight;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
