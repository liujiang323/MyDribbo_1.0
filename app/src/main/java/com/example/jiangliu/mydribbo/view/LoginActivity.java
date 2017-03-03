package com.example.jiangliu.mydribbo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jiangliu.mydribbo.R;
import com.example.jiangliu.mydribbo.dribbble.Dribbble;
import com.example.jiangliu.mydribbo.dribbble.DribbbleException;
import com.example.jiangliu.mydribbo.dribbble.auth.Auth;
import com.example.jiangliu.mydribbo.dribbble.auth.AuthActivity;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.activity_login_btn) TextView loginBtn;
    @BindView(R.id.login_layout) LinearLayout loginView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //load access token from shared preference
        Dribbble.init(this);

        if (!Dribbble.isLoggedIn()) {
            loginView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Auth.openAuthActivity(LoginActivity.this);
                }
            });
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    //get access token from AuthActivity, which parse and post the temporary code here

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Auth.REQ_CODE && resultCode == RESULT_OK) {
            final  String authCode = data.getStringExtra(AuthActivity.KEY_CODE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // this is a network call and it's time consuming
                        // that's why we're doing this in a non-UI thread
                        String token = Auth.fetchAccessToken(authCode);
                        // store access token in SharedPreferences
                            Dribbble.login(LoginActivity.this, token);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    } catch (IOException | DribbbleException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
