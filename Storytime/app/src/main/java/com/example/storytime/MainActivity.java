package com.example.storytime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements SearchDialog.MyListener  {
    private RecyclerView searchRV;
    private static ItemAdapter searchAdapter;
    private SearchDialog searchDialog;
    public ArrayList<Elder> arr;
    public ArrayList<Elder> favArr;
    public ArrayList<Elder> searchResultsArr;
    static boolean firstLoad = true;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSearchButton();

        searchRV = findViewById(R.id.recyclerViewSearch);
        arr = new ArrayList<>();
        favArr = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        searchAdapter = new ItemAdapter(arr, this);
        searchRV.setLayoutManager(new LinearLayoutManager(this));
        searchRV.setAdapter(searchAdapter);
    }

    private void initSearchButton() {
        final ImageButton buttonSearch = findViewById(R.id.imageButtonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                searchDialog = new SearchDialog();
                searchDialog.show(getSupportFragmentManager(), "Set up search");
            }
        });
    }

    @Override
    public void loadSearchResults(ContentValues cv) {
        if(firstLoad) {
            Elder farhan = new Elder("Farhan", "Ghafran", 72, "Arabic", "Lebanon");
            Elder ana = new Elder("Ana", "Lopez", 68, "Spanish", "Panama");
            Elder tara = new Elder("Tara", "Jackson", 80, "English", "United States");
            arr.add(farhan);
            arr.add(ana);
            arr.add(tara);
            firstLoad = false;
        }
        String language = (String) cv.get("language");
        ArrayList<Elder> tempArr = new ArrayList<>();

        if(language.equals("Any")) {
            tempArr = arr;
        }
        else {
            for(int i=0; i<arr.size(); i++) {
                Elder elder = arr.get(i);
                if(elder.getLanguage().equals(language)) {
                    tempArr.add(elder);
                }
            }
        }
        searchAdapter = new ItemAdapter(tempArr, this);
        searchRV.setLayoutManager(new LinearLayoutManager(this));
        searchRV.setAdapter(searchAdapter);

    }


}