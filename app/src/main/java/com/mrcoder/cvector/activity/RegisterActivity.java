package com.mrcoder.cvector.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.mrcoder.cvector.R;
import com.mrcoder.cvector.dbhandler.DBHelper;
import com.mrcoder.cvector.model.Users;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextUserName;
    EditText editTextEmail;
    EditText editTextPassword;

    TextInputLayout textInputLayoutUserName;
    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutPassword;

    Button buttonRegister;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();
        dbHelper = new DBHelper(this);
        initViews();


    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void gotoLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);

    }

    public void onRegisterClick(View view) {
        if (validate()) {
            String strUserName = editTextUserName.getText().toString();
            String strName = editTextUserName.getText().toString();
            String strEmail = editTextEmail.getText().toString();
            String strPassword = editTextPassword.getText().toString();


            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf;
            sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String joiningDate = sdf.format(cal.getTime());

            if (!dbHelper.isEmailExists(strEmail)) {
                if (!dbHelper.isUsernameAvaialable(strUserName)) {

                    dbHelper.addUser(new Users(null, strUserName, strName, strEmail, strPassword, joiningDate, null));
                    startActivity(new Intent(this, LoginActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
                } else {
                    textInputLayoutUserName.setError("Username not available");
                    editTextUserName.setTextColor(Color.RED);
                }
            } else {
                textInputLayoutEmail.setError("Email is already registered");
                editTextEmail.setTextColor(Color.RED);
            }
        }
        /*startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);*/

    }

    private void initViews() {
        editTextEmail = findViewById(R.id.editText_Email);
        editTextPassword = findViewById(R.id.editText_Password);
        editTextUserName = findViewById(R.id.editText_UserName);
        textInputLayoutEmail = findViewById(R.id.textInput_Email);
        textInputLayoutPassword = findViewById(R.id.textInput_Password);
        textInputLayoutUserName = findViewById(R.id.textInput_UserName);
        buttonRegister = findViewById(R.id.cirRegisterButton);
    }

    public boolean validate() {
        boolean isValid = false;

        String UserName = editTextUserName.getText().toString();
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();

        if (UserName.isEmpty()) {
            isValid = false;
            textInputLayoutUserName.setError("Please enter valid username!");
        } else {
            if (UserName.length() > 5) {
                isValid = true;
                textInputLayoutUserName.setError(null);
            } else {
                isValid = false;
                textInputLayoutUserName.setError("Username is to short!");
            }
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            isValid = false;
            textInputLayoutEmail.setError("Please enter valid email!");
        } else {
            isValid = true;
            textInputLayoutEmail.setError(null);
        }

        if (Password.isEmpty()) {
            isValid = false;
            textInputLayoutPassword.setError("Please enter valid password!");
        } else {
            if (Password.length() > 5) {
                isValid = true;
                textInputLayoutPassword.setError(null);
            } else {
                isValid = false;
                textInputLayoutPassword.setError("Password is to short!");
            }
        }
        return isValid;
    }

    /*public String getCurrentDateAndTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String joiningDate = sdf.format(cal.getTime());
        return joiningDate;
    }*/

}