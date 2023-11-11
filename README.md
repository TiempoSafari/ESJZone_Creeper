# ESJZone-Creeper

## 简介

ESJ Zone小说爬虫

## 快速开始

- 配置ESJ_Creeper中config.yml配置文件

```yaml
esjConfig:
 loginPath: https://www.esjzone.me/my/login
 email: "注册ESJ邮箱"
 password: "注册ESJ密码"

webDriverConfig:
 driver: webdriver.chrome.driver
 driverPath: chromedriver.exe安装路径
```

- 启动方法

  - 目前通过ModuleFunctionTest模块测试功能，代码如下

  - ```java
    //新建NovelWebSource类，传入要爬取的小说网页
    NovelWebSource novelWebSource = new NovelWebSource("https://www.esjzone.me/detail/1677487185.html");
    //通过ESJConnector获取Cookies
    novelWebSource.setCookies(ESJConnector.getCookies());
    novelWebSource.printCookies();
    //获取小说，getNovel设置线程数（未实现）
    novelWebSource.getChapterList();
    novelWebSource.printChapterList();
    Novel novel = novelWebSource.getNovel(0);
    novel.printNovelInfo();
    //导出小说为Word格式
    ToWord.novel2docx(novel, "./repository/");
    ```


## 待实现功能

- [ ] 小说封面图像获取
- [ ] 小说内部图像获取
- [ ] 实现小说断点续爬
- [ ] 实现小说类持久化
- [ ] 对小说章节增删改查
- [x] 小说导出为Word
- [ ] 小说导出为Epub