package com.example.storytime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

public class SettingsActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private RadioButton selectedButton;
    private RadioButton button48;
    private RadioButton button60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initStatsButton();
        initHomeButton();
        init14ptButton();
        init24ptButton();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        setFontButton();
        //setFontSizes();
    }

//    public void setFontSizes() {
//
//    }

    public void setFontButton() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            boolean result = false;
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        int fontSize = ((Long) document.get("fontSize")).intValue();
                        if(fontSize == 48) { button48.toggle(); }
                        else if(fontSize == 60) { button60.toggle(); }
                        System.out.println("dino jr.");
                    } else {
                        /////////
                    }
                } else {
                    ////////
                }
            }
        });
    }

    private void init24ptButton() {
        button60 = findViewById(R.id.radioButton60pt);
        button60.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String uid = mAuth.getUid();
                db.collection("users").document(uid).update("fontSize", 60);
            }
        });
    }


    private void init14ptButton() {
        //final RadioGroup group = findViewById(R.id.radioGroup);
        button48 = findViewById(R.id.radioButton48pt);
        button48.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                int selectedId = group.getCheckedRadioButtonId();
//                button14 = (RadioButton) findViewById(selectedId);
                String uid = mAuth.getUid();
                db.collection("users").document(uid).update("fontSize", 48);
            }
        });
    }

    private void initStatsButton() {
        final ImageButton buttonStats = findViewById(R.id.imageButtonStats);
        buttonStats.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, StatsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initHomeButton() {
        final ImageButton buttonHome = findViewById(R.id.imageButtonHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
