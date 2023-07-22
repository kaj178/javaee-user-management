package com.anhkhoa.dataexport.httpclient;

import com.anhkhoa.model.User;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserListResponse {
    @SerializedName("data") // Assign response to just get data, not status
    private ArrayList<User> data;

    public ArrayList<User> getData() {
        return data;
    }
}
