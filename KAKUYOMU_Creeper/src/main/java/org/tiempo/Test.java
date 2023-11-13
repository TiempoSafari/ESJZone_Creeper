package org.tiempo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.io.IOException;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
        //:/html/body/div[1]/div[1]/div[1]/div/div/div/p[1]

        Document document = Jsoup.connect("https://kakuyomu.jp/works/16817330663722833570/episodes/16817330663722869163")
                .get();

        JXDocument jxDocument = JXDocument.create(document);
        List<JXNode> jxNodes = jxDocument.selN("/body/div[1]/div[1]/div[1]/div/div/div/*");
        System.out.println(jxNodes.toString());
    }
}
