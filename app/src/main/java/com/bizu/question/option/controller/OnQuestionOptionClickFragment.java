package com.bizu.question.option.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.bizu.controller.AbstractFragmentTransationOnClickListener;

/**
 * Created by andre.lmello on 11/1/15.
 */
public class OnQuestionOptionClickFragment extends AbstractFragmentTransationOnClickListener {
    public OnQuestionOptionClickFragment(final FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getReplacementFragment() {
        if (mFragment == null) {
            mFragment = new QuestionOptionFragment();
        }
        final Bundle arguments = new Bundle();
        arguments.putCharSequence(QuestionOptionFragment.QUESTION_OPTION_TEXT, mQuestionOptionText);
        mFragment.setArguments(arguments);
        return mFragment;
    }

    public void setQuestionOptionText(final CharSequence questionOptionText) {
        mQuestionOptionText = questionOptionText;
    }

    private CharSequence mQuestionOptionText;
    private QuestionOptionFragment mFragment;
}
