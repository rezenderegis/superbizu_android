package com.bizu.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;

import com.bizu.R;

/**
 * Created by andre.lmello on 11/1/15.
 */
public abstract class AbstractFragmentTransationOnClickListener implements View.OnClickListener {
    public AbstractFragmentTransationOnClickListener(final FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }
    @Override
    public void onClick(View v) {
        final FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.fl_fullscreen, getReplacementFragment());
        transaction.commit();
    }

    public abstract Fragment getReplacementFragment();

    private FragmentManager mFragmentManager;
}
