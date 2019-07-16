package com.rafalczernecki.meteodataserver;

import com.rafalczernecki.meteodataserver.entities.Server;

public class Main {

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }
}
