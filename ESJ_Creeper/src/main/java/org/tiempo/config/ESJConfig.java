package org.tiempo.config;

/**
 * 用于ESJ登录的配置信息类
 */
public class ESJConfig {
    //用户登录页面URL
    private String loginPath;
    //登录邮箱
    private String email;
    //登录密码
    private String password;

    //Getter&Setter
    public String getLoginPath() {
        return loginPath;
    }

    public void setLoginPath(String loginPath) {
        this.loginPath = loginPath;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
