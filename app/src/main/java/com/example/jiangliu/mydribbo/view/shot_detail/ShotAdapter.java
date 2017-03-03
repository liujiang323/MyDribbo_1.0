package com.example.jiangliu.mydribbo.view.shot_detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jiangliu.mydribbo.R;
import com.example.jiangliu.mydribbo.model.Shot;
import com.example.jiangliu.mydribbo.view.bucket_list.BucketListFragment;
import com.example.jiangliu.mydribbo.view.bucket_list.BucketListActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShotAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_SHOT_IMAGE = 0;
    private static final int VIEW_TYPE_SHOT_INFO = 1;

    private final Shot shot;
    private final ShotFragment shotFragment;

    public ShotAdapter(@NonNull ShotFragment shotFragment,
                       @NonNull Shot shot) {
        this.shotFragment = shotFragment;
        this.shot = shot;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_TYPE_SHOT_IMAGE:
                view = LayoutInflater.from(getContext()).inflate(R.layout.shot_item_image, parent, false);
                return new ShotImageViewHolder(view);
            case VIEW_TYPE_SHOT_INFO:
                view = LayoutInflater.from(getContext()).inflate(R.layout.shot_item_info, parent, false);
                return new ShotDetailViewHolder(view);
            default:
                return null;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEW_TYPE_SHOT_IMAGE:
                Glide.with(holder.itemView.getContext())
                     .load(shot.getImageUrl())
                     .placeholder(R.drawable.shot_placeholder)
                     .into(((ShotImageViewHolder) holder).image);
                break;
            case VIEW_TYPE_SHOT_INFO:
                final ShotDetailViewHolder shotDetailViewHolder = (ShotDetailViewHolder) holder;
                shotDetailViewHolder.title.setText(shot.title);
                shotDetailViewHolder.authorName.setText(shot.user.name);

                shotDetailViewHolder.description.setText(Html.fromHtml(
                       shot.description == null ? "" : shot.description));
                shotDetailViewHolder.description.setMovementMethod(LinkMovementMethod.getInstance());

                shotDetailViewHolder.likeCount.setText(String.valueOf(shot.likes_count));
                shotDetailViewHolder.bucketCount.setText(String.valueOf(shot.buckets_count));
                shotDetailViewHolder.viewCount.setText(String.valueOf(shot.views_count));

                Glide.with(shotDetailViewHolder.itemView.getContext())
                     .load(shot.user.avatar_url)
                     .placeholder(R.drawable.user_picture_placeholder)
                     .into(shotDetailViewHolder.authorPicture);

                Drawable likeDrawable = shot.liked
                        ? ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite_dribbble_18dp)
                        : ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite_border_black_18dp);
                shotDetailViewHolder.likeButton.setImageDrawable(likeDrawable);

                Drawable bucketDrawable = shot.bucketed
                        ? ContextCompat.getDrawable(shotDetailViewHolder.itemView.getContext(),
                                                    R.drawable.ic_inbox_dribbble_18dp)
                        : ContextCompat.getDrawable(shotDetailViewHolder.itemView.getContext(),
                                                    R.drawable.ic_inbox_black_18dp);
                shotDetailViewHolder.bucketButton.setImageDrawable(bucketDrawable);

                shotDetailViewHolder.likeCount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Like count clicked", Toast.LENGTH_SHORT).show();
                    }
                });
                shotDetailViewHolder.bucketCount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Bucket count clicked", Toast.LENGTH_SHORT).show();
                    }
                });
                shotDetailViewHolder.likeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shotFragment.like(shot.id, !shot.liked);
                    }
                });
                shotDetailViewHolder.shareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        shotFragment.share();
                    }
                });
                shotDetailViewHolder.bucketButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        shotFragment.bucket();
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_SHOT_IMAGE;
        } else {
            return VIEW_TYPE_SHOT_INFO;
        }
    }

    @NonNull
    private Context getContext() {
        return shotFragment.getContext();
    }
}
