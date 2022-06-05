package cn.archliu.common.exception.sub;

import cn.archliu.common.exception.BaseException;

/**
 * @Author: Arch
 * @Date: 2022-05-09 15:24:24
 * @Description: 权限不足异常
 */
public class PermissionDeniedException extends BaseException {

    public PermissionDeniedException() {
        super("Permission denied !");
    }

    public PermissionDeniedException(String message) {
        super(message);
    }

    public static PermissionDeniedException throwE() {
        return new PermissionDeniedException();
    }

    public static PermissionDeniedException throwE(String message) {
        return new PermissionDeniedException(message);
    }

}
