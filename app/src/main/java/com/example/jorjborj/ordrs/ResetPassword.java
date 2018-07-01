package com.example.jorjborj.ordrs;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jorjborj.ordrs.R;

/**
 * Created by jorjborj on 7/2/2018.
 */

public class ResetPassword extends AppCompatActivity {

    AnimationDrawable anim;
    LinearLayout container;
    EditText currentPass,newPass1,newPass2;
    DatabaseHelper db;
    Button resetBtn,cancelBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        db = new DatabaseHelper(this);
        db.getWritableDatabase();

        container = (LinearLayout)findViewById(R.id.container);
        anim = (AnimationDrawable) container.getBackground();
        anim.setEnterFadeDuration(4000);
        anim.setExitFadeDuration(4000);

        resetBtn = (Button)findViewById(R.id.resetBtn);
        cancelBtn = (Button)findViewById(R.id.cancelBtn);
        currentPass = (EditText)findViewById(R.id.oldpass);
        newPass1 = (EditText)findViewById(R.id.newpass1);
        newPass2 = (EditText)findViewById(R.id.newpass2);


        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPass.getText().toString().equals(db.getAdminPassword())){
                    if(newPass1.getText().toString().equals(newPass2.getText().toString())){
                        db.updatePassword(currentPass.getText().toString(),newPass1.getText().toString());
                        Toast.makeText(ResetPassword.this, "Password reset successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (anim != null && !anim.isRunning())
            anim.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (anim != null && anim.isRunning())
            anim.stop();
    }


    }
