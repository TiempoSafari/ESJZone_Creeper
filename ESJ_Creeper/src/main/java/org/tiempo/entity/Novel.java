package org.tiempo.entity;

import java.util.List;

/**
 * 用于保存爬取后的小说，并将其存入数据库
 */
public class Novel {
    private String novelName;

    private String novelImgPath;

    private List<Volume> VolumeList;

    public Novel() {
    }

    public Novel(String novelName, String novelImgPath, List<Volume> volumeList) {
        this.novelName = novelName;
        this.novelImgPath = novelImgPath;
        VolumeList = volumeList;
    }

    public String getNovelName() {
        return novelName;
    }

    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }

    public String getNovelImgPath() {
        return novelImgPath;
    }

    public void setNovelImgPath(String novelImgPath) {
        this.novelImgPath = novelImgPath;
    }

    public List<Volume> getVolumeList() {
        return VolumeList;
    }

    public void setVolumeList(List<Volume> volumeList) {
        VolumeList = volumeList;
    }

    public void printNovelInfo() {
        System.out.println("《"+this.novelName+"》");
        for (Volume v : this.VolumeList) {
            System.out.println("\t"+v.getName());
            for (Chapter c : v.getChapterList()) {
                System.out.println("\t |\t"+c.getName());
            }
        }
    }

    @Override
    public String toString() {
        return "Novel{" +
                "novelName='" + novelName + '\'' +
                ", novelImg='" + novelImgPath + '\'' +
                ", VolumeList=" + VolumeList +
                '}';
    }
}
