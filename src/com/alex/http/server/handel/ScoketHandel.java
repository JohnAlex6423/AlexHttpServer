package com.alex.http.server.handel;

import com.alex.http.server.application.AlexApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ScoketHandel implements Runnable{

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            Socket connect;
            BufferedReader br;
            while (true) {
                connect = serverSocket.accept();
                br = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                String requestHeader;
                int contentLength = 0;
                while ((requestHeader = br.readLine()) != null && !requestHeader.isEmpty()) {
                    System.out.println(requestHeader);
                    if (requestHeader.startsWith("GET")) {
                        AlexApplication.alexThreadPool.getThreadPool().execute(new GetHandel(requestHeader, connect));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
