package com.smile.moment.ui.view;

import com.android.volley.VolleyError;
import com.smile.moment.model.entity.ImageText;

import java.util.List;

/**
 * @author Smile Wei
 * @since 2016/5/26.
 */
public interface ImageTextView {

    void loading();

    void networkError();

    void error(VolleyError error);

    void setData(List<ImageText> list);
}
