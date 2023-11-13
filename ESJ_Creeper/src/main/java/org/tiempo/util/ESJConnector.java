package org.tiempo.util;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.tiempo.config.AppConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

/**
 * ESJ连接器，用于建立与Esj网站的连接
 */
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

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            webDriver.quit();
        }

        return cookiesJsoup;
    }

    public static void quit() {
        webDriver.quit();
    }
}
