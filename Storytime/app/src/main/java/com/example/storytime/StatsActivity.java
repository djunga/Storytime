package com.example.storytime;

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

public class StatsActivity extends AppCompatActivity {
    public ArrayList<Elder> arr;
    public static ArrayList<Elder> favArr;
    private static RecyclerView favoritesRV;
    private static FavoritesAdapter favoritesAdapter;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        favoritesRV = findViewById(R.id.recyclerViewFavorites);

        initHomeButton();

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
