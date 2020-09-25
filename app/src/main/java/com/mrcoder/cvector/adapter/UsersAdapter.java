package com.mrcoder.cvector.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mrcoder.cvector.R;
import com.mrcoder.cvector.model.Users;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {
    ArrayList<Users> usersList;
    Context mContext;
    /*SQLiteDatabase sqLiteDatabase;
    Users users;*/

    public UsersAdapter(ArrayList<Users> usersList, Context mContext) {
        this.usersList = usersList;
        this.mContext = mContext;
//        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

//        holder.ivUserImage.setImageResource();
        holder.tvUsername.setText(usersList.get(position).getUsername());
        holder.tvEmail.setText(usersList.get(position).getEmail());
        holder.tvName.setText(usersList.get(position).getName());
        holder.tvCreatedAt.setText(usersList.get(position).getCreatedAt());

        holder.ivUserImage.setImageResource(R.drawable.userphoto);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvName, tvEmail, tvCreatedAt;
        ImageView ivUserImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.userProfile_userName);
            tvName = itemView.findViewById(R.id.userProfile_Name);
            tvEmail = itemView.findViewById(R.id.userProfile_email);
            tvCreatedAt = itemView.findViewById(R.id.userProfile_createdAt);
            ivUserImage = itemView.findViewById(R.id.userProfile_userImage);

        }
    }
}
