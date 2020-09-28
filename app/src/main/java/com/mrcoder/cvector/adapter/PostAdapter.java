package com.mrcoder.cvector.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mrcoder.cvector.R;
import com.mrcoder.cvector.model.Posts;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolde> {

    ArrayList<Posts> postsList;
    Context context;

    public PostAdapter(ArrayList<Posts> posts, Context context) {
        this.postsList = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_layout, parent, false);

        return new PostViewHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolde holder, int position) {
        holder.tvPostTitleDescription.setText(postsList.get(position).getPostTitle());
        holder.tvPostedAt.setText(postsList.get(position).getPostedDateTime());
        Glide.with(context).load(postsList.get(position).getPostImage()).into(holder.ivPostedImage);
        holder.tvPostTitleDescription.setText(postsList.get(position).getPostTitle());

    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class PostViewHolde extends RecyclerView.ViewHolder {
        TextView tvPostTitleDescription, tvPostedAt;
        ImageView ivPostedImage;

        public PostViewHolde(@NonNull View itemView) {
            super(itemView);

            tvPostTitleDescription = itemView.findViewById(R.id.row_post_title);
            tvPostedAt = itemView.findViewById(R.id.postedAt);

            ivPostedImage = itemView.findViewById(R.id.row_post_image);
        }
    }
}
