package com.bizu.question.controller;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bizu.R;
import com.bizu.controller.AbstractViewHolder;
import com.bizu.entity.Question;
import com.bizu.question.option.controller.OnQuestionOptionClickedListener;
import com.bizu.util.view.DimensionUtilities;
/**
 * Created by andre.lmello on 10/27/15.
 */
public class Android15LessQuestionsAdapter extends RecyclerView.Adapter<Android15LessQuestionsAdapter.ViewHolder> {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends AbstractViewHolder<RelativeLayout> {

        public ViewHolder(final RelativeLayout itemView) {
            super(itemView);
        }

        public TextView getLineText() {
            return (TextView) mView.findViewById(R.id.tv_line_text);
        }

        public ImageButton getExpandButton() {
            return (ImageButton) mView.findViewById(R.id.ib_expand_button);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Android15LessQuestionsAdapter(final Question myDataset, final RecyclerView recyclerView,
                                         final OnQuestionOptionClickedListener listener,
                                         final Resources resources, final Resources.Theme theme) {
        mQuestion = myDataset;
        mFTListener = listener;
        mResources = resources;
        mTheme = theme;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        final RelativeLayout view = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.line_view_group, parent, false);

        // set the view's size, margins, paddings and layout parameters
        final ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (position == 0) {
            holder.getLineText().setText(mQuestion.getDescription());
        } else if (position == 1) {
            holder.getLineText().setText(mQuestion.getComandoQuestao());
        } else if (position >= 2 && position <= 6) {
            /** menos 2, pois já inicia o position em 2 para os itens */
            holder.getLineText().setText(mQuestion.getItems().get(position - 2).getDescricao());
        }
        holder.getViewTreeObserver()
                .addOnGlobalLayoutListener(new MyOnGlobalLayoutListener(holder));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 1 + mQuestion.getItems().size();
    }

    class ExpandCollapseAnimatorListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {
            isAnimating = true;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            isAnimating = false;
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            isAnimating = true;
        }
    }

    private Question mQuestion;
    private boolean isAnimating = false;
    private final OnQuestionOptionClickedListener mFTListener;
    private final Resources mResources;
    private final Resources.Theme mTheme;

    private class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

        private final ViewHolder holder;

        public MyOnGlobalLayoutListener(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onGlobalLayout() {
            holder.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            View questionLine = holder.getView();
            final int viewHeightPx = holder.getLineText().getLineCount()
                    * holder.getLineText().getLineHeight();
            final float viewHeightDp = DimensionUtilities.pixelToDensityPixel(viewHeightPx,
                    holder.getContext());
            final ImageButton ibExpandButton = holder.getExpandButton();
            final View.OnClickListener listener;

            if (viewHeightDp > 48 && holder.getAdapterPosition() <= 0) {
                ibExpandButton.setVisibility(View.VISIBLE);
                listener =
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!isAnimating) {
                                    if (holder.getView().getHeight()
                                            == DimensionUtilities.densityPixelToPixel(200, v.getContext())) {
                                        final AnimatorSet collapseEveryThing = new AnimatorSet();
                                        final ValueAnimator collapseItem = ValueAnimator.ofObject(new FloatEvaluator(),
                                                DimensionUtilities.densityPixelToPixel(200, v.getContext()),
                                                DimensionUtilities.densityPixelToPixel(72, v.getContext()));
                                        collapseItem.addUpdateListener(new ExpandAnimatorListener(holder.getView()));

                                        final ValueAnimator collapseText = ValueAnimator.ofObject(new FloatEvaluator(),
                                                DimensionUtilities.densityPixelToPixel(111, v.getContext()),
                                                DimensionUtilities.densityPixelToPixel(40, v.getContext()));
                                        collapseText.addUpdateListener(
                                                new ExpandAnimatorListener(holder.getLineText()));

                                        final ValueAnimator rotateIndicator =
                                                ValueAnimator.ofObject(new FloatEvaluator(), 180f, 0f);
                                        rotateIndicator
                                                .addUpdateListener(
                                                        new RotateAnimatorListener(holder.getExpandButton()));

                                        collapseEveryThing.setInterpolator(new DecelerateInterpolator());
                                        collapseEveryThing.playTogether(collapseItem, collapseText, rotateIndicator);
                                        collapseEveryThing.setDuration(1000);
                                        collapseEveryThing.addListener(new ExpandCollapseAnimatorListener());
                                        collapseEveryThing.start();
                                    } else {
                                        final AnimatorSet expandEveryThing = new AnimatorSet();
                                        final ValueAnimator expandItem = ValueAnimator.ofObject(new FloatEvaluator(),
                                                DimensionUtilities.densityPixelToPixel(72, v.getContext()),
                                                DimensionUtilities.densityPixelToPixel(200, v.getContext()));
                                        expandItem.addUpdateListener(new ExpandAnimatorListener(holder.getView()));

                                        final ValueAnimator rotateIndicator =
                                                ValueAnimator.ofObject(new FloatEvaluator(), 0f, 180f);
                                        rotateIndicator
                                                .addUpdateListener(
                                                        new RotateAnimatorListener(holder.getExpandButton()));

                                        final ValueAnimator expandText = ValueAnimator.ofObject(new FloatEvaluator(),
                                                DimensionUtilities.densityPixelToPixel(40, v.getContext()),
                                                DimensionUtilities.densityPixelToPixel(111, v.getContext()));
                                        expandText.addUpdateListener(
                                                new ExpandAnimatorListener(holder.getLineText()));

                                        expandEveryThing.setInterpolator(new DecelerateInterpolator());
                                        expandEveryThing.playTogether(expandItem, expandText, rotateIndicator);
                                        expandEveryThing.setDuration(1000);
                                        expandEveryThing.addListener(new ExpandCollapseAnimatorListener());
                                        expandEveryThing.start();
                                    }
                                }
                            }
                        };

                ibExpandButton.setImageResource(R.drawable.ic_expand_more);
            } else if (viewHeightDp > 48) {
                mFTListener.setQuestionOptionText(holder.getLineText().getText());
                listener = mFTListener;
                ibExpandButton.setVisibility(View.VISIBLE);
                ibExpandButton.setImageResource(R.drawable.ic_more_horiz);
            } else {
                listener = null;
            }

            if (listener != null) {
//                ibExpandButton.setOnClickListener(listener);
                questionLine.setOnClickListener(listener);
            }

        }
    }
}

class ExpandAnimatorListener implements ValueAnimator.AnimatorUpdateListener {
    public ExpandAnimatorListener(final View view) {
        mView = view;
    }
    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        mView.getLayoutParams().height = ((Float)animation.getAnimatedValue()).intValue();
        mView.requestLayout();
    }
    private View mView;
}

class RotateAnimatorListener implements ValueAnimator.AnimatorUpdateListener {
    public RotateAnimatorListener(final ImageButton view) {
        mView = view;
    }
    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        mView.setRotation(((Float) animation.getAnimatedValue()).floatValue());
    }

    private ImageButton mView;
}