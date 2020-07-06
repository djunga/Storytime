/*
 * NAME: Tora Mullings
 * SB ID: 111407756
 * */
package com.example.storytime;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This class is for the Stats Screen. This is a prototype.
 * For now it has a list of favorite elders for the user.
 * In later versions it will have the user's listening streak and
 * time listened.
 */
public class StatsActivity extends AppCompatActivity {
    public ArrayList<Elder> arr;
    public static ArrayList<Elder> favArr;
    private static RecyclerView favoritesRV;
    private static FavoritesAdapter favoritesAdapter;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ConstraintLayout constraintLayoutStats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        favoritesRV = findViewById(R.id.recyclerViewFavorites);

        initHomeButton();
        initSettingsButton();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        constraintLayoutStats = findViewById(R.id.constraintLayoutStats);

        setStyle();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();
        DocumentReference docRef = db.collection("users").document(uid);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ///////////
                        ArrayList<HashMap<String,Object>> hashes = (ArrayList<HashMap<String,Object>>) document.get("favorites");
                        favArr = hashMapToArrayList(hashes);
                        favoritesAdapter = new FavoritesAdapter(favArr);
                        favoritesRV.setLayoutManager(new LinearLayoutManager(StatsActivity.this));
                        favoritesRV.setAdapter(favoritesAdapter);
                    } else {
                        ////////
                    }
                } else {
                   ///////
                }
            }
        });
    }

    /**
     * This method sets the style of the the interface, indigo or crimson.
     */
    public void setStyle() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String style = (String) document.get("style");
                        if(style.equals("crimson")) {
                            constraintLayoutStats.setBackgroundColor(Color.parseColor("#531111"));
                            favoritesRV.setBackgroundColor(Color.parseColor("#531111"));
                        }
                        else if(style.equals("indigo")) {
                            constraintLayoutStats.setBackgroundColor(Color.parseColor("#484E71"));
                            favoritesRV.setBackgroundColor(Color.parseColor("#484E71"));
                        }

                    } else {

                    }
                } else {
                    ///////
                }
            }
        });
    }

    /*The methods below are for initialzing the tab bar buttons.*/
    private void initHomeButton() {
        ImageButton button = findViewById(R.id.imageButtonHome);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(StatsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettingsButton() {
        ImageButton button = findViewById(R.id.imageButtonSettings);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(StatsActivity.this, SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    /* This method converts the hashmap of elders returned by the firestore
    * into an arraylist.
    * */
    public ArrayList<Elder> hashMapToArrayList(ArrayList<HashMap<String,Object>> hashes) {
        ArrayList<Elder> elders = new ArrayList<>();
        for(int i=0; i<hashes.size(); i++) {
            HashMap<String,Object> hash = (HashMap<String,Object>) hashes.get(i);
            String fn = (String) hash.get("firstName");
            String ln = (String) hash.get("lastName");
            Long obj = (Long) hash.get("age");
            int age = obj.intValue();
            String nationality = (String) hash.get("nationality");
            String language = (String) hash.get("language");
            Elder e = new Elder(fn, ln, age, language, nationality);
            elders.add(e);
        }
        return elders;
    }

}
