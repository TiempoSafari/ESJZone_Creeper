package org.tiempo.util;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyles;
import org.tiempo.entity.Chapter;
import org.tiempo.entity.Novel;
import org.tiempo.entity.Volume;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
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
    public static void novel2docx(Novel novel, String savePath, boolean isConvert) {
        // 新建的word文档对象
        XWPFDocument doc = new XWPFDocument();
        // 获取新建文档对象的样式
        XWPFStyles newStyles = doc.createStyles();
        // 关键行// 修改设置文档样式为静态块中读取到的样式
        newStyles.setStyles(wordStyles);

//        XWPFParagraph p = doc.createParagraph();
//        XWPFRun r = p.createRun();
//        r.addCarriageReturn();

        //插入小说封面图片
        XWPFParagraph novelCover = doc.createParagraph();
        XWPFRun novelCoverRun = novelCover.createRun();
        try {
            insertImage(novelCoverRun,novel.getNovelImgPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }

        //写入简介


        //写入章节
        for (Volume volume : novel.getVolumeList()) {
            XWPFParagraph title = doc.createParagraph();
            title.setStyle("1");
            XWPFRun titleRun = title.createRun();

            String volumeName = volume.getName();
            if(isConvert) {
                volumeName = ZhConverterUtil.toSimple(volumeName);
            }

            titleRun.setText(volumeName);
            for (Chapter chapter : volume.getChapterList()) {
                XWPFParagraph chapterTitle = doc.createParagraph();
                chapterTitle.setStyle("2");
                XWPFRun chapterTitleRun = chapterTitle.createRun();

                String chapterName = chapter.getName();
                if(isConvert) {
                    chapterName = ZhConverterUtil.toSimple(chapterName);
                }

                chapterTitleRun.setText(chapterName);

                XWPFParagraph content = doc.createParagraph();
                XWPFRun contentRun = content.createRun();
                for (String paragraph : chapter.textList) {

                    if(isConvert) {
                        paragraph = ZhConverterUtil.toSimple(paragraph);
                    }

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

    public static void insertImage(XWPFRun run, String imagePath) throws IOException, InvalidFormatException {
        File imageFile = new File(imagePath);
        InputStream imageStream = new FileInputStream(imageFile);
        BufferedImage read = ImageIO.read(imageFile);

        // 插入图片
//        run.addPicture(imageStream, XWPFDocument.PICTURE_TYPE_JPEG, imageFile.getName(), Units.toEMU(300), Units.toEMU(200)); // 图片大小可根据需要调整
        run.addPicture(imageStream, XWPFDocument.PICTURE_TYPE_JPEG, imageFile.getName(), Units.toEMU(400), Units.toEMU(400*read.getHeight()/read.getWidth())); // 图片大小可根据需要调整

        // 设置图片位置
//        XWPFPicture picture = run.getEmbeddedPictures().get(0);
//        if (picture != null) {
//            picture.getCTPicture().getSpPr().addNewXfrm().addNewOff().setX(0);
//            picture.getCTPicture().getSpPr().addNewXfrm().addNewOff().setY(0);
//        }

        run.addBreak();
    }
}
