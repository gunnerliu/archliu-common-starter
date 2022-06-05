package cn.archliu.common.response;

import lombok.Getter;

/**
 * @Author: Arch
 * @Date: 2022-03-29 20:25:52
 * @Description: 返回状态
 */
@Getter
public enum ResponseStatus {

    SUCCESS(0, "success"),
    FAILED(-1, "failed");

    private Integer status;

    private String msg;

    private ResponseStatus(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

}
