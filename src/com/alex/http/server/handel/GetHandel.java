package com.alex.http.server.handel;

import com.alex.http.server.application.AlexApplication;
import com.alex.http.server.utils.FileUntils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class GetHandel implements Runnable{

    private String requestHeader;

    private Socket connect;

    public GetHandel(String requestHeader, Socket connect) {
        this.requestHeader = requestHeader;
        this.connect = connect;
    }

    private Map<String,Object> requestInfo(){
        int begin = this.requestHeader.indexOf("/");
        int end = this.requestHeader.indexOf("HTTP/") -1;
        String url = this.requestHeader.substring(begin + 1, end - 1);
        Map<String,Object> map = new HashMap<>(2);
        if (url.contains("?")) {
            int paramIndex = url.indexOf('?');
            map.put("path", url.substring(0,paramIndex));
            url = url.substring(paramIndex + 1);
            while (url.startsWith("&")) {
                url = url.substring(1);
            }
            while (url.endsWith("&")) {
                url = url.substring(0,url.length() - 1);
            }
            if (url.contains("&")) {
                String[] paramSplit = url.split("&");
                Map<String,String> param = new HashMap<>();
                for (String split : paramSplit) {
                    if (split.contains("=")) {
                        param.put(split.substring(0,split.indexOf('=')), split.substring(split.indexOf('=') + 1));
                    }
                }
                map.put("param", param);
            } else {
                Map<String,String> param = new HashMap<>();
                int d = url.indexOf('=');
                if (d != -1) {
                    param.put(url.substring(0,d),url.substring(d + 1));
                    map.put("param", param);
                }
            }
        } else {
            map.put("path", url);
        }
        return map;
    }

    private void resolve(){
        Map<String, Object> info = requestInfo();
        String path = (String) info.get("path");
        try {
            PrintWriter pw = new PrintWriter(connect.getOutputStream());
            if (!path.contains(".html")) {
                if (path.endsWith("/")) {
                    path += "index.html";
                } else {
                    path += "/index.html";
                }
            }
            if (new File(AlexApplication.staticPath + path).exists()) {
                pw.println("HTTP/1.1 200 OK");
                pw.println("Content-type:text/html");
                pw.println();
                pw.println(FileUntils.readFileContent(AlexApplication.staticPath + path));
            } else {
                pw.println("HTTP/1.1 404 NOT FOUND");
                pw.println("Content-type:text/html");
                pw.println();
                pw.println("<h1>404</h1>");
            }
            pw.flush();
            connect.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        resolve();
    }
}
