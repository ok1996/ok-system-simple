package cn.iosd.starter.encode.rsa.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ok1996
 */
@Configuration
@ConfigurationProperties("simple.encode.rsa")
public class RsaProperties {
    /**
     * RSA公钥
     */
    private String publicKey;
    /**
     * RSA私钥
     */
    private String privateKey;
    /**
     * 时间戳校验
     */
    private TimestampValidation timestampValidation;

    public static class TimestampValidation  {
        /**
         * 默认关闭校验校验时间戳
         */
        private Boolean enabled=false;
        /**
         * 校验时间戳的差值 毫秒 默认5秒
         */
        private Long expiryMillis=5000L;
        /**
         * 校验时间戳的字段
         */
        public static final String TIMESTAMP = "timestamp";

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public Long getExpiryMillis() {
            return expiryMillis;
        }

        public void setExpiryMillis(Long expiryMillis) {
            this.expiryMillis = expiryMillis;
        }
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public TimestampValidation getTimestampValidation() {
        return timestampValidation;
    }

    public void setTimestampValidation(TimestampValidation timestampValidation) {
        this.timestampValidation = timestampValidation;
    }
}
