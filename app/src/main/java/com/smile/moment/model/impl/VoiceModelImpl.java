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
package com.smile.moment.model.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smile.moment.app.SmileApplication;
import com.smile.moment.model.LoadModel;
import com.smile.moment.model.entity.Image;
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
public class VoiceModelImpl implements LoadModel {
    @Override
    public void load(final OnLoadListener listener) {
        if (!NetWorkUtil.isNetworkAvailable(SmileApplication.getContext())) {
            listener.networkError();
            return;
        }
        VolleyHttpClient.getInstance(SmileApplication.getContext()).get(ApiUtil.MOMENT_VOICE, null
                , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            List<Image> list = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject data = jsonObject.getJSONObject("S1426236711448");
                            JSONArray topics = data.getJSONArray("topics");
                            JSONObject topic = topics.getJSONObject(0);
                            JSONArray docs = topic.getJSONArray("docs");
                            String book = docs.toString();
                            List<Image> imageList = new Gson().fromJson(book, new TypeToken<List<Image>>() {
                            }.getType());
                            Image image = new Image();
                            image.setType(Image.TYPE_BANNER);
                            image.setImgsrc(data.getString("banner"));
                            list.add(image);
                            list.addAll(imageList);
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
