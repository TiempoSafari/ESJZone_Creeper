package org.tiempo;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateWordDocument {
    public static void main(String[] args) {
        try {
            // 创建一个新的Word文档
            XWPFDocument document = new XWPFDocument();

            // 添加标题
            XWPFParagraph titleParagraph = document.createParagraph();
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = titleParagraph.createRun();
            titleRun.setText("这是标题");
            titleRun.setBold(true);
            titleRun.setFontSize(16);

            // 添加副标题
            XWPFParagraph subTitleParagraph = document.createParagraph();
            subTitleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun subTitleRun = subTitleParagraph.createRun();
            subTitleRun.setText("这是副标题");
            subTitleRun.setFontSize(14);

            // 保存文档到文件
            FileOutputStream out = new FileOutputStream("example.docx");
            document.write(out);
            out.close();

            System.out.println("Word文档生成成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}