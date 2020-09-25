package com.mrcoder.cvector.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolde> {
    @NonNull
    @Override
    public PostViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolde holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PostViewHolde extends RecyclerView.ViewHolder {
        public PostViewHolde(@NonNull View itemView) {
            super(itemView);
        }
    }
}
