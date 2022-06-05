package cn.archliu.common.exception.sub;

import cn.archliu.common.exception.BaseException;

/**
 * @Author: Arch
 * @Date: 2022-05-09 15:22:31
 * @Description: 需要登录访问
 */
public class NeedLoginException extends BaseException {

    public NeedLoginException() {
        super("need login !");
    }

    public NeedLoginException(String message) {
        super(message);
    }

    public static NeedLoginException throwE() {
        return new NeedLoginException();
    }

    public static NeedLoginException throwE(String message) {
        return new NeedLoginException(message);
    }

}
