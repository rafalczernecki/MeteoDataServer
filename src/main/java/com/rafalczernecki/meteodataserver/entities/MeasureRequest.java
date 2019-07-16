package com.rafalczernecki.meteodataserver.entities;

public class MeasureRequest {
    private Integer typeOfData;
    private Long startDate;
    private Long endDate;



    public Integer getTypeOfData() {
        return typeOfData;
    }

    public void setTypeOfData(Integer typeOfData) {
        this.typeOfData = typeOfData;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }
}
