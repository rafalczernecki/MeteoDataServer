package com.rafalczernecki.meteodataserver.entities;

public class SingleQuantityMeasure {
    private Long measureTimeInMillis;
    private Double value;

    public Long getMeasureTimeInMillis() {
        return measureTimeInMillis;
    }

    public void setMeasureTimeInMillis(Long measureTimeInMillis) {
        this.measureTimeInMillis = measureTimeInMillis;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
