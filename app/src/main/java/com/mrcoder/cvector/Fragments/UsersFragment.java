package com.mrcoder.cvector.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mrcoder.cvector.R;
import com.mrcoder.cvector.adapter.UsersAdapter;
import com.mrcoder.cvector.dbhandler.DBHelper;
import com.mrcoder.cvector.model.Users;

import java.util.ArrayList;

public class UsersFragment extends Fragment {

    RecyclerView recyclerUsers;
    UsersAdapter usersAdapter;
    ArrayList<Users> usersList;
    Context mContext;
    SQLiteDatabase db;
    DBHelper dbHelper;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public UsersFragment() {
        // Required empty public constructor
    }

    public static UsersFragment newInstance(String param1, String param2) {
        UsersFragment usersFragment = new UsersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        return usersFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        /*recyclerUsers = view.findViewById(R.id.HoemRecycler_allUsers);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        recyclerUsers.setLayoutManager(mLayoutManager);
        recyclerUsers.setHasFixedSize(true);
        recyclerUsers.setAdapter(usersAdapter);*/
        intViews(view);
        initObjects();

        showAllUsers();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        showAllUsers();
    }

    private void intViews(View view) {
        recyclerUsers = view.findViewById(R.id.HoemRecycler_allUsers);
    }

    private void initObjects() {
        dbHelper = new DBHelper(getContext());

        usersList = new ArrayList<>();
        usersAdapter = new UsersAdapter(usersList, mContext);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerUsers.setLayoutManager(mLayoutManager);
        recyclerUsers.setHasFixedSize(true);
        recyclerUsers.setAdapter(usersAdapter);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @SuppressLint("StaticFieldLeak")
    private void showAllUsers() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                usersList.addAll(dbHelper.getAllUsers());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                usersAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}