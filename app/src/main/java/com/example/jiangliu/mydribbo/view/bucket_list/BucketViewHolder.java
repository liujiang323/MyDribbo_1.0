package com.example.jiangliu.mydribbo.view.bucket_list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiangliu.mydribbo.R;
import com.example.jiangliu.mydribbo.view.base.BaseViewHolder;

import butterknife.BindView;

public class BucketViewHolder extends BaseViewHolder {

    @BindView(R.id.bucket_name) TextView bucketName;
    @BindView(R.id.bucket_shot_count) TextView bucketCount;
    @BindView(R.id.bucket_shot_chosen) ImageView bucketChosen;
    @BindView(R.id.bucket_layout) View bucketLayout;

    public BucketViewHolder(View itemView) {
        super(itemView);
    }
}
