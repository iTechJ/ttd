package com.itechart.tdd.geocode;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionHelper {

    public String sendGet(String url) {
        System.out.println("Send URL: " + url);

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();

        try {
            URL getUrl = new URL(url);
            conn = (HttpURLConnection) getUrl.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch(IOException e) {
            throw new GooglePlacesException("Invalid address", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return jsonResults.toString();
    }
}
