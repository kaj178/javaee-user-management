package com.anhkhoa.dataexport.httpclient;

import com.anhkhoa.model.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class UserAPI {
    public List<User> getMethod(String link) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(link))
                .header("content-type", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return convertToJsonObject(response.body()).getData();
    }

    private UserListResponse convertToJsonObject(String json) {
        return new Gson().fromJson(json, UserListResponse.class);
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
