package cn.iosd.starer.email.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ok1996
 */
@Configuration
@ConfigurationProperties("simple.email")
public class EmailProperties {
    /**
     * SMTP服务器host
     */
    private String smtpHost;
    /**
     * SMTP服务器端口号
     */
    private Integer smtpPort;
    /**
     * 发件人邮箱用户名
     */
    private String username;
    /**
     * 发件人邮箱密码
     */
    private String password;
    /**
     * 发件人邮箱地址
     */
    private String fromEmail;
    private Boolean smtpAuthEnable = true;
    private Boolean smtpStarttlsEnable = true;

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public Integer getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(Integer smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public Boolean getSmtpAuthEnable() {
        return smtpAuthEnable;
    }

    public void setSmtpAuthEnable(Boolean smtpAuthEnable) {
        this.smtpAuthEnable = smtpAuthEnable;
    }

    public Boolean getSmtpStarttlsEnable() {
        return smtpStarttlsEnable;
    }

    public void setSmtpStarttlsEnable(Boolean smtpStarttlsEnable) {
        this.smtpStarttlsEnable = smtpStarttlsEnable;
    }
}
