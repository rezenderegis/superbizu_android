package com.bizu.question.item;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Domain Class that represents a QUESTION ITEM. May be called as QUESTION OPTIONS, but in the Ubiquitous
 * Language we are using ITEM.
 * This class is aggregated with QUESTION. Does not exist QUESTION without ITEMS.
 * Created by dunmait on 12/19/15.
 */
public class QuestionItem {
    private Long mId;
    private final String mBody;
    private final String mStatus;
    private String mImagePath;

    /**
     *
     * @param id
     * @param body
     * @param status
     * @param imagePath
     */
    public QuestionItem(@Nullable final Long id, @NonNull final String body, @NonNull final String status
            , @Nullable final String imagePath) {
        mId = id;
        mBody = body;
        mStatus = status;
        mImagePath = imagePath;
    }

    public QuestionItem(final String body, final String status, final String imagePath) {
        this(null, body, status, imagePath);
    }

    public QuestionItem(final String body, final String status) {
        this(body, status, null);
    }

    public void setId(Long mId) {
        this.mId = mId;
    }

    public void setImagePath(String mImagePath) {
        this.mImagePath = mImagePath;
    }

    public Long getId() {
        return mId;
    }

    public String getBody() {
        return mBody;
    }

    public String getStatus() {
        return mStatus;
    }

    public String getImagePath() {
        return mImagePath;
    }
}
