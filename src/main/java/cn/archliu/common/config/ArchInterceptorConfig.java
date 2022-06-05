package cn.archliu.common.config;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.archliu.common.interceptor.AuthInterceptor;

/**
 * @Author: Arch
 * @Date: 2022-05-09 15:32:59
 * @Description: 拦截器注册器
 */
public class ArchInterceptorConfig implements WebMvcConfigurer {

    private AuthInterceptor authInterceptor;

    /** 需要鉴权的 url */
    private String authPathPattern;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns(authPathPattern).order(Ordered.HIGHEST_PRECEDENCE);
    }

    public ArchInterceptorConfig(AuthInterceptor authInterceptor, String authPathPattern) {
        this.authInterceptor = authInterceptor;
        this.authPathPattern = authPathPattern;
    }

}
