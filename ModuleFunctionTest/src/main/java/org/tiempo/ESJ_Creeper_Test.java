package org.tiempo;

import org.tiempo.entity.Novel;
import org.tiempo.entity.NovelWebSource;
import org.tiempo.util.ESJConnector;

import java.io.IOException;

public class ESJ_Creeper_Test {
    public static void main(String[] args) {
        NovelWebSource novelWebSource = new NovelWebSource("https://www.esjzone.me/detail/1698667241.html");
        novelWebSource.setCookies(ESJConnector.getCookies());
        novelWebSource.printCookies();
        novelWebSource.getChapterList();
        novelWebSource.printChapterList();

        Novel novel = novelWebSource.getNovel(0);
        novel.printNovelInfo();


    }
}
