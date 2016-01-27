package com.bizu.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by andre.lmello on 10/29/15.
 */
public class AbstractViewHolder<T extends View> extends RecyclerView.ViewHolder {
    public AbstractViewHolder(T itemView) {
        super(itemView);
        mView = itemView;
    }

    public ViewTreeObserver getViewTreeObserver() {
        return mView.getViewTreeObserver();
    }

    public T getView() {
        return mView;
    }

    public Context getContext() {
        return mView.getContext();
    }

    protected T mView;
}
