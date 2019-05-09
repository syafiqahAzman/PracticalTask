package com.myapplicationdev.android.practicaltask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnInsert, btnRetrieve;
    TextView tvResult;
    EditText etName, etGPA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.btnInsert);
        btnRetrieve = findViewById(R.id.btnRetrieve);
        tvResult = findViewById(R.id.Result);
        etName = findViewById(R.id.etName);
        etGPA = findViewById(R.id.etGPA);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);
                db.getWritableDatabase();

                String n = etName.getText().toString();
                String strGpa = etGPA.getText().toString();
                Double g = Double.parseDouble(strGpa);

                db.insertStudent(n, g);
                db.close();
            }
        });

        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);

                ArrayList<Student> data = db.getStudents();
                db.close();

                String txt = "";
                for(int i = 0; i < data.size(); i++){
                    Log.d("Database Content", i + ". " + data.get(i));
                    txt += data.get(i).getName() + "\n" + data.get(i).getGpa() ;
                }
                tvResult.setText(txt);
            }
        });
    }
}
