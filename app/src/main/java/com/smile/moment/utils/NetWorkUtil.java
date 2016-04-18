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
package com.smile.moment.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 检测网络工具类
 * 包含检测网络的相关方法
 *
 * @author Smile Wei
 * @since 2014.4.4
 */
public class NetWorkUtil {
    /**
     * 检测当的网络（WIFI、4G/3G/2G）状态
     *
     * @param context context
     * @return boolean
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return !(networkInfo == null || !networkInfo.isConnected()) && networkInfo.getState() == NetworkInfo.State.CONNECTED;
    }

    /**
     * 检测当的网络（4G/3G/2G）状态
     *
     * @param context context
     * @return boolean
     */
    public static boolean isGprsAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null)
            return false;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) == null) {
            return false;
        }
        NetworkInfo.State state = connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_MOBILE).getState();
        return NetworkInfo.State.CONNECTED == state;
    }

    /**
     * 检测当的网络（WIFI）状态
     *
     * @param context context
     * @return boolean
     */
    public static boolean isWifiAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null)
            return false;
        if (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) == null) {
            return false;
        }
        NetworkInfo.State state = connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI).getState();
        return NetworkInfo.State.CONNECTED == state;
    }
}
