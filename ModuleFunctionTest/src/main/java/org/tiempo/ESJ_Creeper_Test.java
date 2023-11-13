package org.tiempo;

import org.tiempo.entity.Novel;
import org.tiempo.entity.NovelWebSource;
import org.tiempo.util.ESJConnector;
import org.tiempo.util.ToWord;

import java.io.IOException;

public class ESJ_Creeper_Test {
    public static void main(String[] args) {
//        NovelWebSource novelWebSource = new NovelWebSource("https://www.esjzone.me/detail/1698667241.html");
//        NovelWebSource novelWebSource = new NovelWebSource("https://www.esjzone.me/detail/1677487185.html");    //章节较少，测试用
//        NovelWebSource novelWebSource = new NovelWebSource("https://www.esjzone.me/detail/1692177832.html");
//        NovelWebSource novelWebSource = new NovelWebSource("https://www.esjzone.me/detail/1626789484.html");    //章节较少，测试用
        NovelWebSource novelWebSource = new NovelWebSource("https://www.esjzone.me/detail/1677487185.html");    //章节较少，测试用
        novelWebSource.setCookies(ESJConnector.getCookies());
        novelWebSource.printCookies();
        novelWebSource.getChapterList();
        novelWebSource.printChapterList();

        Novel novel = novelWebSource.getNovel(0);
        novel.printNovelInfo();

        ToWord.novel2docx(novel, "./repository/", true);

    }
}
