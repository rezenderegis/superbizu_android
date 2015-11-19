package com.bizu.question.option.controller;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bizu.R;

/**
 * Created by andre.lmello on 11/1/15.
 */
public class QuestionOptionFragment extends Fragment {
    public final static String QUESTION_OPTION_TEXT = "com.bizu.question.option.text";
    public QuestionOptionFragment() {

    }
    @SuppressLint("ValidFragment")
    public QuestionOptionFragment(final CharSequence optionText) {
        final Bundle args = new Bundle();
        args.putCharSequence(QUESTION_OPTION_TEXT, optionText);
        setArguments(args);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            if (getArguments().containsKey(QUESTION_OPTION_TEXT)) {
                mOtptionText = getArguments().getCharSequence(QUESTION_OPTION_TEXT);
            }
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View optionContent = inflater.inflate(R.layout.fragment_question_option_content,
                container, false);
        final TextView optionTextView = (TextView) optionContent
                .findViewById(R.id.tv_option_text);
        optionTextView.setText(mOtptionText);
        return optionContent;
    }

    private CharSequence mOtptionText;

}