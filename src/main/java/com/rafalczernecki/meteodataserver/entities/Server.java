package com.rafalczernecki.meteodataserver.entities;

import com.google.gson.Gson;
import com.rafalczernecki.meteodataserver.utils.DatabaseHelper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;

public class Server {

    public void startServer() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
            server.createContext("/save_data", new SaveDataHandler());
            server.createContext("/get_data", new GetDataHandler());
            server.createContext("/test_connection", new TestConnectionHandler());
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class TestConnectionHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.sendResponseHeaders(200,0);
            exchange.close();
        }
    }

    static class SaveDataHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            InputStream input = t.getRequestBody();
            String inputData = new String(input.readAllBytes());
            input.close();
            Gson gson = new Gson();
            Measure measure = gson.fromJson(inputData, Measure.class);
            DatabaseHelper databaseHelper = new DatabaseHelper();
            databaseHelper.insertMeasureIntoDatabase(measure);
        }
    }

    static class GetDataHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            InputStream input = t.getRequestBody();
            String inputData = new String(input.readAllBytes());
            input.close();

            //json input message is of type JsonArray
            //deleting brackets
            StringBuilder sb = new StringBuilder(inputData);
            sb.deleteCharAt(inputData.length()-1);
            sb.deleteCharAt(0);

            Gson gson = new Gson();
            MeasureRequest measureRequest = gson.fromJson(sb.toString(), MeasureRequest.class);
            DatabaseHelper databaseHelper = new DatabaseHelper();
            List<SingleQuantityMeasure> data = databaseHelper.getMeasureDataFromDatabase(measureRequest);
            String response = gson.toJson(data);
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}