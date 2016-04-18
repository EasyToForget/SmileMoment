/*
 * Copyright (c) 2016 [zhiye.wei@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smile.moment.volley;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author Smile Wei
 * @since 2014/4/4
 */
public class PostRequest extends Request<String> implements
        DialogInterface.OnCancelListener {
    private Listener<String> listener;
    private ErrorListener errorListener;
    private Map<String, String> params;
    private Map<String, String> headers;
    private Context context;
    private Dialog dialog;
    private View view;


    /**
     * 构造函数，初始化
     *
     * @param url           请求url
     * @param headers       请求头部
     * @param dialog        显示对话框
     * @param view          显示加载view
     * @param listener      请求成功监听
     * @param errorListener 请求失败监听
     * @param context       上下文
     */
    public PostRequest(String url, Map<String, String> params,
                       Map<String, String> headers, Dialog dialog, View view,
                       Listener<String> listener, ErrorListener errorListener, Context context) {
        super(Method.POST, url, errorListener);
        this.params = params;
        this.headers = headers;
        this.dialog = dialog;
        this.view = view;
        this.listener = listener;
        this.errorListener = errorListener;
        this.context = context;
        if (dialog != null) {
            dialog.show();
            dialog.setOnCancelListener(this);
        }
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 构造函数，初始化
     *
     * @param url           请求url
     * @param dialog        显示对话框
     * @param view          显示加载view
     * @param listener      请求成功监听
     * @param errorListener 请求失败监听
     * @param context       上下文
     */
    public PostRequest(String url, Map<String, String> params, Dialog dialog, View view,
                       Listener<String> listener, ErrorListener errorListener, Context context) {
        this(url, params, null, dialog, view, listener, errorListener, context);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, "utf-8");
            return Response.success(jsonString,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params != null ? params : super.getParams();
    }

    /**
     * 失败时的回调方法
     *
     * @param error 错误信息
     */
    @Override
    public void deliverError(VolleyError error) {
        if (dialog != null)
            dialog.dismiss();
        if (view != null)
            view.setVisibility(View.GONE);
        try {
            errorListener.onErrorResponse(error);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 成功时的回调方法
     *
     * @param response 成功响应字符串
     */
    @Override
    protected void deliverResponse(String response) {
        if (dialog != null)
            dialog.dismiss();
        if (view != null)
            view.setVisibility(View.GONE);
        try {
            listener.onResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCancel(DialogInterface arg0) {
        this.cancel();
    }

}
