package org.tiempo.util;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class NovelImgDownloader {

    private static String downloadPath = "./ESJ_Creeper/src/main/resources/temp/";

    public static String coverDownload(Document novelDocument) {
        //获取小说封面Element
        //:/body/div[3]/section/div/div[1]/div[1]/div[1]/div[1]/a/img
        JXDocument jxDocument = JXDocument.create(novelDocument);
        JXNode jxNode = jxDocument.selNOne("/body/div[3]/section/div/div[1]/div[1]/div[1]/div[1]/a/img");
        Element coverElement = jxNode.asElement();
        String coverURL = coverElement.attributes().get("src");

        try {
            BufferedImage coverIMG = ImageIO.read(new URL(coverURL));

            if (coverIMG != null) {
                File downloadFile = new File(downloadPath+"cover.jpg");
                ImageIO.write(coverIMG, "jpg", downloadFile);
                System.out.println("封面图片下载成功");
            }else {
                System.out.println("封面图片下载失败");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return downloadPath+"cover.jpg";
    }
}
