package cn.archliu.common.util;

import java.util.List;
import java.util.function.Function;

import cn.archliu.common.response.sub.CRUDData;

public class PageUtil {

    public static <T, R> CRUDData<R> build(List<T> data, long total, Function<List<T>, List<R>> convert) {
        return new CRUDData<R>().setItems(convert.apply(data)).setTotal(total);
    }

    private PageUtil() {
    }

}
