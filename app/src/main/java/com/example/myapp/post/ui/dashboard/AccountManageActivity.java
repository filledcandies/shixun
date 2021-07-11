package com.example.myapp.post.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.myapp.R;

public class AccountManageActivity extends AppCompatActivity {
    private Button button_pp,button_nn,button_acc;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);
        button_pp=(Button)findViewById(R.id.ppchange);
        button_nn=(Button)findViewById(R.id.nnchange);
        button_acc=(Button)findViewById(R.id.back_acc);
        button_pp.setOnClickListener(v -> {
            startActivity(new Intent(AccountManageActivity.this, PPChangeActivity.class));
        });
        button_nn.setOnClickListener(v -> {
            startActivity(new Intent(AccountManageActivity.this, NNChangeActivity.class));
        });
        button_acc.setOnClickListener(v -> {
            finish();
        });

    }
}