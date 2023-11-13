package org.tiempo.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.tiempo.entity.ChapterSpiderUnit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KNovelManager {

    private String novelURL;

    public List<ChapterSpiderUnit> chapterList = new ArrayList<>();

    public KNovelManager(String novelURL) {
        this.novelURL = novelURL;
    }

    public void getChapterList() throws IOException {
        //:/html/body/div[1]/div/div[1]/main/div[2]/section/div/ol/li*/*

        Document document = Jsoup.connect(novelURL)
                .get();

        JXDocument jxDocument = JXDocument.create(document);
        List<JXNode> jxNodes = jxDocument.selN("/body/div[1]/div/div[1]/main/div[2]/section/div/ol/li[*]/*");

        for (JXNode jxNode: jxNodes) {
            Element element = jxNode.asElement();
            ChapterSpiderUnit chapterSpiderUnit = new ChapterSpiderUnit();
            if(element.tag().toString() == "span") {
                chapterSpiderUnit.isSeparater = true;
            }

            chapterSpiderUnit.setChapterWebSource(element);
            chapterList.add(chapterSpiderUnit);
//            System.out.println(jxNode);
        }
        System.out.println();
    }

    public void printChapterList() {
        for (ChapterSpiderUnit chapter : this.chapterList) {
            System.out.println(chapter);
        }
    }

    public static void main(String[] args) throws IOException {
//        NovelManager novelManager = new NovelManager("https://kakuyomu.jp/works/1177354054882692464");
        KNovelManager KNovelManager = new KNovelManager("https://kakuyomu.jp/works/16817330664031621412");
        KNovelManager.getChapterList();
        KNovelManager.printChapterList();
    }
}
