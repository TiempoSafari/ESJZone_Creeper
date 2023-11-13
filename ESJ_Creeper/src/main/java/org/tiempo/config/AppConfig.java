package org.tiempo.config;

/**
 * ESJ_Creeper配置类
 */
public class AppConfig {
    private ESJConfig esjConfig;
    private WebDriverConfig webDriverConfig;

    public ESJConfig getEsjConfig() {
        return esjConfig;
    }

    public void setEsjConfig(ESJConfig esjConfig) {
        this.esjConfig = esjConfig;
    }

    public WebDriverConfig getWebDriverConfig() {
        return webDriverConfig;
    }

    public void setWebDriverConfig(WebDriverConfig webDriverConfig) {
        this.webDriverConfig = webDriverConfig;
    }
}
