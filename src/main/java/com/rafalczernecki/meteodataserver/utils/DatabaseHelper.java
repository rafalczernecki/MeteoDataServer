package com.rafalczernecki.meteodataserver.utils;

import com.rafalczernecki.meteodataserver.entities.Measure;
import com.rafalczernecki.meteodataserver.entities.MeasureRequest;
import com.rafalczernecki.meteodataserver.entities.SingleQuantityMeasure;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private static final String DB__RELATIVE_PATH = "measures.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:" + DB__RELATIVE_PATH;
    private static final String TABLE_MEASURES = "measures";
    private static final String MEASURE_TIME_IN_MILLIS = "measureTimeInMillis";
    private static final String COLUMN_TEMPERATURE = "temperature";
    private static final String COLUMN_AIR_PRESSURE = "airPressure";
    private static final String COLUMN_HUMIDITY = "humidity";
    private static final String COLUMN_VALUE = "value";

    private static final String SELECT_DATE_RANGE_FIRST_PART = " FROM " + TABLE_MEASURES + " WHERE " +
            "(" + MEASURE_TIME_IN_MILLIS + " >=";
    private static final String SELECT_DATE_RANGE_SECOND_PART = " AND " + MEASURE_TIME_IN_MILLIS + " <= ";


    public void insertMeasureIntoDatabase(Measure measure) {

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             Statement statement = conn.createStatement()) {

            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_MEASURES +
                    "(" + MEASURE_TIME_IN_MILLIS + " INTEGER, " +
                    COLUMN_TEMPERATURE + " REAL, " +
                    COLUMN_AIR_PRESSURE + " REAL, " +
                    COLUMN_HUMIDITY + " REAL" + ")");

            statement.execute("INSERT INTO " + TABLE_MEASURES + " (" +
                    MEASURE_TIME_IN_MILLIS + ", " +
                    COLUMN_TEMPERATURE + ", " +
                    COLUMN_AIR_PRESSURE + ", " +
                    COLUMN_HUMIDITY + ") " +
                    "VALUES(" +
                    measure.getMeasureTimeInMillis() + ", " +
                    measure.getTemperature() + ", " +
                    measure.getAirPressure() + ", " +
                    measure.getHumidity() + ")");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<SingleQuantityMeasure> getMeasureDataFromDatabase(MeasureRequest measureRequest) {

        StringBuilder sb = new StringBuilder("SELECT " + MEASURE_TIME_IN_MILLIS + ", ");
        sb.append(getTypeOfData(measureRequest.getTypeOfData()) + " AS " + COLUMN_VALUE);
        sb.append(SELECT_DATE_RANGE_FIRST_PART + measureRequest.getStartDate());
        sb.append(SELECT_DATE_RANGE_SECOND_PART + measureRequest.getEndDate());
        sb.append(")");

        try(Connection conn = DriverManager.getConnection(CONNECTION_STRING);
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(sb.toString())) {

            List<SingleQuantityMeasure> data = new ArrayList<>();
            while(results.next()) {
                SingleQuantityMeasure measure = new SingleQuantityMeasure();
                measure.setMeasureTimeInMillis(results.getLong(MEASURE_TIME_IN_MILLIS));
                measure.setValue(results.getDouble(COLUMN_VALUE));
                data.add(measure);
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getTypeOfData(int type) {
        switch(type) {
            case 2:
                return "airPressure";
            case 3:
                return "humidity";
            default:
                return "temperature";
        }
    }
}
