package org.tiempo;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyles;
import org.tiempo.entity.Chapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ToWordTest {

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
     * @param title 从ESJ获取的轻小说名称（作为生成word文档的名称）
     * @param chapterList 轻小说各章节标题及内容
     */
    public void convert2Word(String title, List<Chapter> chapterList) {
        // 新建的word文档对象
        XWPFDocument doc = new XWPFDocument();
        // 获取新建文档对象的样式
        XWPFStyles newStyles = doc.createStyles();
        // 关键行// 修改设置文档样式为静态块中读取到的样式
        newStyles.setStyles(wordStyles);

        XWPFParagraph p = doc.createParagraph();
        XWPFRun r = p.createRun();
        r.addCarriageReturn();

        for (int i = 0; i < chapterList.size(); i++) {
            XWPFParagraph para1 = doc.createParagraph();
            para1.setStyle("1");
            XWPFRun run1 = para1.createRun();
            run1.setText(chapterList.get(i).getName());

            XWPFParagraph paraX = doc.createParagraph();
            XWPFRun runX = paraX.createRun();
            List<String> textList = chapterList.get(i).getTextList();
            for (int j = 0; j < textList.size(); j++) {
                runX.setText(textList.get(j));
                runX.addCarriageReturn();
            }
        }

        for (int i = 0; i < chapterList.size(); i++) {
            XWPFParagraph para1 = doc.createParagraph();
            para1.setStyle("2");
            XWPFRun run1 = para1.createRun();
            run1.setText(chapterList.get(i).getName());

            XWPFParagraph paraX = doc.createParagraph();
            XWPFRun runX = paraX.createRun();
            List<String> textList = chapterList.get(i).getTextList();
            for (int j = 0; j < textList.size(); j++) {
                runX.setText(textList.get(j));
                runX.addCarriageReturn();
            }
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(title+".docx");
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

    public static void main(String[] args) {
        ArrayList<String> s1 = new ArrayList<>();
        s1.add("1111111111111111111");
        s1.add("2222222222222");
        s1.add("qdh24teaerhsa");
        Chapter c1 = new Chapter("a",s1);

        ArrayList<String> s2 = new ArrayList<>();
        s2.add("623647643223qgr");
        s2.add("43hrefbdzxyu54hwet");
        s2.add("53uhwte4key5t63q2");
        s2.add("2q4uy3w54i6ios7le5kudtf");
        Chapter c2 = new Chapter("b",s2);

        ArrayList<String> s3 = new ArrayList<>();
        s3.add("4563ikjrtnsdmderasu5wsej");
        s3.add("534ujisrtekerudtkws467k");
        s3.add("24yuhtjdsnmjdr6ij4jtyjkd");
        s3.add("22wsfgdvgjmk,68urytsrrgba");
        Chapter c3 = new Chapter("c",s3);

        List<Chapter> chapterList = new ArrayList<>();
        chapterList.add(c1);
        chapterList.add(c2);
        chapterList.add(c3);

        ToWordTest toWord = new ToWordTest();
        toWord.convert2Word("test",chapterList);
    }
}
