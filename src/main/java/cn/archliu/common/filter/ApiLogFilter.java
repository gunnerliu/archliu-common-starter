package cn.archliu.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(1)
@ConditionalOnProperty(prefix = "archliu.common", name = "api-log", havingValue = "true", matchIfMissing = false)
@Component("apiLogFilter")
public class ApiLogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        long startTime = System.currentTimeMillis();
        chain.doFilter(request, response);
        log.info("request uri: {} timeConsuming: {}", httpServletRequest.getRequestURI(),
                System.currentTimeMillis() - startTime);
    }

}
