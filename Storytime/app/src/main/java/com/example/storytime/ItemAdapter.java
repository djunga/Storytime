/*
 * NAME: Tora Mullings
 * SB ID: 111407756
 * */
package com.example.storytime;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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
import java.util.HashMap;

/* These classes were modeled based on code from the textbook for the course.
 * We need an Adapter and a ViewHolder to respond when an item in the RecyclerView is clicked.
 * The purpose of the ItemAdapter is to display the search results.
 * */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.EldersViewHolder> {
    ArrayList<Elder> elderArr;
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private static final String TAG = "Set Favorites: ";

    public interface OnItemClickListener {
        void onFavoriteClick(int position);
    }

    /**
     * The View Holder assigns variables to the nodes on the list view cards.
     */
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

    public ItemAdapter(ArrayList<Elder> list, Context context) {
        elderArr = list;
        this.context = context;
    }

    @Override
    @NonNull
    public EldersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new EldersViewHolder(view);
    }

    /**
     * The purpose of this class is to set the Elder's information on the card.
     * It also sets up a click listener for the user to save an Elder to their
     * list of favorites.
     * @param holder This holds each node on the list view card.
     * @param position This is the position of the current card in the recycler view.
     */
    @Override
    public void onBindViewHolder(@NonNull EldersViewHolder holder, int position) {
        final ImageButton imageButtonFavorite = holder.itemView.findViewById(R.id.imageButtonFavorite);
        final int mPosition = position;
        imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap currBit = ((BitmapDrawable)imageButtonFavorite.getDrawable()).getBitmap();
                Bitmap favBit = BitmapFactory.decodeResource(context.getResources(), R.drawable.favorite32);
                Elder currentElder = elderArr.get(mPosition);
                if(!favBit.sameAs(currBit)) {
                    imageButtonFavorite.setImageResource(R.drawable.favorite32);
                    imageButtonFavorite.setColorFilter(0);
                    addToFavorites(currentElder);
                }
                else {
                    imageButtonFavorite.setImageResource(R.drawable.clearstar32);
                    removeFromFavorites(currentElder);
                }
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

        setFavoritesInSearch(currentElder, holder);
    }

    /**
     * This method enters the firestore to store the elder.
     * @param elder The Elder to add to favorites.
     */
    public void addToFavorites(Elder elder) {
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getUid();
        db = FirebaseFirestore.getInstance();
        db.collection("users").document(uid).update("favorites", FieldValue.arrayUnion(elder));
    }

    /**
     * This method enters the firestore remove the elder.
     * @param elder The Elder to be removed.
     */
    public void removeFromFavorites(Elder elder) {
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getUid();
        db = FirebaseFirestore.getInstance();
        db.collection("users").document(uid).update("favorites", FieldValue.arrayRemove(elder));
    }

    /**
     * This method is for checking which of the elders in the search results are also
     * in the favorites list. It sets the yellow star on the card and make it clickable.
     * @param e The current elder.
     * @param h The view holder that has the nodes from the current card.
     */
    public void setFavoritesInSearch(final Elder e, EldersViewHolder h) {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();
        DocumentReference docRef = db.collection("users").document(uid);
        final ImageButton imageButtonFavorite = h.imageButtonFavorite;
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            boolean result = false;
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        ArrayList<HashMap<String,Object>> hashes = (ArrayList<HashMap<String,Object>>) document.get("favorites");
                        ArrayList<Elder> favorites = hashMapToArrayList(hashes);
                        for(int i=0; i < favorites.size(); i++) {
                            if(favorites.get(i).equals(e)) {
                                imageButtonFavorite.setImageResource(R.drawable.favorite32);
                                imageButtonFavorite.setColorFilter(0);
                            }
                        }
                        System.out.println("dino jr.");
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    /**
     * This method converts the hashmap from the firestore into an
     * arraylist of Elders.
     * @param hashes The arraylist of hashmaps that the firestore returned.
     * @return Returns an arraylist of Elders.
     */
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
    public int getItemCount() {
        return elderArr.size();
    }
}
