package org.tiempo.entity;

import java.util.List;

public class Volume {
    private String name;
    private List<Chapter> chapterList;

    public Volume() {
    }

    public Volume(String name) {
        this.name = name;
    }

    public Volume(List<Chapter> chapterList) {
        this.chapterList = chapterList;
    }

    public List<Chapter> getChapterList() {
        return chapterList;
    }

    public void setChapterList(List<Chapter> chapterList) {
        this.chapterList = chapterList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Volume:" + name + '\n' +
                "chapterList=" + chapterList;
    }
}
