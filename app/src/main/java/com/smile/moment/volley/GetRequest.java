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
import com.smile.moment.widget.LoadingView;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author Smile Wei
 * @since 2014/4/4
 */
public class GetRequest extends Request<String> implements DialogInterface.OnCancelListener {
    /**
     * 请求头
     */
    private Map<String, String> headers;

    /**
     * 加载ProgressBar
     */
    private Dialog dialog;
    /**
     * 加载view
     */
    private LoadingView view;

    private Listener<String> listener;
    private ErrorListener errorListener;
    private Context context;

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
    public GetRequest(String url, Map<String, String> headers,
                      Dialog dialog, LoadingView view, Listener<String> listener,
                      ErrorListener errorListener, Context context) {
        super(Method.GET, url, errorListener);
        this.dialog = dialog;
        this.view = view;
        this.headers = headers;
        this.errorListener = errorListener;
        this.listener = listener;
        this.context = context;
        if (dialog != null) {
            dialog.show();
            dialog.setOnCancelListener(this);
        }
        if (view != null) {
            view.setVisible(View.VISIBLE);
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
    public GetRequest(String url, Dialog dialog, LoadingView view, Listener<String> listener,
                      ErrorListener errorListener, Context context) {
        this(url, null, dialog, view, listener, errorListener, context);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
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
            view.setVisible(View.GONE);
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
        if (dialog != null)
            dialog.dismiss();
        if (view != null)
            view.setVisible(View.GONE);
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

    @Override
    public void onCancel(DialogInterface dialog) {
        this.cancel();
    }

}
