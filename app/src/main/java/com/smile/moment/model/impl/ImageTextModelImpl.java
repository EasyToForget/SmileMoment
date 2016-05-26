package com.smile.moment.model.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smile.moment.app.SmileApplication;
import com.smile.moment.model.LoadModel;
import com.smile.moment.model.entity.ImageText;
import com.smile.moment.presenter.OnLoadListener;
import com.smile.moment.utils.ApiUtil;
import com.smile.moment.utils.NetWorkUtil;
import com.smile.moment.volley.VolleyHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Smile Wei
 * @since 2016/5/26.
 */
public class ImageTextModelImpl implements LoadModel {
    @Override
    public void load(final OnLoadListener listener) {
        if (!NetWorkUtil.isNetworkAvailable(SmileApplication.getContext())) {
            listener.networkError();
            return;
        }
        VolleyHttpClient.getInstance(SmileApplication.getContext()).get(ApiUtil.MOMENT_IMAGE_TEXT, null
                , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            List<ImageText> list = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject data = jsonObject.getJSONObject("S1426236075742");
                            JSONArray topics = data.getJSONArray("topics");
                            JSONObject topic = topics.getJSONObject(0);
                            JSONArray docs = topic.getJSONArray("docs");
                            String book = docs.toString();
                            List<ImageText> imageTextList = new Gson().fromJson(book, new TypeToken<List<ImageText>>() {
                            }.getType());
                            ImageText imageText = new ImageText();
                            imageText.setType(ImageText.TYPE_BANNER);
                            imageText.setImgsrc(data.getString("banner"));
                            list.add(imageText);
                            list.addAll(imageTextList);
                            listener.onSuccess(list);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error);
                    }
                });
    }
}
