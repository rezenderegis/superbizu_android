package com.bizu.question.controller;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bizu.R;
import com.bizu.entity.Question;
import com.bizu.question.option.controller.OnQuestionOptionClickedListener;

/**
 * Created by andre.lmello on 11/1/15.
 */
public class QuestionFragment extends Fragment {

    public static final String PARAMETER_QUESTION = "com.bizu.QuestionFragment.QUESTION";

    public QuestionFragment (final Bundle bundle) {
        setArguments(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final RecyclerView recyclerView = (RecyclerView) inflater
                .inflate(R.layout.fragment_question_content, container, false);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        final LinearLayoutManager layoutManager;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            layoutManager = new LinearLayoutManager(getContext());
//        } else {
            layoutManager = new LinearLayoutManager(getActivity());
//        }
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = null;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            adapter = new Android15LessQuestionsAdapter((Question)getArguments().getParcelable(PARAMETER_QUESTION)
                    , recyclerView, new OnQuestionOptionClickedListener(getFragmentManager()),
                    getActivity().getResources(), getActivity().getTheme());
        } else {
            adapter = new Android15PlusQuestionsAdapter((Question)getArguments().getParcelable(PARAMETER_QUESTION));
        }
        recyclerView.setAdapter(adapter);

        return recyclerView;
    }
}
