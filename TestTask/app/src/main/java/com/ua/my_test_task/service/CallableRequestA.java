package com.ua.my_test_task.service;

import android.util.Log;

import com.ua.my_test_task.model.RequestAObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.net.ssl.HttpsURLConnection;

public class CallableRequestA implements Callable<List<RequestAObject>> {
    StringBuilder data;
    List<RequestAObject> objectList;
    URL url;
    RequestAObject tempRequestAObject = null;
    @Override
    public List<RequestAObject> call() throws Exception {
        data = new StringBuilder();
        objectList = new ArrayList<RequestAObject>();
        try {
            url = new URL("https://demo0040494.mockable.io/api/v1/trending");
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

            JSONArray array = new JSONArray(data.toString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = (JSONObject) array.get(i);
                tempRequestAObject = new RequestAObject();
                tempRequestAObject.setId((Integer) object.get("id"));
                tempRequestAObject.setTitle((String) object.get("title"));

                if (!tempRequestAObject.getTitle().equals("Game")) {
                    objectList.add(tempRequestAObject);
                    Log.i("TEMP AAAA OBJECT ===", tempRequestAObject.getTitle());
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return objectList;
    }
}
