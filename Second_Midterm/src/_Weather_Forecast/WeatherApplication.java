package _Weather_Forecast;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

interface Display {}

class DispatcherInfo {
    private float prevTemp, prevHum, prevPreas, temp, hum, pres;

    public DispatcherInfo() {
        this.prevTemp = 0;
        this.prevHum = 0;
        this.prevPreas = 0;
        this.temp = 0;
        this.hum = 0;
        this.pres = 0;
    }

    public void setTemp(float temp) {
        this.prevTemp = this.temp;
        this.temp = temp;
    }

    public void setHum(float hum) {
        this.prevHum = this.hum;
        this.hum = hum;
    }

    public void setPres(float pres) {
        this.prevPreas = this.pres;
        this.pres = pres;
    }

    public float getPres() {
        return pres;
    }

    public float getPrevPreas() {
        return prevPreas;
    }

    public float getTemp() {
        return temp;
    }

    public float getHum() {
        return hum;
    }
}

class WeatherDispatcher {

    private Set<Display> displays;
    private DispatcherInfo dispatcherInfo;

    public WeatherDispatcher() {
        this.displays = new HashSet<>();
        this.dispatcherInfo = new DispatcherInfo();
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.dispatcherInfo.setHum(humidity);
        this.dispatcherInfo.setPres(pressure);
        this.dispatcherInfo.setTemp(temperature);

        notifyDisplays();
    }

    public void notifyDisplays() {
        this.displays.forEach(System.out::println);
        System.out.println();
    }

    public void register(Display display) {
        this.displays.add(display);
    }

    public void remove(Display display) {
        this.displays.remove(display);
    }

    public DispatcherInfo getDispatcherInfo() {
        return dispatcherInfo;
    }
}

class CurrentConditionsDisplay implements Display {

    private WeatherDispatcher weatherDispatcher;

    public CurrentConditionsDisplay(WeatherDispatcher weatherDispatcher) {
        this.weatherDispatcher = weatherDispatcher;
        this.weatherDispatcher.register(this);
    }

    @Override
    public String toString() {
        return String.format("Temperature: %.1fF\nHumidity: %.1f%%",
                this.weatherDispatcher.getDispatcherInfo().getTemp(),
                this.weatherDispatcher.getDispatcherInfo().getHum());
    }
}

class ForecastDisplay implements Display {

    private WeatherDispatcher weatherDispatcher;

    public ForecastDisplay(WeatherDispatcher weatherDispatcher) {
        this.weatherDispatcher = weatherDispatcher;
        this.weatherDispatcher.register(this);
    }

    @Override
    public String toString() {
        StringBuilder what = new StringBuilder();

        if(this.weatherDispatcher.getDispatcherInfo().getPrevPreas()
                < this.weatherDispatcher.getDispatcherInfo().getPres())
            what.append("Improving");
        else if(this.weatherDispatcher.getDispatcherInfo().getPrevPreas()
                == this.weatherDispatcher.getDispatcherInfo().getPres())
            what.append("Same");
        else
            what.append("Cooler");

        return String.format("Forecast: %s", what.toString());
    }
}

public class WeatherApplication {

    public static void main(String[] args) {
        WeatherDispatcher weatherDispatcher = new WeatherDispatcher();

        CurrentConditionsDisplay currentConditions = new CurrentConditionsDisplay(weatherDispatcher);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherDispatcher);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            weatherDispatcher.setMeasurements(Float.parseFloat(parts[0]), Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
            if(parts.length > 3) {
                int operation = Integer.parseInt(parts[3]);
                if(operation==1) {
                    weatherDispatcher.remove(forecastDisplay);
                }
                if(operation==2) {
                    weatherDispatcher.remove(currentConditions);
                }
                if(operation==3) {
                    weatherDispatcher.register(forecastDisplay);
                }
                if(operation==4) {
                    weatherDispatcher.register(currentConditions);
                }

            }
        }
    }
}