package com.example.storytime;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/* These classes were modeled based on code from the textbook for the course.
 * We need an Adapter and a ViewHolder to respond when an item in the RecyclerView is clicked.
 * */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.EldersViewHolder> {
    ArrayList<Elder> elderArr;

    public class EldersViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewAge;
        public TextView textViewLanguage;
        public TextView textViewNationality;
        public ImageView imageViewPFP;
        public ImageView imageViewFlag;

        public EldersViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAge = itemView.findViewById(R.id.textViewAge);
            textViewLanguage = itemView.findViewById(R.id.textViewLanguage);
            textViewNationality = itemView.findViewById(R.id.textViewNationality);
            imageViewPFP = itemView.findViewById(R.id.imageViewPFP);
            imageViewFlag = itemView.findViewById(R.id.imageViewFlag);
        }
    }

    public ItemAdapter(ArrayList<Elder> list) {
        elderArr = list;

    }

    @Override
    @NonNull
    public EldersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new EldersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EldersViewHolder holder, int position) {
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
