package com.anhkhoa.dataexport.httpclient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserAPI {
    public String getMethod(String link) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(link))
                .header("content-type", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

//    public static void main(String[] args) {
//        try {
//            String url = "http://localhost:8080/demorest/api/users/v1";
//            System.out.println(new UserAPI().getMethod(url));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
