package com.smile.moment.widget.recyclerviewhelper;

import android.support.v7.widget.RecyclerView;

/**
 * @author Smile Wei
 * @since 2016/4/13.
 */
public interface OnStartDragListener {
    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}
