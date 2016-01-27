package com.bizu.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.bizu.R;

/**
 * Created by andre.lmello on 11/1/15.
 */
public abstract class AbstractDoFragmentTransactionOnClickListener implements View.OnClickListener {
    public AbstractDoFragmentTransactionOnClickListener(final FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }
    @Override
    public void onClick(View v) {
        final FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(R.id.fl_fullscreen, getReplacementFragment());
        transaction.addToBackStack(null);
        if (transaction.commit() < 0) {
            throw new IllegalStateException();
        }
    }

    public abstract Fragment getReplacementFragment();

    private FragmentManager mFragmentManager;
}
