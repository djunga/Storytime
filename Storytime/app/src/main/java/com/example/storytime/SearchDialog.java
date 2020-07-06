/*
 * NAME: Tora Mullings
 * SB ID: 111407756
 * */
package com.example.storytime;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import androidx.fragment.app.DialogFragment;

/**
 * The purpose of the Search Dialog is to create a dialog
 * when the magnifying glass is clicked and prompt
 * the user to enter search criteria.
 */
public class SearchDialog extends DialogFragment {
    private MyListener listener;
    private Spinner spinnerLanguages;
    private Spinner spinnerNationality;
    public SearchDialog() {
        super();
    }

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

                spinnerNationality = view.findViewById(R.id.spinnerCountries);
                searchCriteria.put("country", spinnerNationality.getSelectedItem().toString());
                listener.loadSearchResults(searchCriteria);

                getDialog().dismiss();
            }
        });
        return view;
    }

    /**
     * The listener gets attached to Main Activity.
     * @param context The context is Main Activity
     */
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

    /**
     * This listener is for sending the search criteria to the main activity
     * where it will be used to populate the recycler view with
     * the correct search results.
     */
    public interface MyListener {
        void loadSearchResults(ContentValues cv);
    }

}