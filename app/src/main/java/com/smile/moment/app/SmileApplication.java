package com.smile.moment.app;

import android.app.Application;
import android.content.Context;

/**
 * @author Smile Wei
 * @since 2016/5/26.
 */
public class SmileApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
