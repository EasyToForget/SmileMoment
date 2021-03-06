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
package com.smile.moment.ui.contract;

import com.android.volley.VolleyError;
import com.smile.moment.BasePresenter;
import com.smile.moment.BaseView;
import com.smile.moment.model.entity.Image;

import java.util.List;

/**
 * @author Smile Wei
 * @since 2016/6/7.
 */
public interface VoiceContract {

    interface View extends BaseView {
        void loading();

        void networkError();

        void error(VolleyError error);

        void showImages(List<Image> list);

    }

    interface Presenter extends BasePresenter {
        void result();
    }
}
