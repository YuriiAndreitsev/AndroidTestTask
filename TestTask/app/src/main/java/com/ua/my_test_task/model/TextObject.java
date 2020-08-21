package com.ua.my_test_task.model;

public class TextObject {
    private String content;

    @Override
    public String toString() {
        return "TextObject{" +
                "content='" + content + '\'' +
                '}';
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
