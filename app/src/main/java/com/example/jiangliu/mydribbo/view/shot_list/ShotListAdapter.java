package com.example.jiangliu.mydribbo.view.shot_list;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.jiangliu.mydribbo.R;
import com.example.jiangliu.mydribbo.model.Shot;
import com.example.jiangliu.mydribbo.utils.ModelUtils;
import com.example.jiangliu.mydribbo.view.base.BaseViewHolder;
import com.example.jiangliu.mydribbo.view.base.InfiniteAdapter;
import com.example.jiangliu.mydribbo.view.shot_detail.ShotActivity;
import com.example.jiangliu.mydribbo.view.shot_detail.ShotFragment;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ShotListAdapter extends InfiniteAdapter<Shot> {

    private final ShotListFragment shotListFragment;

    public ShotListAdapter(@NonNull ShotListFragment shotListFragment,
                           @NonNull List<Shot> data,
                           @NonNull LoadMoreListener loadMoreListener) {
        super(shotListFragment.getContext(), data, loadMoreListener);
        this.shotListFragment = shotListFragment;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onBindItemViewHolder(BaseViewHolder holder, int position) {
        final Shot shot = getData().get(position);

        final ShotViewHolder shotViewHolder = (ShotViewHolder) holder;

        shotViewHolder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ShotActivity.class);
                intent.putExtra(ShotFragment.KEY_SHOT,
                        ModelUtils.toString(shot, new TypeToken<Shot>() {}));
                intent.putExtra(ShotActivity.KEY_SHOT_TITLE, shot.title);
                shotListFragment.startActivityForResult(intent, ShotListFragment.REQ_CODE_SHOT);
            }
        });

        shotViewHolder.viewCount.setText(String.valueOf(shot.views_count));
        shotViewHolder.bucketCount.setText(String.valueOf(shot.buckets_count));
        shotViewHolder.likeCount.setText(String.valueOf(shot.likes_count));

        Drawable likeDrawable = shot.liked
                ? ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite_dribbble_18dp)
                : ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite_border_black_18dp);
        shotViewHolder.likeButton.setImageDrawable(likeDrawable);

        Drawable bucketDrawable = shot.bucketed
                ? ContextCompat.getDrawable(shotViewHolder.itemView.getContext(),
                R.drawable.ic_inbox_dribbble_18dp)
                : ContextCompat.getDrawable(shotViewHolder.itemView.getContext(),
                R.drawable.ic_inbox_black_18dp);
        shotViewHolder.bucketButton.setImageDrawable(bucketDrawable);

        Glide.with(shotViewHolder.itemView.getContext())
                .load(shot.getImageUrl())
                .placeholder(R.drawable.shot_placeholder)
                .into(shotViewHolder.image);
    }

    @Override
    protected BaseViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_shot, parent, false);
        return new ShotViewHolder(view);
    }
}
