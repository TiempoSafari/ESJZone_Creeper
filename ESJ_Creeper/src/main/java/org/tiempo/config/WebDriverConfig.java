package org.tiempo.config;

/**
 * 用于配置selenium浏览器驱动的类
 */
public class WebDriverConfig {
    //浏览器类型
    private String driver;
    //浏览器驱动安装位置
    private String driverPath;

    //Getter&Setter
    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDriverPath() {
        return driverPath;
    }

    public void setDriverPath(String driverPath) {
        this.driverPath = driverPath;
    }

}
