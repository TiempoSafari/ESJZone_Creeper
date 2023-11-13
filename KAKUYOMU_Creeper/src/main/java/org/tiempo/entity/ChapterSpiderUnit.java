package org.tiempo.entity;

import org.jsoup.nodes.Element;

public class ChapterSpiderUnit {

    public Boolean isDownload = false;

    public Boolean isOccupy = false;

    public Boolean isSeparater = false;

    private Element chapterWebSource;

    public ChapterSpiderUnit() {
    }

    public Element getChapterWebSource() {
        return chapterWebSource;
    }

    public void setChapterWebSource(Element chapterWebSource) {
        this.chapterWebSource = chapterWebSource;
    }

    @Override
    public String toString() {
        return (isSeparater?"🔖":"\t📖")+":"+chapterWebSource.text();
    }
}
