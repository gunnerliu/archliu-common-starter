package cn.archliu.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import cn.archliu.common.config.ArchComProperties;
import cn.archliu.common.config.ArchComProperties.SecurityProperties;
import cn.archliu.common.constants.AuthConstant;
import cn.archliu.common.entity.LoginUser;
import cn.archliu.common.exception.sub.NeedLoginException;
import cn.archliu.common.exception.sub.PermissionDeniedException;
import cn.archliu.common.manager.AuthManager;
import cn.archliu.common.util.ClientUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @Author: Arch
 * @Date: 2022-05-09 14:24:42
 * @Description: 鉴权拦截器,对外网接口进行鉴权
 */
@ConditionalOnProperty(prefix = "archliu.common.security-properties", name = "security-enabled", havingValue = "true", matchIfMissing = false)
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthManager authManager;

    @Autowired
    private ArchComProperties archComProperties;

    @SuppressWarnings({ "squid:S3516" })
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 设置 IP
        String clientIp = ClientUtil.getClientIp(request);
        request.setAttribute(AuthConstant.CLIENT_IP, clientIp);
        // 已登录用户设置登录信息
        String archToken = ClientUtil.getArchToken(request);
        LoginUser loginUser = null;
        if (StrUtil.isNotBlank(archToken)) {
            loginUser = authManager.getLoginUser(archToken);
            request.setAttribute(AuthConstant.LOGIN_USER_INFO, loginUser);
        }
        String requestURI = request.getRequestURI();
        SecurityProperties securityProperties = archComProperties.getSecurityProperties();
        if (requestURI.startsWith(securityProperties.getNoLoginUrlPrefix())) {
            // 无需登录即可访问的请求直接放行
            return true;
        }
        // 拦截未登录的用户请求
        if (loginUser == null) {
            throw NeedLoginException.throwE("需要登录！");
        }
        // 管理员 URL 拦截
        if (requestURI.startsWith(securityProperties.getAdminUrlPrefix())
                && BooleanUtil.isFalse(loginUser.getIsAdmin())) {
            throw PermissionDeniedException.throwE("无权访问！");
        }
        return true;
    }

}
