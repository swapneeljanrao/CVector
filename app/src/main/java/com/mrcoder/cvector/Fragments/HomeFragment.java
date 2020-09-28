package com.mrcoder.cvector.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mrcoder.cvector.R;
import com.mrcoder.cvector.adapter.PostAdapter;
import com.mrcoder.cvector.dbhandler.DBHelper;
import com.mrcoder.cvector.model.Posts;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    RecyclerView recyclerPosts;
    PostAdapter postAdapter;
    ArrayList<Posts> postList;
    Context mContext;
    SQLiteDatabase db;
    DBHelper dbHelper;

    public HomeFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        intViews(view);

        initObjects();

        //showAllPosts();
        //getAllPosts();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getAllPosts();
    }

    @SuppressLint("StaticFieldLeak")
    private void getAllPosts() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                postList.addAll(dbHelper.getAllPosts());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                postAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    private void initObjects() {
        dbHelper = new DBHelper(getContext());

        postList = new ArrayList<>();
        postAdapter = new PostAdapter(postList, mContext);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerPosts.setLayoutManager(mLayoutManager);
        recyclerPosts.setHasFixedSize(true);
        recyclerPosts.setAdapter(postAdapter);
    }

    private void intViews(View view) {
        recyclerPosts = view.findViewById(R.id.HoemRecycler_allPosts);
    }
}