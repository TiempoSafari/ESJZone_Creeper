package org.tiempo.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.tiempo.config.AppConfig;
import org.tiempo.entity.Chapter;
import org.tiempo.entity.Novel;
import org.tiempo.entity.Volume;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

public class ESJConnector {

//    public Map<String,String> cookies;

    private static WebDriver webDriver;

    private String novelURL;

    public ESJConnector() {
    }

    public ESJConnector(String novelURL) {
        this.novelURL = novelURL;
    }

    /**
     * 获取登录Cookies。
     * 想实现将Cookie存入文件，但通过Cookie访问网页出现问题，无法获取页面。
     * @return 返回Map<String,String>格式的Cookies
     * @throws InterruptedException
     * @throws IOException
     */
    public static Map<String,String> getCookies() {
        //获取配置文件
        File file = new File("./ESJ_Creeper/src/main/resources/config.yml");
        Yaml yaml = new Yaml();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        AppConfig appConfig = yaml.loadAs(fileReader, AppConfig.class);

        //配置webDriver连接
        System.setProperty(appConfig.getWebDriverConfig().getDriver(), appConfig.getWebDriverConfig().getDriverPath());
        webDriver = new ChromeDriver();
        Dimension dimension = new Dimension(1700,1000);
        webDriver.manage().window().setSize(dimension);
        webDriver.get(appConfig.getEsjConfig().getLoginPath());

        Map<String,String> cookiesJsoup = new HashMap<>();

        try {
            //提交登录表单
            WebElement email = webDriver.findElement(By.name("email"));
            email.sendKeys(appConfig.getEsjConfig().getEmail());
            WebElement pwd = webDriver.findElement(By.name("pwd"));
            pwd.sendKeys(appConfig.getEsjConfig().getPassword());
            WebElement btn = webDriver.findElement(By.xpath("/html/body/div[3]/section/div/div[1]/form/div[4]/a"));
            Thread.sleep(3000);
            btn.click();

            //searchBufferTime
            Thread.sleep(5000);

            //获取Cookies,建立Jsoup连接
            Set<Cookie> cookies = webDriver.manage().getCookies();

            for (Cookie cookie : cookies) {
                cookiesJsoup.put(cookie.getName(),cookie.getValue());
            }

//            this.cookies = cookiesJsoup;

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            webDriver.quit();
        }


        return cookiesJsoup;
    }

//    /**
//     * 获取登录Cookies。
//     * 想实现将Cookie存入文件，但通过Cookie访问网页出现问题，无法获取页面。
//     * @return 返回Map<String,String>格式的Cookies
//     * @throws InterruptedException
//     * @throws IOException
//     */
//    public Map<String,String> getCookies() {
//        //获取配置文件
//        File file = new File("./ESJ_Creeper/src/main/resources/config.yml");
//        Yaml yaml = new Yaml();
//        FileReader fileReader = null;
//        try {
//            fileReader = new FileReader(file);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        AppConfig appConfig = yaml.loadAs(fileReader, AppConfig.class);
//
//        //配置webDriver连接
//        System.setProperty(appConfig.getWebDriverConfig().getDriver(), appConfig.getWebDriverConfig().getDriverPath());
//        webDriver = new ChromeDriver();
//        Dimension dimension = new Dimension(1700,1000);
//        webDriver.manage().window().setSize(dimension);
//        webDriver.get(appConfig.getEsjConfig().getLoginPath());
//
//        try {
//            //提交登录表单
//            WebElement email = webDriver.findElement(By.name("email"));
//            email.sendKeys(appConfig.getEsjConfig().getEmail());
//            WebElement pwd = webDriver.findElement(By.name("pwd"));
//            pwd.sendKeys(appConfig.getEsjConfig().getPassword());
//            Thread.sleep(1000);
//            WebElement btn = webDriver.findElement(By.xpath("/html/body/div[3]/section/div/div[1]/form/div[4]/a"));
//            btn.click();
//
//            //searchBufferTime
//            Thread.sleep(3000);
//
//            //获取Cookies,建立Jsoup连接
//            Set<Cookie> cookies = webDriver.manage().getCookies();
//            Map<String,String> cookiesJsoup = new HashMap<>();
//            for (Cookie cookie : cookies) {
//                System.out.println(cookie.getName()+"➡"+cookie.getExpiry());
//                cookiesJsoup.put(cookie.getName(),cookie.getValue());
//            }
//
////        //将获得的Cookies写入文件
////        File file = new File("./ESJ_Creeper/src/main/resources/ESJcookies.txt");
////        ObjectOutputStream objectOutputStream = null;
////        try {
////            objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
////            objectOutputStream.writeObject(cookies);
////            objectOutputStream.close();
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
//
//            this.cookies = cookiesJsoup;
//
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } finally {
//            webDriver.quit();
//        }
//
//
//        return this.cookies;
//    }

//    /**
//     * 根据提供的element标签中的信息，获取章节并封装程Chapter类
//     * @param element 包含章节href的标签
//     * @return 返回章节
//     * @throws IOException
//     * @throws InterruptedException
//     */
//    public Chapter getChapter(Element element) throws IOException, InterruptedException {
//        Chapter chapter = new Chapter();
//        chapter.setName(element.text());
//        String href = element.attributes().get("href");
//        //获取对应章节的网页doc
//        Document document = Jsoup.connect(href)
//                .cookies(this.cookies)
//                .get();
//
//
//        Thread.sleep(2000);
//
//        //使用Jsoup的document创建，通过xPath查询元素
//        JXDocument jxDocument = JXDocument.create(document);
//        //完整的xPath：/html/body/div[3]/section/div/div[1]/div[3]/p[*]
//        //获取章节中所有段落元素
//        List<JXNode> texts = jxDocument.selN("//div[@class=\"forum-content mt-3\"]/p");
//
//        Thread.sleep(2000);
//
//        //将元素封装成String数组
//        List<String> textList = new ArrayList<>();
//        for (JXNode jxNode : texts) {
//            Element text = jxNode.asElement();
//            textList.add(text.text());
//        }
//        chapter.setTextList(textList);
//
//        Random random = new Random();
//        int randInt = random.nextInt(10000);
//        System.out.println("获取章节《"+chapter.getName()+"》完成，随机等待"+randInt/1000.0+"秒");
//        Thread.sleep(randInt);
//
//        return chapter;
//    }
//
//    /**
//     * 将Novel保存为TXT文件
//     * @param novel 通过ESJ爬取的小说
//     * @throws IOException
//     */
//    public void saveTXT(Novel novel) throws IOException {
//        String title = "testNovel";
//
//        File file = new File(title+".txt");
//        FileWriter fileWriter = new FileWriter(file.getName(), true);
//        List<Volume> novelVolumeList = novel.getVolumeList();
//        for (int i = 0; i < novelVolumeList.size(); i++) {
//            fileWriter.write("\n-------"+novelVolumeList.get(i).getName()+"-------\n");
//            List<Chapter> chapterList = novelVolumeList.get(i).getChapterList();
//            for (int j = 0; j < chapterList.size(); j++) {
//                fileWriter.write(chapterList.get(j).toString()+"\n");
//            }
//        }
//        fileWriter.close();
//    }
//
//    public void getChapterList() {
//        try {
//            Thread.sleep(3000);
//
//            //直到获取document为止
//            Document document = document = Jsoup.connect(novelURL)
//                    .cookies(this.cookies)
//                    .get();
//            Thread.sleep(2000);
//            System.out.print("*");
//
//            //直到获取novel为止
//            Element novel = novel = document.getElementById("chapterList");
//            if (novel == null) {
//                System.err.println("页面获取失败");
//            }else {
//                System.out.println("页面获取成功！");
//            }
//
//            Elements allElements = novel.getAllElements();
//            Boolean[] separateFlag = separateChapter(allElements);
//            Novel esjNovel = getVolumeList(separateFlag, allElements);
//            //保存TXT文件
//            saveTXT(esjNovel);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public Boolean[] separateChapter(Elements allElements) {
//        //获取章节分割标志
//        Boolean[] separator = new Boolean[allElements.size()];
//        for (int i = 0; i < separator.length; i++) {
//            separator[i] = Boolean.TRUE;
//        }
//        for (int i = 0; i < allElements.size(); i++) {
//            Element element = allElements.get(i);
//            if(element.tag().toString() == "a" && element.attributes().hasKey("href")) {
//                separator[i] = Boolean.FALSE;
//                if (i+1<separator.length) {
//                    separator[i+1] = Boolean.FALSE;
//                }
//            }
//            if (element.tag().toString() == "span") {
//                separator[i] = Boolean.FALSE;
//            }
//
//            System.out.println((separator[i]?"✔":"❌")+"----<"+element.tag()+">"+element.text());
//        }
//        return separator;
//    }
//
//    public Novel getVolumeList(Boolean[] separateFlag, Elements allElements) throws IOException, InterruptedException {
//        //按卷分割章节，得到List<Volume>
//        int flag = 0;
//        List<Volume> volumes = new ArrayList<>();
//        List<Chapter> chapterList = new ArrayList<>();
//        for (int i = 0; i < separateFlag.length; i++) {
//            if(separateFlag[i] == Boolean.TRUE) {
//                //如果遇到分隔符，且当前chapterList不为空，则将其打包为一卷
//                if(flag != 0) {
//                    Volume volume = new Volume(chapterList);
//                    volume.setName("第"+(volumes.size()+1)+"卷");
//                    volumes.add(volume);
//                    flag = 0;
//                    System.out.println(volume);
//                }
//                chapterList = new ArrayList<>();
//            }else {     //获取章节信息
//                if (allElements.get(i).tag().toString() == "a" && allElements.get(i).attributes().hasKey("href")) {
//                    System.out.println(allElements.get(i).text());
//                    Chapter chapter = getChapter(allElements.get(i));
//                    chapterList.add(chapter);
//                    flag++;
//                }
//            }
//        }
//        Novel book = new Novel("testBook","ImgURL",volumes);
//        return book;
//    }

    public static void quit() {
        webDriver.quit();
    }
}
