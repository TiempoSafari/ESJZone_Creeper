package org.tiempo;

import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlCursor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class GenerateTableOfContents {
    public static void main(String[] args) {
        try {
            // 创建一个新的Word文档
            XWPFDocument document = new XWPFDocument();

            // 添加标题1
            XWPFParagraph title1 = document.createParagraph();
            title1.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun title1Run = title1.createRun();
            title1Run.setText("章节 1");
            title1Run.setBold(true);
            title1Run.setFontSize(16);

            // 添加标题2
            XWPFParagraph title2 = document.createParagraph();
            title2.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun title2Run = title2.createRun();
            title2Run.setText("子章节 1.1");
            title2Run.setBold(true);
            title2Run.setFontSize(14);

            // 保存标题和内容的信息以便后续创建目录
            CustomParagraph customTitle1 = new CustomParagraph(title1Run, 1);
            CustomParagraph customTitle2 = new CustomParagraph(title2Run, 2);

            // 添加目录
            createTableOfContents(document, customTitle1, customTitle2);

            // 保存文档到文件
            FileOutputStream out = new FileOutputStream("table_of_contents.docx");
            document.write(out);
            out.close();

            System.out.println("Word文档生成成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 自定义段落类，用于保存标题和级别信息
    static class CustomParagraph {
        XWPFRun run;
        int level;

        CustomParagraph(XWPFRun run, int level) {
            this.run = run;
            this.level = level;
        }
    }

    // 创建目录
    static void createTableOfContents(XWPFDocument document, CustomParagraph... paragraphs) {
        XWPFParagraph toc = document.createParagraph();
        XWPFRun tocRun = toc.createRun();
        tocRun.setText("目录");
        tocRun.setFontSize(14);

        for (CustomParagraph customParagraph : paragraphs) {
            XWPFParagraph tocEntry = document.createParagraph();
            XWPFRun tocEntryRun = tocEntry.createRun();
            int level = customParagraph.level;
            for (int i = 0; i < level; i++) {
                tocEntryRun.addTab();
            }
            tocEntryRun.setText(customParagraph.run.getText(0)); // 设置目录项的文本内容
            tocEntryRun.setFontSize(12);
        }
    }
}