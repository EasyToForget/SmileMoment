package com.smile.moment.presenter;

import com.android.volley.VolleyError;
import com.smile.moment.model.LoadModel;
import com.smile.moment.model.entity.ImageText;
import com.smile.moment.model.impl.ImageTextModelImpl;
import com.smile.moment.model.impl.VoiceModelImpl;
import com.smile.moment.ui.view.ImageTextView;

import java.util.List;

/**
 * @author Smile Wei
 * @since 2016/5/26.
 */
public class VoicePresenterImpl implements OnLoadListener<List<ImageText>>, LoadPresenter {
    private ImageTextView imageTextView;
    private LoadModel loadModel;

    public VoicePresenterImpl(ImageTextView imageTextView) {
        this.imageTextView = imageTextView;
        loadModel = new VoiceModelImpl();
    }

    @Override
    public void getData() {
        imageTextView.loading();
        loadModel.load(this);
    }

    @Override
    public void onSuccess(List<ImageText> list) {
        imageTextView.setData(list);
    }

    @Override
    public void onError(VolleyError error) {
        imageTextView.error(error);
    }

    @Override
    public void networkError() {
        imageTextView.networkError();
    }
}
