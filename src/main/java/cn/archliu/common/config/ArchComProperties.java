package cn.archliu.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Component
@ConfigurationProperties(prefix = "archliu.common")
public class ArchComProperties {

    /** 是否添加统一异常处理类 */
    private boolean addAdvice = Boolean.TRUE;

    /** 是否打印接口请求日志 */
    private boolean apiLog = Boolean.FALSE;

    /** 跨域配置 */
    private CORSProperties corsProperties = new CORSProperties();

    /** 安全配置 */
    private SecurityProperties securityProperties = new SecurityProperties();

    @Data
    @Accessors(chain = true)
    public static class CORSProperties {

        /** 是否开启跨域 */
        private boolean corsOrigin = Boolean.FALSE;

        private String credentials = "true";

        private String origin = "*";

        private String methods = "GET, POST, DELETE, PUT";

        private String headers = "Content-Type,X-CAF-Authorization-Token,sessionToken,X-TOKEN,X-ArchToken";
    }

    @SuppressWarnings({ "squid:S1075" })
    @Data
    @Accessors(chain = true)
    public static class SecurityProperties {

        /** 是否开启鉴权 */
        private boolean securityEnabled = Boolean.FALSE;

        /** RSA私钥 */
        private String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJJsG0ikcKl/JlTay4LPt3zWtgjIPmuWvLrV/V6imC97eQglGxZ/uJa6gvX2g+LqHZU+lv+AybjMewnif0DDVYwmZVcNMsPRiYVCDzM7xpub1nTQZvvTb6aAW9U9D7FysAmzf40Co59esQ0c9FOS6ZPODq6Crp9CRLFQtjODQW/FAgMBAAECgYAsMBfjykx9unOU9BqqtYkIUbvJ1rSPv4PhVqcKIkOPQNH71UbheVX5lvviwqx2lHStD6B+Jx25eWxARWxl3Q05s5CVv6uMSCSmnxbzHLoOIpf7ymOHtxvGTUMGGOHHiqBV4/57yA3TfTebD166L3ICe7rpM1b+0dAjX7U9IqbGWQJBAMZPYyYHDg5sNNWXj8LaY3zkcjXaFF2LIZuDKy1RuhMWIvHIv2ZluS10bS2cSQByL8xp7EHYRSYMCWmi2vz/0LcCQQC9BIN4kC3va+yUXYP/XdPgbQWaET0qTuXawk5RZNtHyVIA87gQqYtxlB7SdNVy9X0lesHp2nCsbWYf99EOrw9jAkBI0BdIfCimFQblMewAEG2dCsgAi90UZ6RkVCoDTtGydltISXw1Xb47OVdo+sw3FLYGKRItLvpcuOiv17LuWANLAkBzK1jgERmsFFpmXESdSJJc4JDVO0PFj6VGEbaeqZFiwCwTIG062dN5NQNCwKV+Ek5ak92rm8mjhlT/jYP4Knp5AkAxB0uModtRfKFU29yOh1q2EHYzPY7tkqEAq/1ciTIWt8E4nTg8Du2R4phPln+JnZKS5FXxiRKT0/YFA5S0Ggin";

        /** RSA公钥 */
        private String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSbBtIpHCpfyZU2suCz7d81rYIyD5rlry61f1eopgve3kIJRsWf7iWuoL19oPi6h2VPpb/gMm4zHsJ4n9Aw1WMJmVXDTLD0YmFQg8zO8abm9Z00Gb702+mgFvVPQ+xcrAJs3+NAqOfXrENHPRTkumTzg6ugq6fQkSxULYzg0FvxQIDAQAB";

        /** aes base64 的 key */
        private String aesKey = "pqYtPHFARCKgIoahAQa5pw==";

        /** 暴露给外网需要鉴权的 url; 服务器内网接口不做鉴权,仅提供给其他服务器调用 */
        private String authPathPattern = "/api/**";

        /** 无需登录即可访问的 url 前缀 */
        private String noLoginUrlPrefix = "/api/noLogin";

        /** 管理员请求 url 前缀 */
        private String adminUrlPrefix = "/api/admin";

        /** token 失效时间,单位分钟 */
        private long tokenExpireTime = 30;

        /** token 续签时间,单位分钟 */
        private long tokenRenewTime;

    }

}
