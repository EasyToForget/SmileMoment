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
package com.smile.moment.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smile.moment.R;
import com.smile.moment.model.entity.VoiceContent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Smile Wei
 * @since 2016/4/12.
 */
public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.ViewHolder> {
    private Activity activity;
    private List<VoiceContent> list;

    public AudioAdapter(Activity activity, List<VoiceContent> list) {
        this.activity = activity;
        this.list = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voice_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(activity).load(list.get(position).getCover())
                .placeholder(R.color.place_holder_color)
                .error(R.color.place_holder_color)
                .into(holder.image);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image)
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(getLayoutPosition());
                }
            });
            ButterKnife.bind(this, itemView);
        }

    }
}
