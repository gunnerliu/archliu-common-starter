package cn.archliu.common.constants;

/**
 * @Author: Arch
 * @Date: 2022-05-09 15:02:27
 * @Description: 权限相关的常量
 */
public final class AuthConstant {

    /** cookie 的 name */
    public static final String ARCH_TOKEN = "ArchToken";

    /** header 的 name */
    public static final String X_ARCH_TOKEN = "X-ArchToken";

    /** request 请求域中设置的登录用户信息 */
    public static final String LOGIN_USER_INFO = "loginUserInfo";

    /** 请求域中设置的客户端 IP */
    public static final String CLIENT_IP = "clientIp";

    private AuthConstant() {
    }

}
