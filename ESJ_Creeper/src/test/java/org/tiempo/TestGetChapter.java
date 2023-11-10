package org.tiempo;

import junit.framework.TestCase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.tiempo.entity.Chapter;
import org.tiempo.util.ESJConnector;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestGetChapter extends TestCase {

    public void testGetChapter() throws IOException, InterruptedException {


//        ESJConnector esjConnector = new ESJConnector();
//        Document document = Jsoup.connect("https://www.esjzone.me/detail/1689724843.html")
//                .cookies(esjConnector.getCookies())
//                .get();
//
//        JXDocument jxDocument = JXDocument.create(document);
//        List<JXNode> jxNodes = jxDocument.selN("//div[@id=\"chapterList\"]/details/a[2]");
//        Thread.sleep(3000);
//        Element element = jxNodes.get(0).asElement();
//
//        Chapter chapter = esjConnector.getChapter(element);
//
//        System.out.println(chapter);
    }
}
