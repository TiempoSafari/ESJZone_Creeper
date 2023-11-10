package org.tiempo.entity;

import org.jsoup.nodes.Element;

public class SpiderUnit {
    public Boolean isSeparater;

    public Boolean isDownload = false;

    public Boolean isOccupy = false;

    private Element chapterWebSource;

    private Chapter chapter;

    public SpiderUnit() {
    }

    public Element getChapterWebSource() {
        return chapterWebSource;
    }

    public void setChapterWebSource(Element chapterWebSource) {
        this.chapterWebSource = chapterWebSource;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    @Override
    public String toString() {
        return (isSeparater?"🔖":"\t📖")+":"+chapterWebSource.text();
    }
}
