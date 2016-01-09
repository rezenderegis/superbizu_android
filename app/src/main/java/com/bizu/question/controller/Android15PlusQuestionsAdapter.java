//package com.bizu.question.controller;
//
//import android.animation.Animator;
//import android.animation.AnimatorSet;
//import android.animation.FloatEvaluator;
//import android.animation.ValueAnimator;
//import android.annotation.TargetApi;
//import android.os.Build;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewTreeObserver;
//import android.view.animation.DecelerateInterpolator;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import com.bizu.R;
//import com.bizu.controller.AbstractViewHolder;
//import com.bizu.util.view.DimensionUtilities;
//
///**
// * Created by andre.lmello on 10/27/15.
// */
//@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//public class Android15PlusQuestionsAdapter extends RecyclerView.Adapter<Android15PlusQuestionsAdapter.ViewHolder> {
//    // Provide a reference to the views for each data item
//    // Complex data items may need more than one view per item, and
//    // you provide access to all the views for a data item in a view holder
//    public static class ViewHolder extends AbstractViewHolder<View> {
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//        }
//
//        public TextView getLineText() {
//            return (TextView) mView.findViewById(R.id.tv_line_text);
//        }
//
//        public ImageButton getExpandButton() {
//            return (ImageButton) mView.findViewById(R.id.ib_expand_button);
//        }
//    }
//
//    // Provide a suitable constructor (depends on the kind of dataset)
//    public Android15PlusQuestionsAdapter(String[] myDataset) {
//        mDataset = myDataset;
//    }
//
//    // Create new views (invoked by the layout manager)
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent,
//                                                   int viewType) {
//        // create a new view
//        final View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.line_view_group, parent, false);
//
//        // set the view's size, margins, paddings and layout parameters
//        final ViewHolder vh = new ViewHolder(view);
//        return vh;
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position) {
//        // - get element from your dataset at this position
//        // - replace the contents of the view with that element
//        holder.getLineText().setText(mDataset[position]);
//        holder.getViewTreeObserver()
//                .addOnGlobalLayoutListener(new MyOnGlobalLayoutListener(holder));
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    @Override
//    public int getItemCount() {
//        return mDataset.length;
//    }
//
//    class ExpandCollapseAnimatorListener implements Animator.AnimatorListener {
//
//        @Override
//        public void onAnimationStart(Animator animation) {
//            isAnimating = true;
//        }
//
//        @Override
//        public void onAnimationEnd(Animator animation) {
//            isAnimating = false;
//        }
//
//        @Override
//        public void onAnimationCancel(Animator animation) {
//
//        }
//
//        @Override
//        public void onAnimationRepeat(Animator animation) {
//            isAnimating = true;
//        }
//    }
//
//    private String[] mDataset;
//    private boolean isAnimating = false;
//
//    private class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
//
//        private final ViewHolder holder;
//
//        public MyOnGlobalLayoutListener(ViewHolder holder) {
//            this.holder = holder;
//        }
//
//        @Override
//        public void onGlobalLayout() {
//            holder.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//
//            final int viewHeightPx = holder.getLineText().getLineCount()
//                    * holder.getLineText().getLineHeight();
//            final float viewHeightDp = DimensionUtilities.pixelToDensityPixel(viewHeightPx,
//                    holder.getContext());
//
//            if (viewHeightDp > 48) {
//                //FIXME: descobrir como que o IB propaga o evento de click (ACTION_UP)
//                final View.OnClickListener listener =
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                if (!isAnimating) {
//                                    if (holder.getView().getHeight()
//                                            == DimensionUtilities.densityPixelToPixel(200, v.getContext())) {
//                                        final AnimatorSet collapseEveryThing = new AnimatorSet();
//                                        final ValueAnimator collapseItem = ValueAnimator.ofObject(new FloatEvaluator(),
//                                                DimensionUtilities.densityPixelToPixel(200, v.getContext()),
//                                                DimensionUtilities.densityPixelToPixel(72, v.getContext()));
//                                        collapseItem.addUpdateListener(new ExpandAnimatorListener(holder.getView()));
//
//                                        final ValueAnimator collapseText = ValueAnimator.ofObject(new FloatEvaluator(),
//                                                DimensionUtilities.densityPixelToPixel(111, v.getContext()),
//                                                DimensionUtilities.densityPixelToPixel(40, v.getContext()));
//                                        collapseText.addUpdateListener(
//                                                new ExpandAnimatorListener(holder.getLineText()));
//
//                                        final ValueAnimator rotateIndicator =
//                                                ValueAnimator.ofObject(new FloatEvaluator(), 180f, 0f);
//                                        rotateIndicator
//                                                .addUpdateListener(
//                                                        new RotateAnimatorListener(holder.getExpandButton()));
//
//                                        collapseEveryThing.setInterpolator(new DecelerateInterpolator());
//                                        collapseEveryThing.playTogether(collapseItem, collapseText, rotateIndicator);
//                                        collapseEveryThing.setDuration(1000);
//                                        collapseEveryThing.addListener(new ExpandCollapseAnimatorListener());
//                                        collapseEveryThing.start();
//                                    } else {
//                                        final AnimatorSet expandEveryThing = new AnimatorSet();
//                                        final ValueAnimator expandItem = ValueAnimator.ofObject(new FloatEvaluator(),
//                                                DimensionUtilities.densityPixelToPixel(72, v.getContext()),
//                                                DimensionUtilities.densityPixelToPixel(200, v.getContext()));
//                                        expandItem.addUpdateListener(new ExpandAnimatorListener(holder.getView()));
//
//                                        final ValueAnimator rotateIndicator =
//                                                ValueAnimator.ofObject(new FloatEvaluator(), 0f, 180f);
//                                        rotateIndicator
//                                                .addUpdateListener(
//                                                        new RotateAnimatorListener(holder.getExpandButton()));
//
//                                        final ValueAnimator expandText = ValueAnimator.ofObject(new FloatEvaluator(),
//                                                DimensionUtilities.densityPixelToPixel(40, v.getContext()),
//                                                DimensionUtilities.densityPixelToPixel(111, v.getContext()));
//                                        expandText.addUpdateListener(
//                                                new ExpandAnimatorListener(holder.getLineText()));
//
//                                        expandEveryThing.setInterpolator(new DecelerateInterpolator());
//                                        expandEveryThing.playTogether(expandItem, expandText, rotateIndicator);
//                                        expandEveryThing.setDuration(1000);
//                                        expandEveryThing.addListener(new ExpandCollapseAnimatorListener());
//                                        expandEveryThing.start();
//                                    }
//                                }
//                            }
//                        };
//                final ImageButton ibExpandButton = holder.getExpandButton();
//                ibExpandButton.setVisibility(View.VISIBLE);
//                ibExpandButton.setImageResource(R.drawable.ic_expand_more);
//                ibExpandButton.setOnClickListener(listener);
//                holder.getView().setOnClickListener(listener);
//            }
//
//        }
//    }
//}
