package com.example.jiangliu.mydribbo.view.shot_list;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiangliu.mydribbo.R;
import com.example.jiangliu.mydribbo.view.base.BaseViewHolder;

import butterknife.BindView;

public class ShotViewHolder extends BaseViewHolder {

    @BindView(R.id.shot_image) public ImageView image;
    @BindView(R.id.shot_action_like) public ImageButton likeButton;
    @BindView(R.id.shot_action_bucket) public ImageButton bucketButton;
    @BindView(R.id.shot_view_count) public TextView viewCount;
    @BindView(R.id.shot_like_count) public TextView likeCount;
    @BindView(R.id.shot_bucket_count) public TextView bucketCount;
    @BindView(R.id.shot_clickable_cover) public View cover;

    public ShotViewHolder(View itemView) {
        super(itemView);
    }
}
