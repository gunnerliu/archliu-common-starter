package cn.archliu.common.entity;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: Arch
 * @Date: 2022-05-09 14:27:08
 * @Description: 登录用户信息
 */
@Data
@Accessors(chain = true)
public class LoginUser {

    /** 帐号名 */
    private String UserName;

    /** 昵称 */
    private String nickName;

    /** 是否是管理员 */
    private Boolean isAdmin = false;

    /** token */
    private String token;

    /** token 创建时间 */
    private LocalDateTime tokenCreateTime;

    /** 客户端 IP */
    private String clientIp;

    /** 用户信息 */
    private Object userInfo;

}
