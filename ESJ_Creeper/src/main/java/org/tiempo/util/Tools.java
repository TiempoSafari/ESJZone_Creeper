package org.tiempo.util;

import org.tiempo.entity.Chapter;
import org.tiempo.entity.Novel;
import org.tiempo.entity.Volume;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Tools {

    public static void loading(String load, Integer time) {
        System.out.println("正在等待"+load+"加载");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将Novel保存为TXT文件
     * @param novel 通过ESJ爬取的小说
     * @throws IOException
     */
    public static void saveTXT(Novel novel) throws IOException {
        String title = novel.getNovelName();

        File file = new File(title+".txt");
        FileWriter fileWriter = new FileWriter(file.getName(), true);
        List<Volume> novelVolumeList = novel.getVolumeList();
        for (int i = 0; i < novelVolumeList.size(); i++) {
            fileWriter.write("\n-------"+novelVolumeList.get(i).getName()+"-------\n");
            List<Chapter> chapterList = novelVolumeList.get(i).getChapterList();
            for (int j = 0; j < chapterList.size(); j++) {
                fileWriter.write(chapterList.get(j).toString()+"\n");
            }
        }
        fileWriter.close();
    }
}
