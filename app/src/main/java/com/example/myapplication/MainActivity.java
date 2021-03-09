package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import java.io.*;
import java.util.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import java.io.IOException;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {
    public String lecture;
    public String wk;
    public String text = "";
    public String url = "http://10.0.2.2/android_test/index.php"  ;

    private Button connect;
     public String studentNum;

    JSONParser jsonParser =new JSONParser();

    int i=0;



    Button scanBtn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //gets sNumber from first page
        if( getIntent().getExtras() != null)
        {
             studentNum = getIntent().getStringExtra("studentNum");
        }

        setContentView(R.layout.activity_main);

        scanBtn = findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(this);
        connect = findViewById(R.id.connect);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.AttendanceLog attendanceLog= new MainActivity.AttendanceLog();
                //split qrcode lec+wk string into two variables to send to php
                String[] lecList = lecture.split(",");
                lecture = lecList[0];
                wk = lecList[1];
                System.out.println(lecture + wk + studentNum + "init");
                attendanceLog.execute(lecture, wk,studentNum);

            }
        });
    }


    @Override
    public void onClick(View v) {
    scanCode();
    }

    private void scanCode(){

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);

        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Code");
        integrator.initiateScan();


    }
    @Override //launches qr scanner
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(result.getContents());
                lecture = result.getContents();
                System.out.println(lecture);
                builder.setTitle("Scanning Result");
                Toast.makeText(this, "aaaaa", Toast.LENGTH_LONG).show();
                builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        scanCode();
                    }
                }).setNegativeButton("Correct", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        closeContextMenu();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            } else {
                Toast.makeText(this, "No Result", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);

        }
    }


//sending json to php
@SuppressLint("StaticFieldLeak")
    public class AttendanceLog extends AsyncTask<String, String, JSONObject> {


    @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {



           // String email = args[2];
            String sNum = args[2];
            String wk = args[1];
            String lecture= args[0];
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("sNum", sNum));
            params.add(new BasicNameValuePair("wk", wk));
            params.add(new BasicNameValuePair("lecture", lecture));


            System.out.println(params);
            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);
            System.out.println(json);

            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            try {
                if (result != null) {
                    Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(),result.getInt("success"),Toast.LENGTH_LONG).show();
                    System.out.println(result.getInt("success"));



                } else {
                    Toast.makeText(getApplicationContext(), "Unable2222 to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }





}












