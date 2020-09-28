package com.mrcoder.cvector.Fragments;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mrcoder.cvector.R;
import com.mrcoder.cvector.activity.LoginActivity;
import com.mrcoder.cvector.dbhandler.DBHelper;
import com.mrcoder.cvector.model.Users;

public class ProfileFragment extends Fragment {

    TextView tvUsername, tvEmail, tvProfileTime;
    ImageView ivUserImage;
    Button btnSignOut, btnDeleteAccount;
    DBHelper dbHelper;
    String strUsername;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        dbHelper = new DBHelper(getContext());

        LoginActivity loginActivity = new LoginActivity();
        strUsername = loginActivity.sendUsername();

        intViews(view);


        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                requireActivity().finish();
            }
        });
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*SQLiteDatabase db = dbHelper.getWritableDatabase();

                String whereClause = "username=?";
                String[] whereArgs = {strUsername};

                db.delete("cvector_users", whereClause, whereArgs);*/
//                dbHelper.deleteAccount();

                startActivity(new Intent(getContext(), LoginActivity.class));
                requireActivity().finish();
            }
        });
        return view;
    }

    private void intViews(View view) {
        tvUsername = view.findViewById(R.id.profileFragment_profileTvPersonName);
        tvEmail = view.findViewById(R.id.profileFragment_tvEmail);
        tvProfileTime = view.findViewById(R.id.profileFragment_tvProfileTime);
        ivUserImage = view.findViewById(R.id.profileFragment_UserProfileImage);
        btnDeleteAccount = view.findViewById(R.id.profileFragment_btnDeleteAccount);
        btnSignOut = view.findViewById(R.id.profileFragment_btnSignOut);
    }

    public void signOut() {
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    public void deleteAccount(Users users) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = "id=?";
        String[] whereArgs = {users.getUsername()};
        db.delete("cvector_users", whereClause, whereArgs);
        dbHelper.deleteAccount();
    }

}