package com.example.storytime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_fields);

        initRegisterScreenButton();

    }

    private void initRegisterScreenButton() {
        final Button buttonRegister = findViewById(R.id.buttonRegisterScreen);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }
}
