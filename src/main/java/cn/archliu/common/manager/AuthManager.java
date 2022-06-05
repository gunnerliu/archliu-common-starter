package cn.archliu.common.manager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.archliu.common.config.ArchComProperties;
import cn.archliu.common.config.ArchComProperties.SecurityProperties;
import cn.archliu.common.constants.AuthConstant;
import cn.archliu.common.entity.LoginUser;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;

/**
 * @Author: Arch
 * @Date: 2022-05-09 15:05:04
 * @Description: 权限管理器
 */
@SuppressWarnings({ "squid:S1854" })
@ConditionalOnProperty(prefix = "archliu.common.security-properties", name = "security-enabled", havingValue = "true", matchIfMissing = false)
@Component
public class AuthManager {

    /** 登录用户缓存, key->token, value->loginUser */
    private HashMap<String, LoginUser> loginUsers = new HashMap<>();

    /** 反向索引, key->userName,value->token */
    private HashMap<String, String> invertedIndex = new HashMap<>();

    private ReentrantLock lock = new ReentrantLock();

    @Autowired
    private ArchComProperties archComProperties;

    public String cacheLoginUser(LoginUser loginUser, HttpServletResponse response) {
        // 检查下该用户是否已经登录了
        if (invertedIndex.containsKey(loginUser.getUserName())) {
            lock.lock();
            try {
                // 已经登录，如果不是同一个 IP 的话，就将之前的踢掉
                LoginUser existedUser = loginUsers.get(invertedIndex.get(loginUser.getUserName()));
                if (!StrUtil.equals(existedUser.getClientIp(), loginUser.getClientIp())) {
                    putCache(loginUser);
                } else {
                    // IP 一致的话就将之前的 token 返回
                    return invertedIndex.get(loginUser.getUserName());
                }
            } finally {
                lock.unlock();
            }
        } else {
            putCache(loginUser);
        }
        // 返回中设置 Set-cookie
        response.addCookie(new Cookie(AuthConstant.ARCH_TOKEN, invertedIndex.get(loginUser.getUserName())));
        loginUser.setToken(invertedIndex.get(loginUser.getUserName()));
        response.addHeader(AuthConstant.X_ARCH_TOKEN, invertedIndex.get(loginUser.getUserName()));
        return invertedIndex.get(loginUser.getUserName());
    }

    /**
     * 放入缓存
     * 
     * @param loginUser
     */
    private void putCache(LoginUser loginUser) {
        String token = JWT.create().setPayload("username", loginUser.getUserName())
                .setKey(StrUtil.bytes(LocalDateTime.now().toString())).sign();
        loginUser.setToken(token).setTokenCreateTime(LocalDateTime.now());
        loginUsers.put(token, loginUser);
        invertedIndex.put(loginUser.getUserName(), token);
    }

    /**
     * 根据 archToken 获取登录用户信息
     * 
     * @param archToken
     * @return
     */
    public LoginUser getLoginUser(String archToken) {
        // token 失效与 token 续签
        SecurityProperties securityProperties = archComProperties.getSecurityProperties();
        LoginUser loginUser = loginUsers.get(archToken);
        if (loginUser == null) {
            return null;
        }
        long minutes = Duration.between(loginUser.getTokenCreateTime(), LocalDateTime.now()).toMinutes();
        // token 失效
        if (minutes > securityProperties.getTokenExpireTime()) {
            loginUsers.remove(archToken);
            invertedIndex.remove(loginUser.getUserName());
            return null;
        }
        // token续签
        if (minutes > securityProperties.getTokenRenewTime()) {
            // 进行续签
            loginUser.setTokenCreateTime(LocalDateTime.now());
        }
        return loginUser;
    }

    /**
     * 将失效的 token 刷掉
     * 30 分钟一刷
     */
    @Scheduled(fixedDelay = 1800000)
    private void clearExpireToken() {
        Iterator<Entry<String, LoginUser>> iterator = loginUsers.entrySet().iterator();
        while (iterator.hasNext()) {
            LoginUser loginUser = iterator.next().getValue();
            long minutes = Duration.between(loginUser.getTokenCreateTime(), LocalDateTime.now()).toMinutes();
            if (minutes > archComProperties.getSecurityProperties().getTokenExpireTime()) {
                loginUsers.remove(loginUser.getToken());
                invertedIndex.remove(loginUser.getUserName());
            }
        }
    }

}
