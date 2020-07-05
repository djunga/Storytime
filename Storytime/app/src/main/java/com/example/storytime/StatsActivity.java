package com.example.storytime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class StatsActivity extends AppCompatActivity implements FavoritesAdapter.GetFavArrListener {
    public ArrayList<Elder> arr;
    public ArrayList<Elder> favArr;
    private RecyclerView favoritesRV;
    private static FavoritesAdapter favoritesAdapter;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        favoritesRV = findViewById(R.id.recyclerViewFavorites);
        favArr = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

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
                        System.out.println("dino jr.");
                    } else {
                        ////////
                    }
                } else {
                   ///////
                }
            }
        });

        favoritesAdapter = new FavoritesAdapter(favArr, this);
        favoritesRV.setLayoutManager(new LinearLayoutManager(this));
        favoritesRV.setAdapter(favoritesAdapter);
    }


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

    @Override
    public void getFavArr(ArrayList<Elder> arr) {
        favArr = arr;
        favoritesAdapter = new FavoritesAdapter(favArr, this);
        favoritesRV.setLayoutManager(new LinearLayoutManager(this));
        favoritesRV.setAdapter(favoritesAdapter);
        //reload();
    }
}
