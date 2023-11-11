package org.tiempo.util;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyles;
import org.tiempo.entity.Chapter;
import org.tiempo.entity.Novel;
import org.tiempo.entity.Volume;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ToWord {
    //word整体样式
    private static CTStyles wordStyles = null;

    //获取Word模板整体样式
    static {
        XWPFDocument template;
        // 读取模板文档
        try {
            template = new XWPFDocument(new FileInputStream("./ESJ_Novel_Export/src/main/resources/docxFormat/format.docx"));
            // 获得模板文档的整体样式
            wordStyles = template.getStyle();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将从网页获取的文章转换为带格式的word文档
     * @param novel 爬取的小说类
     */
    public static void novel2docx(Novel novel, String savePath) {
        // 新建的word文档对象
        XWPFDocument doc = new XWPFDocument();
        // 获取新建文档对象的样式
        XWPFStyles newStyles = doc.createStyles();
        // 关键行// 修改设置文档样式为静态块中读取到的样式
        newStyles.setStyles(wordStyles);

        XWPFParagraph p = doc.createParagraph();
        XWPFRun r = p.createRun();
        r.addCarriageReturn();

        for (Volume volume : novel.getVolumeList()) {
            XWPFParagraph title = doc.createParagraph();
            title.setStyle("1");
            XWPFRun titleRun = title.createRun();
            titleRun.setText(volume.getName());
            for (Chapter chapter : volume.getChapterList()) {
                XWPFParagraph chapterTitle = doc.createParagraph();
                chapterTitle.setStyle("2");
                XWPFRun chapterTitleRun = chapterTitle.createRun();
                chapterTitleRun.setText(chapter.getName());

                XWPFParagraph content = doc.createParagraph();
                XWPFRun contentRun = content.createRun();
                for (String paragraph : chapter.textList) {
                    contentRun.setText(paragraph);
                    contentRun.addCarriageReturn();
                }
            }
        }

        FileOutputStream fos = null;
        try {
            //通过配置文件设置保存路径。默认未项目下/repository
            fos = new FileOutputStream(savePath+novel.getNovelName()+".docx");
            doc.write(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
