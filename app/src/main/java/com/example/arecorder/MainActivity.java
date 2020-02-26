package com.example.arecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button student,teacher,admin;
    String k,p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        k="page";
        p="MainActivity";
        adminAction();
        studentAction();
        teacherAction();
    }

    public void init(){
        student=findViewById(R.id.student);
        teacher=findViewById(R.id.teacher);
        admin=findViewById(R.id.admin);
    }

    public void adminAction(){
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),Login.class);
                i.putExtra("key","1");
                i.putExtra(k,p);
                startActivity(i);
                finish();

            }
        });

    }
    public void teacherAction(){
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),Login.class);
                i.putExtra("key","2");
                i.putExtra(k,p);
                startActivity(i);
                finish();
            }
        });
    }

    public void studentAction(){
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),Login.class);
                i.putExtra("key","3");
                i.putExtra(k,p);
                startActivity(i);
                finish();
            }
        });
    }

}
