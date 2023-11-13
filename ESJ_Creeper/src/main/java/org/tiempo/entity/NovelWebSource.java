package org.tiempo.entity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.tiempo.util.NovelImgDownloader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * 爬取前小说的网络资源
 */
public class NovelWebSource {

    private Map<String,String> cookies;

    private String novelURL;

    private Document novelPage;

    public List<SpiderUnit> tirmedChapterList = new ArrayList<>();

    public NovelWebSource() {
    }

    public NovelWebSource(String novelURL) {
        this.novelURL = novelURL;
    }

    public NovelWebSource(Map<String, String> cookies, String novelURL) {
        this.cookies = cookies;
        this.novelURL = novelURL;
    }

    /**
     * 根据提供的element标签中的信息，获取章节并封装程Chapter类
     * @param element 包含章节href的标签
     * @return 返回章节
     * @throws IOException
     * @throws InterruptedException
     */
    public Chapter getChapter(Element element) throws IOException, InterruptedException {
        Chapter chapter = new Chapter();
        chapter.setName(element.text());
        String href = element.attributes().get("href");
        //获取对应章节的网页doc
        Document document = Jsoup.connect(href)
                .cookies(this.cookies)
                .get();


        Thread.sleep(2000);

        //使用Jsoup的document创建，通过xPath查询元素
        JXDocument jxDocument = JXDocument.create(document);
        //完整的xPath：/html/body/div[3]/section/div/div[1]/div[3]/p[*]
        //获取章节中所有段落元素
        List<JXNode> texts = jxDocument.selN("//div[@class=\"forum-content mt-3\"]/p");

        Thread.sleep(2000);

        //将元素封装成String数组
        List<String> textList = new ArrayList<>();
        for (JXNode jxNode : texts) {
            Element text = jxNode.asElement();
            textList.add(text.text());
            //在Chapter中加入Element元素便于生成Epub格式
            chapter.textElementList.add(text);
        }
        chapter.setTextList(textList);

        Random random = new Random();
        int randInt = random.nextInt(10000);
        System.out.println("获取章节《"+chapter.getName()+"》完成，随机等待"+randInt/1000.0+"秒");
        Thread.sleep(randInt);

        return chapter;
    }

