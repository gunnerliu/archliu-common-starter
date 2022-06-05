package cn.archliu.common.response;

import static cn.archliu.common.response.ResponseStatus.FAILED;
import static cn.archliu.common.response.ResponseStatus.SUCCESS;

import cn.archliu.common.response.sub.ResData;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: Arch
 * @Date: 2022-03-29 20:11:36
 * @Description: 通用返回,适配 amis
 */
@Data
@Accessors(chain = true)
public class ComRes<T> {

    /** 默认空返回实体，避免新建对象，不允许修改、设值 */
    private static final ResData<Void> voidRes = new ResData<>();

    /** 状态 */
    private Integer status;

    /** 消息提示 */
    private String msg;

    /** 数据 */
    private T data;

    public static ComRes<ResData<Void>> success() {
        return data(SUCCESS, voidRes);
    }

    public static <T> ComRes<T> success(T data) {
        return data(SUCCESS, data);
    }

    public static ComRes<ResData<Void>> failed() {
        return data(FAILED, voidRes);
    }

    public static ComRes<ResData<Void>> failed(String message) {
        return data(FAILED.getStatus(), message, voidRes);
    }

    public static <T> ComRes<T> data(ResponseStatus responseStatus, T data) {
        ComRes<T> comRes = new ComRes<>();
        comRes.setStatus(responseStatus.getStatus());
        comRes.setMsg(responseStatus.getMsg());
        comRes.setData(data);
        return comRes;
    }

    public static <T> ComRes<T> data(Integer status, String message, T data) {
        ComRes<T> comRes = new ComRes<>();
        comRes.setStatus(status);
        comRes.setMsg(message);
        comRes.setData(data);
        return comRes;
    }

}
