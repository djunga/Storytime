package com.example.storytime;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.EldersViewHolder> {
    ArrayList<Elder> elderArr;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private static final String TAG = "Set Favorites: ";

    public class EldersViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewAge;
        public TextView textViewLanguage;
        public TextView textViewNationality;
        public ImageView imageViewPFP;
        public ImageView imageViewFlag;
        public ImageButton imageButtonFavorite;
        public ConstraintLayout constraintLayoutItem;

        public EldersViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAge = itemView.findViewById(R.id.textViewAge);
            textViewLanguage = itemView.findViewById(R.id.textViewLanguage);
            textViewNationality = itemView.findViewById(R.id.textViewNationality);
            imageViewPFP = itemView.findViewById(R.id.imageViewPFP);
            imageViewFlag = itemView.findViewById(R.id.imageViewFlag);
            imageButtonFavorite = itemView.findViewById(R.id.imageButtonFavorite);
            constraintLayoutItem = itemView.findViewById(R.id.constraintLayoutItem);
        }
    }

    public FavoritesAdapter(ArrayList<Elder> list) {
        elderArr = list;
    }

    @Override
    @NonNull
    public FavoritesAdapter.EldersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new FavoritesAdapter.EldersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.EldersViewHolder holder, int position) {
        final ImageButton imageButtonFavorite = holder.itemView.findViewById(R.id.imageButtonFavorite);
        imageButtonFavorite.setImageResource(R.drawable.favorite32);
        imageButtonFavorite.setColorFilter(0);
        final int mPosition = position;
        imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Elder currentElder = elderArr.get(mPosition);
                elderArr.remove(mPosition);
                imageButtonFavorite.setImageResource(R.drawable.clearstar32);
                removeFromFavorites(currentElder);
            }
        });

        Elder currentElder = elderArr.get(position);
        holder.textViewName.setText(currentElder.getFirstName() + " " + currentElder.getLastName());
        holder.textViewAge.setText(""+currentElder.getAge() + " years old");
        holder.textViewLanguage.setText(currentElder.getLanguage());
        holder.textViewNationality.setText(currentElder.getNationality());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        int fontSize = ((Long) document.get("fontSize")).intValue();
                        holder.textViewName.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
                        holder.textViewAge.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
                        holder.textViewLanguage.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
                        holder.textViewNationality.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
                        System.out.println("dino jr.");

                        String style = (String) document.get("style");
                        if(style.equals("crimson")) {
                            holder.constraintLayoutItem.setBackgroundColor(Color.parseColor("#A61B1B"));
                        }
                        else if(style.equals("indigo")) {
                            holder.constraintLayoutItem.setBackgroundColor(Color.parseColor("#3D4EAE"));
                        }
                    } else {
                        ////////
                    }
                } else {
                    ///////
                }
            }
        });

        String fn = currentElder.getFirstName();
        if(fn.toLowerCase().equals("farhan")) {
            holder.imageViewPFP.setImageResource(R.drawable.farhan);
            holder.imageViewFlag.setImageResource(R.drawable.lebanon32);
        }
        else if(fn.toLowerCase().equals("ana")) {
            holder.imageViewPFP.setImageResource(R.drawable.ana);
            holder.imageViewFlag.setImageResource(R.drawable.panama32);
        }
        else if(fn.toLowerCase().equals("tara")) {
            holder.imageViewPFP.setImageResource(R.drawable.tara);
            holder.imageViewFlag.setImageResource(R.drawable.unitedstates32);
        }
        else if(fn.toLowerCase().equals("sanjeed")) {
            holder.imageViewPFP.setImageResource(R.drawable.sanjeed);
            holder.imageViewFlag.setImageResource(R.drawable.india32);
        }
        else if(fn.toLowerCase().equals("cynthia")) {
            holder.imageViewPFP.setImageResource(R.drawable.cynthia);
            holder.imageViewFlag.setImageResource(R.drawable.panama32);
        }
        else if(fn.toLowerCase().equals("james")) {
            holder.imageViewPFP.setImageResource(R.drawable.james);
            holder.imageViewFlag.setImageResource(R.drawable.unitedstates32);
        }
    }

    public void removeFromFavorites(Elder elder) {
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getUid();
        db = FirebaseFirestore.getInstance();
        db.collection("users").document(uid).update("favorites", FieldValue.arrayRemove(elder));
    }

    @Override
    public int getItemCount() {
        return elderArr.size();
    }
}
