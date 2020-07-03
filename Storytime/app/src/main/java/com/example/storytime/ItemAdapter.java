package com.example.storytime;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/* These classes were modeled based on code from the textbook for the course.
 * We need an Adapter and a ViewHolder to respond when an item in the RecyclerView is clicked.
 * */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.EldersViewHolder> {
    ArrayList<Elder> elderArr;
    private OnItemClickListener mListener;
    private Context context;

    public interface OnItemClickListener {
        void onFavoriteClick(int position);
        //void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public class EldersViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewAge;
        public TextView textViewLanguage;
        public TextView textViewNationality;
        public ImageView imageViewPFP;
        public ImageView imageViewFlag;
        //public ImageButton imageButtonFavorite;

        public EldersViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAge = itemView.findViewById(R.id.textViewAge);
            textViewLanguage = itemView.findViewById(R.id.textViewLanguage);
            textViewNationality = itemView.findViewById(R.id.textViewNationality);
            imageViewPFP = itemView.findViewById(R.id.imageViewPFP);
            imageViewFlag = itemView.findViewById(R.id.imageViewFlag);
            //imageButtonFavorite = itemView.findViewById(R.id.imageButtonFavorite);

//            imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    imageButtonFavorite.setColorFilter(null);
//                    notifyItemChanged(position);
//                }
//            });
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

    @Override
    public void onBindViewHolder(@NonNull EldersViewHolder holder, int position) {
        final ImageButton imageButtonFavorite = holder.itemView.findViewById(R.id.imageButtonFavorite);
        imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable x = imageButtonFavorite.getDrawable();
                Bitmap currBit = ((BitmapDrawable)imageButtonFavorite.getDrawable()).getBitmap();
                Resources res = context.getResources();
                Drawable fav = res.getDrawable(R.drawable.favorite32);
                Bitmap favBit = BitmapFactory.decodeResource(context.getResources(), R.drawable.favorite32);
                if(!favBit.sameAs(currBit)) {
                    imageButtonFavorite.setImageResource(R.drawable.favorite32);
                    imageButtonFavorite.setColorFilter(0);
                }
                else {
                    imageButtonFavorite.setImageResource(R.drawable.clearstar32);
                }
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

    @Override
    public int getItemCount() {
        return elderArr.size();
    }
}
