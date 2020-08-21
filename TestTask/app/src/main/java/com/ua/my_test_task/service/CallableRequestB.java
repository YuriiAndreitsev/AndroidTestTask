package com.ua.my_test_task.service;

import android.util.Log;

import com.ua.my_test_task.model.RequestBObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Callable;

import javax.net.ssl.HttpsURLConnection;

public class CallableRequestB implements Callable<RequestBObject> {
    String url_parameter;
    StringBuilder data;
    RequestBObject requestBObject;
    URL url;

    public CallableRequestB() {
    }

    public CallableRequestB(String url_parameter) {
        this.url_parameter = url_parameter;
    }

    @Override
    public RequestBObject call() throws Exception {
        data = new StringBuilder();
        try {
            url = new URL("https://demo0040494.mockable.io/api/v1/object/" + url_parameter);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            InputStream is = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                if (line != null) {
                    data = data.append(line);
                }
            }
            JSONObject object = new JSONObject(data.toString());
            requestBObject = new RequestBObject();
            requestBObject.setType((String) object.get("type"));
            if (requestBObject.getType().equals("text")) {
                requestBObject.setContents((String) object.get("contents"));
            } else {
                requestBObject.setContents((String) object.get("url"));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        Log.i("OBJECT ===== BBBBBB", requestBObject.getType());
        Log.i("OBJECT ===== BBBBBB", requestBObject.getContents());
        return requestBObject;
    }

}