    public void getChapterList() {
        try {
            Thread.sleep(2000);

            //获取document
            this.novelPage = Jsoup.connect(novelURL)
                    .cookies(this.cookies)
                    .get();
            Thread.sleep(2000);
//            System.out.print("*");

            //获取chapterList
            Element novel = this.novelPage.getElementById("chapterList");
            if (novel == null) {
                System.err.println("页面获取失败");
                throw new NullPointerException("获取document页面不包含chapterList字段，请重新获取页面");
            }else {
                System.out.println("页面获取成功！");
            }

            Elements allElements = novel.getAllElements();
            trimChapterList(allElements);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 分割获得的章节列表
     * @param allElements 未处理的章节元素
     * @return 返回标志数组，true代表元素为大纲、false为章节
     */
    public Boolean[] separateChapterList(Elements allElements) {
        //获取章节分割标志
        Boolean[] separator = new Boolean[allElements.size()];
        for (int i = 0; i < separator.length; i++) {
            separator[i] = Boolean.TRUE;
        }
        for (int i = 0; i < allElements.size(); i++) {
            Element element = allElements.get(i);
            if(element.tag().toString() == "a" && element.attributes().hasKey("href")) {
                separator[i] = Boolean.FALSE;
                if (i+1<separator.length) {
                    separator[i+1] = Boolean.FALSE;
                }
            }
            if (element.tag().toString() == "span") {
                separator[i] = Boolean.FALSE;
            }

//            System.out.println((separator[i]?"✔":"❌")+"----<"+element.tag()+">"+element.text());
        }
        return separator;
    }

    /**
     * 修剪章节列表，删除多余标签
     * @param allElements 未处理的章节元素
     */
    public void trimChapterList(Elements allElements) {
        Boolean[] separatedFlag = separateChapterList(allElements);
        for (int i = 0; i < allElements.size(); i++) {
            SpiderUnit unit = new SpiderUnit();
            Element element = allElements.get(i);
            if(separatedFlag[i] == true) {
                if(i+1<separatedFlag.length && separatedFlag[i+1] == false) {
                    unit.isSeparater = true;
                    unit.setChapterWebSource(element);
                    this.tirmedChapterList.add(unit);
                }
            }else {
                if(element.tag().toString() == "a" && element.attributes().hasKey("href")) {
                    unit.isSeparater = false;
                    unit.setChapterWebSource(element);
                    this.tirmedChapterList.add(unit);
                }
            }
        }

        List<SpiderUnit> removeList = new ArrayList<>();
        Iterator<SpiderUnit> it01 = this.tirmedChapterList.iterator();
        while (it01.hasNext()) {
            SpiderUnit chapter =it01.next();
            if(chapter.isSeparater && chapter.getChapterWebSource().text().toString().equals("")) {
                removeList.add(chapter);
            }
        }

        Iterator<SpiderUnit> it02 = this.tirmedChapterList.iterator();
        SpiderUnit ch = null;
        while (it02.hasNext()) {
            ch = it02.next();
            if(ch.isSeparater) {
                break;
            }
        }

        int flag = 0;
        while (it02.hasNext()) {
            SpiderUnit temp = it02.next();
            if(!temp.isSeparater) {
                flag++;
            }else {
                if(flag == 0) {
                    removeList.add(ch);
                }
                ch = temp;
                flag = 0;
            }
        }

        for (SpiderUnit r : removeList) {
            this.tirmedChapterList.remove(r);
        }
    }

    public void getChapterBySingleThread() {
        for (SpiderUnit chapterWebSource : this.tirmedChapterList) {
            if(!chapterWebSource.isSeparater) {
                try {
                    Chapter chapter = getChapter(chapterWebSource.getChapterWebSource());
                    chapterWebSource.isDownload = true;
                    chapterWebSource.setChapter(chapter);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void getChapterByMultiThread(int threadNum) {

    }

    /**
     * 多线程获取章节，并拼接为Novel格式
     * @param threadNum 线程数量
     * @return 以Novel格式返回获取的小说
     */
    public Novel getNovel(int threadNum) {
        //将已经下载好的放入Volume中，封装为小说输出
        //改为可以设置线程数、多线程获取章节内容
        if (threadNum != 0) {
            getChapterByMultiThread(threadNum);
        }else {
            getChapterBySingleThread();
        }

        //封装小说的每个Volume，形成VolumeList
        int volumeNameLimit = 100;
        List<Volume> volumes = new ArrayList<>();
        List<Chapter> chapterList = new ArrayList<>();
        Iterator<SpiderUnit> iterator = this.tirmedChapterList.iterator();
        Volume volume = new Volume();
        if(this.tirmedChapterList.get(0) != null) {
            if(this.tirmedChapterList.get(0).isSeparater) {
                SpiderUnit next = iterator.next();
                if(next.getChapterWebSource().text().length()<volumeNameLimit)
                volume.setName(next.getChapterWebSource().text());
            }
        }

        while (iterator.hasNext()) {
            SpiderUnit unit = iterator.next();
            if(!unit.isSeparater) {
                chapterList.add(unit.getChapter());
            }else {
                volume.setChapterList(chapterList);
                volumes.add(volume);
//                System.out.println(">>>封装章节为："+volume.getName());

                chapterList = new ArrayList<>();
                volume = new Volume();
                if(unit.getChapterWebSource().text().length()<volumeNameLimit) {
                    volume.setName(unit.getChapterWebSource().text());
                }
            }
        }
        volume.setChapterList(chapterList);
        volumes.add(volume);

        //获取小说名
        //XPath: /html/body/div[3]/section/div/div[1]/div[1]/div[2]/h2
        JXDocument jxDocument = JXDocument.create(this.novelPage);
        JXNode novelTitle = jxDocument.selNOne("//h2[@class=\"p-t-10 text-normal\"]");

        //获取小说封面图
        String coverPath = NovelImgDownloader.coverDownload(this.novelPage);

        //获取小说简介
        //:/body/div[3]/section/div/div[1]/div[2]/div/div/div/p
        JXNode novelInfo = jxDocument.selNOne("/body/div[3]/section/div/div[1]/div[2]/div/div/div/p");

        //封装未Novel类
//        Novel novel = new Novel(novelTitle.asElement().text(),coverPath,volumes);
        Novel novel = new Novel(novelTitle.asElement().text(),novelInfo.asElement().text(),coverPath,volumes);
        return novel;
    }

    @Deprecated
    public Novel getVolumeList(Boolean[] separateFlag, Elements allElements) throws IOException, InterruptedException {
        //按卷分割章节，得到List<Volume>
        int flag = 0;
        List<Volume> volumes = new ArrayList<>();
        List<Chapter> chapterList = new ArrayList<>();
        for (int i = 0; i < separateFlag.length; i++) {
            if(separateFlag[i] == Boolean.TRUE) {
                //如果遇到分隔符，且当前chapterList不为空，则将其打包为一卷
                if(flag != 0) {
                    Volume volume = new Volume(chapterList);
                    volume.setName("第"+(volumes.size()+1)+"卷");
                    volumes.add(volume);
                    flag = 0;
                    System.out.println(volume);
                }
                chapterList = new ArrayList<>();
            }else {     //获取章节信息
                if (allElements.get(i).tag().toString() == "a" && allElements.get(i).attributes().hasKey("href")) {
                    System.out.println(allElements.get(i).text());
                    Chapter chapter = getChapter(allElements.get(i));
                    chapterList.add(chapter);
                    flag++;
                }
            }
        }
        Novel book = new Novel("testBook","ImgURL",volumes);
        return book;
    }

    /**
     * 控制台打印Cookies
     */
    public void printCookies() {
        for (Map.Entry cookie : this.cookies.entrySet()) {
//            System.out.println("🍪Key:"+cookie.getKey()+"\t\t🍪Value:"+cookie.getValue());
            System.out.printf("🍪Key:%-15s",cookie.getKey());
            System.out.println("🍪Value:"+cookie.getValue());
        }
    }

    /**
     * 控制台打印章节列表
     */
    public void printChapterList() {
        for (SpiderUnit chapter : this.tirmedChapterList) {
            System.out.println(chapter);
        }
    }

    //Getter&Setter
    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public String getNovelURL() {
        return novelURL;
    }

    public void setNovelURL(String novelURL) {
        this.novelURL = novelURL;
    }
}
