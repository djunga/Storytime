package com.example.storytime;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class SearchDialog extends DialogFragment {
    private MyListener listener;
    private Spinner spinnerLanguages;
    private Spinner spinnerNationality;
    public SearchDialog() {
        super();
    }
/*    public SearchDialog(Item item) {
        selectedItem = item;
    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_search, container);
        getDialog().setTitle("Opened Dialog");
        Button button = view.findViewById(R.id.myButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                spinnerLanguages = view.findViewById(R.id.spinnerLanguages);
                ContentValues searchCriteria = new ContentValues();
                searchCriteria.put("language", spinnerLanguages.getSelectedItem().toString());
                listener.applyChanges(searchCriteria);

                getDialog().dismiss();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (MyListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement MyListener");
        }
    }

    public interface MyListener {
        void applyChanges(ContentValues cv);
    }

}