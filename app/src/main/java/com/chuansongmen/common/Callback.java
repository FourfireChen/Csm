package com.chuansongmen.common;

/**
 * 如果是为了判断成功与否，如果成功有值，失败无值，则成功返回结果，失败返回Datarepostory.FAIL；
 * 若成功失败都无值，则返回Boolean就行
 * @param <T>
 */

public interface Callback<T> {
    void onResponse(T result);
}
