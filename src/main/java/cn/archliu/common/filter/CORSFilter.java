package cn.archliu.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.archliu.common.config.ArchComProperties;

@Order(2)
@ConditionalOnProperty(prefix = "archliu.common.cors-properties", name = "cors-origin", havingValue = "true", matchIfMissing = false)
@Component("corsFilter")
public class CORSFilter implements Filter {

    @Autowired
    private ArchComProperties archComProperties;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader("Access-Control-Allow-Credentials", archComProperties.getCorsProperties().getCredentials());
        res.addHeader("Access-Control-Allow-Origin", archComProperties.getCorsProperties().getOrigin());
        res.addHeader("Access-Control-Allow-Methods", archComProperties.getCorsProperties().getMethods());
        res.addHeader("Access-Control-Allow-Headers", archComProperties.getCorsProperties().getHeaders());
        if (((HttpServletRequest) request).getMethod().equals("OPTIONS")) {
            response.getWriter().println("ok");
            return;
        }
        chain.doFilter(request, response);
    }

}
