package net;

import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

public final class GETRequest {
    private final int responseCode;
    private InputStream inputStream;

    public GETRequest(@NotNull URL url) throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(30000);
        connection.setRequestMethod("GET");

        responseCode = connection.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            inputStream = connection.getInputStream();
        }
    }

    public GETRequest(String url) throws IOException {
        this(new URL(url));
    }

    public int getResponseCode() {
        return responseCode;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
