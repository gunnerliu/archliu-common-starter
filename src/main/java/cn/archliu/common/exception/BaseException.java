package cn.archliu.common.exception;

/**
 * @Author: Arch
 * @Date: 2022-03-29 20:59:21
 * @Description: 业务基础异常
 */
public class BaseException extends RuntimeException {

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

}
