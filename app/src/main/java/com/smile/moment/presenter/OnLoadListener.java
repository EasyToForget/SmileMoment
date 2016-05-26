package com.smile.moment.presenter;

import com.android.volley.VolleyError;

/**
 * @author Smile Wei
 * @since 2016/5/26.
 * 在Presenter层实现，给Model层回调，更改View层的状态，确保Model层不直接操作View层
 */
public interface OnLoadListener<T> {

    /**
     * 成功时的回调
     *
     * @param success 成功信息
     */
    void onSuccess(T success);

    /**
     * 失败时的回调
     *
     * @param error 错误信息
     */
    void onError(VolleyError error);

    void networkError();
}
