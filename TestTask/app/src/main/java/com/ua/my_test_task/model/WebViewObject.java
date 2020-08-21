package com.ua.my_test_task.model;

public class WebViewObject {
    private String url_string;

    @Override
    public String toString() {
        return "WebViewObject{" +
                "url_string='" + url_string + '\'' +
                '}';
    }

    public String getUrl_string() {
        return url_string;
    }

    public void setUrl_string(String url_string) {
        this.url_string = url_string;
    }
}
