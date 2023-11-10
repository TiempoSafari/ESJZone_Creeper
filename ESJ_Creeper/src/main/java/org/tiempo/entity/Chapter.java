package org.tiempo.entity;


import java.util.ArrayList;
import java.util.List;

public class Chapter {
    public String name;
    public String text;
    public List<String> textList = new ArrayList<>();

    public Chapter() {
    }

    public Chapter(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public Chapter(String name, List<String> textList) {
        this.name = name;
        this.textList = textList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getTextList() {
        return textList;
    }

    public void setTextList(List<String> textList) {
        this.textList = textList;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", textList=" + textList +
                '}';
    }
}
