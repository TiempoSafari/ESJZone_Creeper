//package org.tiempo;
//
//import org.jsoup.Connection;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.parser.Tag;
//import org.jsoup.select.Elements;
//import org.openqa.selenium.Cookie;
//import org.tiempo.entity.Chapter;
//import org.tiempo.entity.Novel;
//import org.tiempo.entity.Volume;
//import org.tiempo.util.ESJConnector;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.util.*;
//
//public class Test01 {
//    public static void main(String[] args) {
//        ESJConnector esjConnector = new ESJConnector();
//        String novelURL = "https://www.esjzone.me/detail/1694251503.html";
//
//        try {
//            //判断保存的Cookie是否过期
//            Map<String, String> cookies = new HashMap<>();
////            File file = new File("./ESJ_Creeper/src/main/resources/ESJcookies.txt");
////            if(file.exists()) {
////                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
////                Set<Cookie> historyCookies = (Set<Cookie>) objectInputStream.readObject();
////
////
////                Date currentData = new Date();
////                Boolean f = Boolean.FALSE;
////                for (Cookie c : historyCookies) {
////                    Date expiry = c.getExpiry();
////                    if (expiry == null)
////                        continue;
////                    if (currentData.after(expiry)) {
////                        f = true;
////                    }
////                }
////
////                if (f) {
////                    cookies = esjConnector.getCookies();
////                    Thread.sleep(3000);
////                }else {
////                    for (Cookie cookie : historyCookies) {
////                        cookies.put(cookie.getName(),cookie.getValue());
////                    }
////
////                    Document post = Jsoup.connect("https://www.esjzone.me/my/profile")
////                            .cookies(cookies)
////                            .cookies(cookies)
////                            .cookies(cookies)
////                            .get();
////                    System.out.println(post);
////                }
////            }else {
//                cookies = esjConnector.getCookies();
//                Thread.sleep(3000);
////            }
//
//
//            //直到获取document为止
//            Document document = null;
//            while (document == null) {
//                document = Jsoup.connect(novelURL)
//                        .cookies(cookies)
//                        .get();
//                Thread.sleep(1000);
////                System.out.println(document);
//                System.out.print("*");
//            }
//            Thread.sleep(1000);
//            while (document.getElementById("chapterList") == null) {
//                document = Jsoup.connect(novelURL)
//                        .cookies(cookies)
//                        .get();
//                Thread.sleep(1000);
//                System.out.print("-");
//            }
//            System.out.println("页面获取成功！");
//
//
//            //直到获取novel为止
//            Element novel = null;
//            novel = document.getElementById("chapterList");
//
//            Elements allElements = novel.getAllElements();
//
//            //获取章节分割标志
//            Boolean[] separator = new Boolean[allElements.size()];
//            for (int i = 0; i < separator.length; i++) {
//                separator[i] = Boolean.TRUE;
//            }
//            for (int i = 0; i < allElements.size(); i++) {
//                Element element = allElements.get(i);
//                if(element.tag().toString() == "a" && element.attributes().hasKey("href")) {
//                    separator[i] = Boolean.FALSE;
//                    if (i+1<separator.length) {
//                        separator[i+1] = Boolean.FALSE;
//                    }
//                }
//                if (element.tag().toString() == "span") {
//                    separator[i] = Boolean.FALSE;
//                }
//
//                System.out.println((separator[i]?"✔":"❌")+"----<"+element.tag()+">"+element.text());
//            }
//
//            //按卷分割章节，得到List<Volume>
//            int flag = 0;
//            List<Volume> volumes = new ArrayList<>();
//            List<Chapter> chapterList = new ArrayList<>();
//            for (int i = 0; i < separator.length; i++) {
//                if(separator[i] == Boolean.TRUE) {
//                    //如果遇到分隔符，且当前chapterList不为空，则将其打包为一卷
//                    if(flag != 0) {
//                        Volume volume = new Volume(chapterList);
//                        volume.setName("第"+(volumes.size()+1)+"卷");
//                        volumes.add(volume);
//                        flag = 0;
//                        System.out.println(volume);
//                    }
//                    chapterList = new ArrayList<>();
//                }else {     //获取章节信息
//                    if (allElements.get(i).tag().toString() == "a" && allElements.get(i).attributes().hasKey("href")) {
//                        System.out.println(allElements.get(i).text());
//                        Chapter chapter = esjConnector.getChapter(allElements.get(i));
//                        chapterList.add(chapter);
//                        flag++;
//                    }
//                }
//            }
//
//            System.out.println(volumes);
//            Novel book = new Novel("testBook","ImgURL",volumes);
//            esjConnector.saveTXT(book);
//
//        } catch (IOException e) {
//            System.out.println("获取失败");
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } finally {
//            System.out.println("Finally");
////            esjConnector.close();
//        }
//    }
//}
