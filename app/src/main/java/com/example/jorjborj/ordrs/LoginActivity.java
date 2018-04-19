package com.example.jorjborj.ordrs;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    //create flash screen!
    //stam test


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.INPUT);
        Button loginBtn = (Button)findViewById(R.id.loginBtn);
        final TextInputEditText username = (TextInputEditText)findViewById(R.id.usernameInput);
        final TextInputEditText password = (TextInputEditText)findViewById(R.id.passwordInput);

            username.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent i = new Intent(LoginActivity.this,MainActivity.class);
                Intent i = new Intent(LoginActivity.this,PickOptionActivity.class);
                startActivity(i);
                        finish();


            }
        });


    }
}
