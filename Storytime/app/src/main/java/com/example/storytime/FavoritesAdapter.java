package com.example.storytime;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
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
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.EldersViewHolder> {
    ArrayList<Elder> elderArr;
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public interface GetFavArrListener {
        void getFavArr(ArrayList<Elder> arr);
    }

    public class EldersViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewAge;
        public TextView textViewLanguage;
        public TextView textViewNationality;
        public ImageView imageViewPFP;
        public ImageView imageViewFlag;
        public ImageButton imageButtonFavorite;

        public EldersViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAge = itemView.findViewById(R.id.textViewAge);
            textViewLanguage = itemView.findViewById(R.id.textViewLanguage);
            textViewNationality = itemView.findViewById(R.id.textViewNationality);
            imageViewPFP = itemView.findViewById(R.id.imageViewPFP);
            imageViewFlag = itemView.findViewById(R.id.imageViewFlag);
            imageButtonFavorite = itemView.findViewById(R.id.imageButtonFavorite);
        }
    }

    public FavoritesAdapter(ArrayList<Elder> list, Context context) {
        elderArr = list;
        this.context = context;
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
        final int mPosition = position;
        imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable x = imageButtonFavorite.getDrawable();
                Bitmap currBit = ((BitmapDrawable)imageButtonFavorite.getDrawable()).getBitmap();
                Resources res = context.getResources();
                Drawable fav = res.getDrawable(R.drawable.favorite32);
                Bitmap favBit = BitmapFactory.decodeResource(context.getResources(), R.drawable.favorite32);
                Elder currentElder = elderArr.get(mPosition);
                imageButtonFavorite.setImageResource(R.drawable.clearstar32);
                removeFromFavorites(currentElder);
            }
        });

        Elder currentElder = elderArr.get(position);
        holder.textViewName.setText(currentElder.getFirstName() + " " + currentElder.getLastName());
        holder.textViewAge.setText(""+currentElder.getAge() + " years old");
        holder.textViewLanguage.setText(currentElder.getLanguage());
        holder.textViewNationality.setText(currentElder.getNationality());
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
