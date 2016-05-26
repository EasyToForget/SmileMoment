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

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;

/**
 * @author Smile Wei
 * @since 2014/4/4
 */
public class GetRequest extends Request<String> {
    private Listener<String> listener;
    private ErrorListener errorListener;

    /**
     * 构造函数，初始化
     *
     * @param url           请求url
     * @param listener      请求成功监听
     * @param errorListener 请求失败监听
     */
    public GetRequest(String url, Listener<String> listener,
                      ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.errorListener = errorListener;
        this.listener = listener;
    }

    /**
     * 成功时的回调方法
     *
     * @param response 成功响应字符串
     */
    @Override
    protected void deliverResponse(String response) {
        try {
            listener.onResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 失败时的回调方法
     *
     * @param error 错误信息
     */
    @Override
    public void deliverError(VolleyError error) {
        try {
            errorListener.onErrorResponse(error);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
