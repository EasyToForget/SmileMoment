package com.smile.moment.utils;

import android.app.Activity;

import com.smile.moment.R;

/**
 * 启动和关闭activity动画类
 *
 * @author Smile Wei
 * @since 2014.4.4
 */
public class ActivityTransitionUtil {
    /**
     * 从右侧滑入
     *
     * @param activity activity
     */
    public static void startActivityTransition(Activity activity) {
        activity.overridePendingTransition(R.anim.swipeback_stack_right_in,
                R.anim.swipeback_stack_to_back);

    }

    /**
     * 从左侧滑出
     *
     * @param activity activity
     */
    public static void finishActivityTransition(Activity activity) {
        activity.overridePendingTransition(R.anim.swipeback_stack_to_front,
                R.anim.swipeback_stack_right_out);
    }
}
