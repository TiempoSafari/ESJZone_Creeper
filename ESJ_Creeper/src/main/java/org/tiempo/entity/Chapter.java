package org.tiempo.entity;

import org.jsoup.nodes.Element;
import java.util.ArrayList;
import java.util.List;

/**
 * 储存小说爬取后的章节信息
 */
public class Chapter {
    //章节名称
    public String name;

    public String text;
    //章节内容，每段为String类型用于导出Word格式
    public List<String> textList = new ArrayList<>();
    //章节内容，每段为Element类型用于导出Epub格式
    public List<Element> textElementList = new ArrayList<>();

    //Constructor
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

    //Getter&Setter
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
                ", textList=" + textList +
                '}';
    }
}
