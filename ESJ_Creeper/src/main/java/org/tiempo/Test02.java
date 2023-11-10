package org.tiempo;

import org.tiempo.entity.Novel;
import org.tiempo.entity.NovelWebSource;
import org.tiempo.util.ESJConnector;
import org.tiempo.util.Tools;

import java.io.IOException;

public class Test02 {
    public static void main(String[] args) {
//        ESJConnector esjConnector = new ESJConnector("https://www.esjzone.me/detail/1694251503.html");
//        https://www.esjzone.me/detail/1683688239.html
//        ESJConnector esjConnector = new ESJConnector("https://www.esjzone.me/detail/1696394621.html");
//        esjConnector.getCookies();
//        esjConnector.getChapterList();

//        NovelWebSource novelWebSource = new NovelWebSource("https://www.esjzone.me/detail/1683688239.html");
        NovelWebSource novelWebSource = new NovelWebSource("https://www.esjzone.me/detail/1698667241.html");
        novelWebSource.setCookies(ESJConnector.getCookies());
        novelWebSource.printCookies();
        novelWebSource.getChapterList();
        novelWebSource.printChapterList();

        Novel novel = novelWebSource.getNovel(0);
        novel.printNovelInfo();

        try {
            Tools.saveTXT(novel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*
        爬取“日文原版”，通过GPT翻译
        将ESJConnecctor中抽取出“WebNovelSource”
        加入多线程操作
        */
    }
}
