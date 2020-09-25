package com.mrcoder.cvector.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mrcoder.cvector.R;
import com.mrcoder.cvector.adapter.UsersAdapter;
import com.mrcoder.cvector.dbhandler.DBHelper;
import com.mrcoder.cvector.model.Users;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerUsers;
    UsersAdapter usersAdapter;
    ArrayList<Users> usersList;
    Context mContext;
    SQLiteDatabase db;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_home);
        intViews();
        initObjects();

        showAllUsers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
        finish();
        return super.onOptionsItemSelected(item);

    }

    private void intViews() {
        recyclerUsers = findViewById(R.id.HoemRecycler_allUsers);
    }

    private void initObjects() {
        dbHelper = new DBHelper(this);

        usersList = new ArrayList<>();
        usersAdapter = new UsersAdapter(usersList, mContext);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerUsers.setLayoutManager(mLayoutManager);
        recyclerUsers.setHasFixedSize(true);
        recyclerUsers.setAdapter(usersAdapter);
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