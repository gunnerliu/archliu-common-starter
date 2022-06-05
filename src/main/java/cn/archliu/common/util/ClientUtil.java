package cn.archliu.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import cn.archliu.common.constants.AuthConstant;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientUtil {

    /**
     * 获取客户端 IP
     * 
     * @param request
     * @return
     */
    @SuppressWarnings({ "squid:S3776", "squid:S1141" })
    public static String getClientIp(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    try {
                        ipAddress = InetAddress.getLocalHost().getHostAddress();
                    } catch (UnknownHostException e) {
                        log.error("获取IP时异常！", e);
                    }
                }
            }
            // 通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null) {
                if (ipAddress.contains(",")) {
                    return ipAddress.split(",")[0];
                } else {
                    return ipAddress;
                }
            } else {
                return "";
            }
        } catch (Exception e) {
            log.error("获取IP时异常！", e);
            return "";
        }
    }

    /**
     * 获取登录 archToken
     * 
     * @param request
     * @return
     */
    public static String getArchToken(HttpServletRequest request) {
        // 先从 Cookie 中取
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(AuthConstant.ARCH_TOKEN)) {
                    return cookie.getValue();
                }
            }
        }
        // 如果 cookie 中没有，再从 header 中取
        return request.getHeader(AuthConstant.X_ARCH_TOKEN);
    }

    private ClientUtil() {
    }

}
