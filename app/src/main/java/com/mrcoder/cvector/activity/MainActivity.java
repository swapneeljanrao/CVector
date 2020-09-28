package com.mrcoder.cvector.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.mrcoder.cvector.Fragments.HomeFragment;
import com.mrcoder.cvector.Fragments.ProfileFragment;
import com.mrcoder.cvector.Fragments.UsersFragment;
import com.mrcoder.cvector.R;
import com.mrcoder.cvector.adapter.ViewPagerAdapter;
import com.mrcoder.cvector.dbhandler.DBHelper;
import com.mrcoder.cvector.model.Posts;
import com.mrcoder.cvector.model.Users;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton fab;

    Dialog popupadd_newPost;
    ProgressBar new_popup_Progress;
    ImageView new_popup_postImage, new_popup_newPostbtn;
    TextView tvPostTitle_description;
    EditText etPost_description;

    Uri pickedImageURI = null;
    DBHelper dbHelper;

    String strUsername;

    Users users;

    private static final int REQUESTCODE = 2;
    private static final int PReqCode = 2;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        LoginActivity loginActivity = new LoginActivity();
        strUsername = loginActivity.sendUsername();

        initViews();

        initPopUp();

        setOnImagePopupClick();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupadd_newPost.show();
            }
        });

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new HomeFragment(), "Home");
        viewPagerAdapter.addFragment(new UsersFragment(), "Users");
        viewPagerAdapter.addFragment(new ProfileFragment(), "Profile");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setOnImagePopupClick() {
        new_popup_postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestPermission();
            }
        });
    }

    private void checkAndRequestPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(MainActivity.this, "Allow storage access to continue", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
            }
        } else {
            openGallery();
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESTCODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initPopUp() {
        popupadd_newPost = new Dialog(this);
        popupadd_newPost.setContentView(R.layout.new_post_popup);
        Objects.requireNonNull(popupadd_newPost.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupadd_newPost.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        popupadd_newPost.getWindow().getAttributes().gravity = Gravity.CENTER_VERTICAL;

        new_popup_postImage = popupadd_newPost.findViewById(R.id.new_post_imagePost);
        new_popup_Progress = popupadd_newPost.findViewById(R.id.new_post_progressbar);
        new_popup_newPostbtn = popupadd_newPost.findViewById(R.id.new_post_editblog);
        etPost_description = popupadd_newPost.findViewById(R.id.new_post_postTitle_description);

        final String strGetpostTitle = etPost_description.getText().toString();
        //etPost_description.clearComposingText();


        new_popup_newPostbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_popup_newPostbtn.setVisibility(View.INVISIBLE);
                new_popup_Progress.setVisibility(View.VISIBLE);

                new_popup_postImage.setImageURI(null);
                etPost_description.setText(null);

                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf;
                sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

                String postedDate = sdf.format(cal.getTime());
                String strPickedImageURI = pickedImageURI.toString();

                //if (!strGetpostTitle.isEmpty() && !strPickedImageURI.isEmpty()) {
                dbHelper.addPost(new Posts(null, strGetpostTitle, strPickedImageURI, strUsername, postedDate));
                showMessage("New post created");
                popupadd_newPost.dismiss();
            } /*else {
                    showMessage("No field should be empty");
                }*/
            //}
        });
    }

    private void showMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        new_popup_newPostbtn.setVisibility(View.VISIBLE);
        new_popup_Progress.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUESTCODE && data != null) {
            pickedImageURI = data.getData();
            new_popup_postImage.setImageURI(pickedImageURI);
        }
    }

    private void initViews() {
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        fab = findViewById(R.id.fabNewPost);

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
}