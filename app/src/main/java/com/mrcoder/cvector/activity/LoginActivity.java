package com.mrcoder.cvector.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.mrcoder.cvector.R;
import com.mrcoder.cvector.dbhandler.DBHelper;
import com.mrcoder.cvector.model.Users;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;

    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutPassword;

    Button buttonLogin;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);
        dbHelper = new DBHelper(this);

        initViews();
    }

    public void gotoRegister(View View) {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public void onLoginClick(View view) {

        //code without validateUser()
        String strUsername = editTextUsername.getText().toString();
        String strPassword = editTextPassword.getText().toString();

        Users currentUser = dbHelper.AuthenticateUser(new Users(null, strUsername, null, null, strPassword, null, null));
        if (currentUser != null) {
//            Snackbar.make(buttonLogin, "Successfully Logged in!", Snackbar.LENGTH_LONG).show();
            Intent homeIntent = new Intent(this, HomeActivity.class);
            homeIntent.putExtra("userName", strUsername);
            startActivity(homeIntent);

            Toast.makeText(this, "Successfully Logged in!", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(this, MainActivity.class));
            // overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
        } else {
//            Snackbar.make(buttonLogin, "Logged failed!", Snackbar.LENGTH_LONG).show();
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();

        }
        //Code with validateUser();
        /*if (validateUser()) {
            String strUsername = editTextUsername.getText().toString();
            String strPassword = editTextPassword.getText().toString();

            Users currentUser = dbHelper.AuthenticateUser(new Users(null, strUsername, null, null, strPassword, null, null));
            if (currentUser != null) {
                Snackbar.make(buttonLogin, "Successfully Logged in!", Snackbar.LENGTH_LONG).show();

                startActivity(new Intent(this, HomeActivity.class));
                overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
            } else {
                Snackbar.make(buttonLogin, "Logged failed!", Snackbar.LENGTH_LONG).show();
            }
        }*/

        finish();
    }

    private void initViews() {
        editTextUsername = findViewById(R.id.editText_LoginUserName);
        editTextPassword = findViewById(R.id.editText_LoginPassword);
        buttonLogin = findViewById(R.id.cirLoginButton);
        textInputLayoutEmail = findViewById(R.id.textInput_LoginUserName);
        textInputLayoutPassword = findViewById(R.id.textInput_LoginPassword);
    }

    public boolean validateUser() {
        boolean isValid = false;
        String strEmail = editTextUsername.getText().toString();
        String strPassword = editTextPassword.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            isValid = false;
            textInputLayoutEmail.setError("Please enter valid email");
            textInputLayoutEmail.setBoxStrokeColor(Color.RED);
        } else {
            isValid = true;
            textInputLayoutEmail.setError(null);
        }
        if (strPassword.isEmpty()) {
            isValid = false;
            textInputLayoutPassword.setError("Please Enter password");
            textInputLayoutEmail.setBoxStrokeColor(Color.RED);
        } else {
            if (strPassword.length() > 5) {
                isValid = true;
                textInputLayoutPassword.setError(null);
            } else {
                isValid = false;
                textInputLayoutPassword.setError("Password should be more than 5 chars");
            }
        }
        return isValid;
    }
}
