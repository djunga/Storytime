package com.example.storytime;

import android.content.Intent;
import android.graphics.Color;
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
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SettingsActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private RadioButton button48;
    private RadioButton button60;
    private RadioButton buttonIndigo;
    private RadioButton buttonCrimson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initStatsButton();
        initHomeButton();
        init14ptButton();
        init24ptButton();
        initIndigoButton();
        initCrimsonButton();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        setFontButton();
        setStyleButton();
    }

    public void setFontButton() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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

    public void setStyleButton() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ConstraintLayout settingsConstraintLayout = findViewById(R.id.constraintLayoutSettings);
                        String style = (String) document.get("style");
                        if(style.equals("crimson")) {
                            settingsConstraintLayout.setBackgroundColor(Color.parseColor("#531111"));
                            buttonCrimson.toggle();
                        }
                        else if(style.equals("indigo")) {
                            settingsConstraintLayout.setBackgroundColor(Color.parseColor("#484E71"));
                            buttonIndigo.toggle();
                        }
                    } else {
                        /////////
                    }
                } else {
                    ////////
                }
            }
        });
    }

    private void initIndigoButton() {
        buttonIndigo = findViewById(R.id.radioButtonIndigo);
        buttonIndigo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String uid = mAuth.getUid();
                db.collection("users").document(uid).update("style", "indigo");
                ConstraintLayout settingsConstraintLayout = findViewById(R.id.constraintLayoutSettings);
                settingsConstraintLayout.setBackgroundColor(Color.parseColor("#484E71"));
            }
        });
    }

    private void initCrimsonButton() {
        buttonCrimson = findViewById(R.id.radioButtonCrimson);
        buttonCrimson.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String uid = mAuth.getUid();
                db.collection("users").document(uid).update("style", "crimson");
                ConstraintLayout settingsConstraintLayout = findViewById(R.id.constraintLayoutSettings);
                settingsConstraintLayout.setBackgroundColor(Color.parseColor("#531111"));
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
        button48 = findViewById(R.id.radioButton48pt);
        button48.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
