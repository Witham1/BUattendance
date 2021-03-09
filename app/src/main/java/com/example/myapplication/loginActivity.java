package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;

public class loginActivity extends AppCompatActivity {
    public EditText studentID;
    public String sID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void saveID(View v) {
        studentID = findViewById(R.id.studentID);
        sID = studentID.getText().toString();
        System.out.println(sID);
        Intent intent = new Intent(loginActivity.this, MainActivity.class);
        intent.putExtra("sID", sID);
        startActivity(intent);

    }



    }

