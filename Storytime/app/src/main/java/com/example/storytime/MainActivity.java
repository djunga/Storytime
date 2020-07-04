package com.example.storytime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchDialog.MyListener  {
    private RecyclerView searchRV;
    private static ItemAdapter searchAdapter;
    private SearchDialog searchDialog;
    public ArrayList<Elder> arr;
    public ArrayList<Elder> favArr;
    public ArrayList<Elder> searchResultsArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSearchButton();

        searchRV = findViewById(R.id.recyclerViewSearch);
        Elder farhan = new Elder("Farhan", "Ghafran", 72, "Arabic", "Lebanon");
        Elder ana = new Elder("Ana", "Lopez", 68, "Spanish", "Panama");
        Elder tara = new Elder("Tara", "Jackson", 80, "English", "United States");
        arr = new ArrayList<>();
        favArr = new ArrayList<>();
/*        arr.add(farhan);
        arr.add(ana);
        arr.add(tara);*/
        searchAdapter = new ItemAdapter(arr, this);
        searchRV.setLayoutManager(new LinearLayoutManager(this));
        searchRV.setAdapter(searchAdapter);
        ////////
//        searchAdapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
//            @Override
//            public void onFavoriteClick(int position) {
//                toggleFavorite(position);
//            }
//            @Override
//            public void onDeleteClick(int position) {
//                removeItem(position);
//            }
//        });
        ///////
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

//    private void toggleFavorite(int position) {
//        Elder elder = searchResultsArr.get(position);
//
//        if()
//        searchAdapter.notifyItemChanged(position);
//    }

    @Override
    public void applyChanges(ContentValues cv) {
        Elder farhan = new Elder("Farhan", "Ghafran", 72, "Arabic", "Lebanon");
        Elder ana = new Elder("Ana", "Lopez", 68, "Spanish", "Panama");
        Elder tara = new Elder("Tara", "Jackson", 80, "English", "United States");
        arr.add(farhan);
        arr.add(ana);
        arr.add(tara);
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