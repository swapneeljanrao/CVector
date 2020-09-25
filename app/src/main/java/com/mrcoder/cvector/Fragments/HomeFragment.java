package com.mrcoder.cvector.Fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mrcoder.cvector.R;
import com.mrcoder.cvector.adapter.UsersAdapter;
import com.mrcoder.cvector.dbhandler.DBHelper;
import com.mrcoder.cvector.model.Users;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    RecyclerView recyclerUsers;
    UsersAdapter usersAdapter;
    ArrayList<Users> postList;
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

        return view;
    }

    private void showAllPosts(View view) {

    }

    private void initObjects() {
        dbHelper = new DBHelper(getContext());

        postList = new ArrayList<>();
        usersAdapter = new UsersAdapter(postList, mContext);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerUsers.setLayoutManager(mLayoutManager);
        recyclerUsers.setHasFixedSize(true);
        recyclerUsers.setAdapter(usersAdapter);
    }

    private void intViews(View view) {
        recyclerUsers = view.findViewById(R.id.HoemRecycler_allUsers);
    }
}