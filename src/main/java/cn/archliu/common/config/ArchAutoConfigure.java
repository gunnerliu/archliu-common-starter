package cn.archliu.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import cn.archliu.common.config.ArchComProperties.SecurityProperties;
import cn.archliu.common.interceptor.AuthInterceptor;
import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * @Author: Arch
 * @Date: 2022-03-29 21:40:14
 * @Description: 自动装配配置类
 */
@Configuration
@EnableConfigurationProperties({ ArchComProperties.class })
@ComponentScan(basePackages = { "cn.archliu.common" })
public class ArchAutoConfigure {

    @Autowired
    private ArchComProperties archComProperties;

    @Bean(name = "archRSA")
    public RSA archRSA() {
        SecurityProperties securityProperties = archComProperties.getSecurityProperties();
        return new RSA(securityProperties.getPrivateKey(), securityProperties.getPublicKey());
    }

    @Bean(name = "archAES")
    public SymmetricCrypto symmetricCrypto() {
        SecurityProperties securityProperties = archComProperties.getSecurityProperties();
        return new SymmetricCrypto(SymmetricAlgorithm.AES, Base64.decode(securityProperties.getAesKey()));
    }

    @Bean(name = "archInterceptorConfig")
    @ConditionalOnProperty(prefix = "archliu.common.security-properties", name = "security-enabled", havingValue = "true", matchIfMissing = false)
    public ArchInterceptorConfig archInterceptorConfig(AuthInterceptor authInterceptor) {
        SecurityProperties securityProperties = archComProperties.getSecurityProperties();
        return new ArchInterceptorConfig(authInterceptor, securityProperties.getAuthPathPattern());
    }

}
