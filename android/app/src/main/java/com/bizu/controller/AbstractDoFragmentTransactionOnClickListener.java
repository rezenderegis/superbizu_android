package com.bizu.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
        transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out,
                android.R.animator.fade_in, android.R.animator.fade_out);
        transaction.replace(R.id.fl_fullscreen, getReplacementFragment());
        transaction.addToBackStack(null);
        if (transaction.commit() < 0) {
            throw new IllegalStateException();
        }
    }

    public abstract Fragment getReplacementFragment();

    private FragmentManager mFragmentManager;
}
