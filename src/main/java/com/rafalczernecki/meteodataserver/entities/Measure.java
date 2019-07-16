package com.rafalczernecki.meteodataserver.entities;

public class Measure {

    private Long measureTimeInSeconds;
    private Double temperature;
    private Double airPressure;
    private Double humidity;

    public Double getTemperature() {
        return temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public Double getAirPressure() {
        return airPressure;
    }

    public Long getMeasureTimeInSeconds() {
        return measureTimeInSeconds;
    }

    public void setMeasureTimeInSeconds(Long measureTimeInSeconds) {
        this.measureTimeInSeconds = measureTimeInSeconds;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setAirPressure(Double airPressure) {
        this.airPressure = airPressure;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Long getMeasureTimeInMillis() {
        return measureTimeInSeconds * 1000;
    }
}
