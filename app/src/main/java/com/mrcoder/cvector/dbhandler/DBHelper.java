package com.mrcoder.cvector.dbhandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mrcoder.cvector.activity.LoginActivity;
import com.mrcoder.cvector.model.Posts;
import com.mrcoder.cvector.model.Users;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    //    private static final String DATABASE_NAME = "CALCULATIONS";
    //User registrations database
    private static final String DATABASE_NAME = "USERS";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "cvector_users";
    private static final String TABLE_NAME2 = "cvector_users";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_1_USERNAME = "username";
    private static final String COLUMN_2_NAME = "name";
    private static final String COLUMN_3_EMAIL = "email";
    private static final String COLUMN_4_PASSWORD = "password";
    private static final String COLUMN_5_CREATED_AT = "created_at";
    private static final String COLUMN_6_USER_IMAGE = "user_imageBLOB";

    //User's posts database
    private static final String TABLE_USERS_POST = "users_post";
    private static final String COLUMN_POST_ID = "_id_Post";
    private static final String COLUMN_1_POST_TITLE = "post_title";
    private static final String COLUMN_2_POST_IMAGE = "post_image";
    private static final String COLUMN_3_POSTED_AT = "posted_at";
    private static final String COLUMN_4_POSTED_BY_USERNAME = "posted_by_username";

    String createTable_Query2 = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_1_USERNAME + " VARCHAR(255) NOT NULL , " +
            COLUMN_2_NAME + " VARCHAR(255) NOT NULL , " +
            COLUMN_3_EMAIL + " VARCHAR(255) NOT NULL , " +
            COLUMN_4_PASSWORD + " VARCHAR(255) NOT NULL , " +
            COLUMN_6_USER_IMAGE + " LONGBLOB , " +
            COLUMN_5_CREATED_AT + " TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP " +
            ")";

    String createTable_Query1 = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INT ," +
            COLUMN_1_USERNAME + " VARCHAR(255) NOT NULL , " +
            COLUMN_2_NAME + " VARCHAR(255) NOT NULL , " +
            COLUMN_3_EMAIL + " VARCHAR(255) NOT NULL , " +
            COLUMN_4_PASSWORD + " VARCHAR(255) NOT NULL , " +
            COLUMN_6_USER_IMAGE + " LONGBLOB , " +
            COLUMN_5_CREATED_AT + " TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP " +
            ")";

    String creatTable_Query_Post = "CREATE TABLE " + TABLE_USERS_POST + "(" + COLUMN_POST_ID + "INT NOT NULL ," +
            COLUMN_1_POST_TITLE + " VARCHAR(255) NOT NULL , " +
            COLUMN_2_POST_IMAGE + " LONBLOB NOT NULL , " +
            COLUMN_3_POSTED_AT + " TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
            COLUMN_4_POSTED_BY_USERNAME + " VARCHAR(255) NOT NULL " +
            ")";
    /*
    * "CREATE TABLE IF NOT EXISTS employees (\n" +
                        "    id INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                        "    name varchar(200) NOT NULL,\n" +
                        "    department varchar(200) NOT NULL,\n" +
                        "    joiningdate datetime NOT NULL,\n" +
                        "    salary double NOT NULL\n" +
                        ");*/

    /*String createTable_Query = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER," +
            COLUMN_1_USERNAME + " VARCHAR(200) PRIMARY KEY," +
            COLUMN_2_NAME + " VARCHAR(200)," +
            COLUMN_3_EMAIL + " VARCHAR(200)," +
            COLUMN_4_PASSWORD + " VARCHAR(200)," +
            COLUMN_5_CREATED_AT + " DATETIME," +
            COLUMN_6_USER_IMAGE + "BLOB);";*/

    //String createTable_Query2 = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID"+" INT NOT NULL AUTO_INCREMENT , `ussername` VARCHAR(255) NOT NULL , `name` VARCHAR(255) NOT NULL , `email` VARCHAR(255) NOT NULL , `password` VARCHAR(255) NOT NULL , `user_image` LONGBLOB NOT NULL , `createdAt` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP , UNIQUE `username` (`ussername`)) ENGINE = MyISAM;";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable_Query1);
        db.execSQL(creatTable_Query_Post);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS_POST);
    }

    public void addPost(Posts posts) {
        SQLiteDatabase dbPost = this.getWritableDatabase();
        ContentValues contentValues_Posts = new ContentValues();
        contentValues_Posts.put(COLUMN_1_POST_TITLE, posts.getPostTitle());
        contentValues_Posts.put(COLUMN_2_POST_IMAGE, posts.getPostImage());
        contentValues_Posts.put(COLUMN_3_POSTED_AT, posts.getPostedDateTime());
        contentValues_Posts.put(COLUMN_4_POSTED_BY_USERNAME, posts.getPostId());
        dbPost.insert(TABLE_USERS_POST, null, contentValues_Posts);
    }


    public Users AuthenticateUser(Users usersAuth) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_1_USERNAME, COLUMN_2_NAME, COLUMN_3_EMAIL, COLUMN_4_PASSWORD, COLUMN_5_CREATED_AT, COLUMN_6_USER_IMAGE}, COLUMN_1_USERNAME + "=?",
                new String[]{usersAuth.getUsername()}, null, null, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            Users users = new Users(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
            if (usersAuth.getPassword().equals(users)) ;
            return users;
        }
        return null;
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_1_USERNAME, COLUMN_2_NAME, COLUMN_3_EMAIL, COLUMN_4_PASSWORD, COLUMN_5_CREATED_AT, COLUMN_6_USER_IMAGE}, COLUMN_3_EMAIL + "=?",
                new String[]{email}, null, null, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public boolean isUsernameAvaialable(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_1_USERNAME, COLUMN_2_NAME, COLUMN_3_EMAIL, COLUMN_4_PASSWORD, COLUMN_5_CREATED_AT, COLUMN_6_USER_IMAGE}, COLUMN_1_USERNAME + "=?",
                new String[]{username}, null, null, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public void addUser(Users users) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1_USERNAME, users.getUsername());
        contentValues.put(COLUMN_2_NAME, users.getName());
        contentValues.put(COLUMN_3_EMAIL, users.getEmail());
        contentValues.put(COLUMN_4_PASSWORD, users.getPassword());
        contentValues.put(COLUMN_5_CREATED_AT, users.getCreatedAt());
        contentValues.put(COLUMN_6_USER_IMAGE, users.getImageURL());

        db.insert(TABLE_NAME, null, contentValues);
        Log.d("User inserted", "name " + users.getName() + "username" + users.getUsername() + "email" + users.getEmail() + "pass" + users.getPassword() + "Created at" + users.getCreatedAt());
        Log.d("db inserted", db.toString());

    }

    public void deleteAccount() {
        LoginActivity loginActivity = new LoginActivity();
        String strUsername = loginActivity.sendUsername();
        Log.d("usernaaaame",strUsername);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //db.delete(TABLE_NAME, COLUMN_1_USERNAME + " = ?", new String[]{String.valueOf(deleteUser.getUsername())});
        String deletQurery = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_1_USERNAME + " = " + strUsername + ";";
        db.execSQL(deletQurery);
        db.close();
    }

    public ArrayList<Posts> getAllPosts(){
        String column[]={
          COLUMN_1_POST_TITLE,
          COLUMN_2_POST_IMAGE,
          COLUMN_3_POSTED_AT,
          COLUMN_4_POSTED_BY_USERNAME
        };
        ArrayList<Posts> postList =new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS_POST, null,null,null,null,null,COLUMN_4_POSTED_BY_USERNAME);
        if (cursor.moveToFirst()){
            do {
                Posts posts = new Posts();
                posts.setPostTitle(cursor.getString(cursor.getColumnIndex(COLUMN_1_POST_TITLE)));
                posts.setPostImage(cursor.getString(cursor.getColumnIndex(COLUMN_2_POST_IMAGE)));
                posts.setPostedDateTime(cursor.getString(cursor.getColumnIndex(COLUMN_3_POSTED_AT)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return postList;
    }
    public ArrayList<Users> getAllUsers() {
        String column[] = {
                COLUMN_1_USERNAME,
                COLUMN_2_NAME,
                COLUMN_3_EMAIL,
                COLUMN_5_CREATED_AT,
                COLUMN_6_USER_IMAGE
        };

        ArrayList<Users> usersList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, column, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Users users = new Users();
                users.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_1_USERNAME)));
                users.setName(cursor.getString(cursor.getColumnIndex(COLUMN_2_NAME)));
                users.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_3_EMAIL)));
                users.setCreatedAt(cursor.getString(cursor.getColumnIndex(COLUMN_5_CREATED_AT)));
                users.setImageURL(cursor.getString(cursor.getColumnIndex(COLUMN_6_USER_IMAGE)));
                usersList.add(users);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return usersList;
    }
}
