package com.example.jiangliu.mydribbo.view.bucket_list;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jiangliu.mydribbo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewBucketDialogFragment extends android.support.v4.app.DialogFragment {

    public static final String KEY_BUCKET_NAME = "bucket_name";
    public static final String KEY_BUCKET_DESCRIPTION = "bucket_description";

    public static final String TAG = "NewBucketDialogFragment";

    @BindView(R.id.new_bucket_name) EditText bucketName;
    @BindView(R.id.new_bucket_description) EditText bucketDescription;

    public static NewBucketDialogFragment newInstance() {
        return new NewBucketDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_bucket, null);
        ButterKnife.bind(this, view);

        return new android.support.v7.app.AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle(R.string.new_bucket_title)
                .setPositiveButton(R.string.new_bucket_create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent result = new Intent();
                        result.putExtra(KEY_BUCKET_NAME, bucketName.getText().toString());
                        result.putExtra(KEY_BUCKET_DESCRIPTION, bucketDescription.getText().toString());
                        getTargetFragment().onActivityResult(BucketListFragment.REQ_CODE_NEW_BUCKET,
                                Activity.RESULT_OK,
                                result);
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.new_bucket_cancel, null)
                .show();
    }
}
