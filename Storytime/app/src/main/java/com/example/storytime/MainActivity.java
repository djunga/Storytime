package com.example.storytime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView searchRV;
    private RecyclerView.Adapter searchAdapter;
    private SearchDialog searchDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSearchButton();

        searchRV = findViewById(R.id.recyclerViewSearch);
        Elder farhan = new Elder("Farhan", "Ghafran", 72, "Arabic", "Lebanon");
        Elder ana = new Elder("Ana", "Lopez", 68, "Spanish", "Panama");
        Elder tara = new Elder("Tara", "Jackson", 80, "English", "United States");
        ArrayList<Elder> arr = new ArrayList<>();
        arr.add(farhan);
        arr.add(ana);
        arr.add(tara);
        searchAdapter = new ItemAdapter(arr);
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
}