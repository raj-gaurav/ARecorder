package com.example.arecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Admin extends AppCompatActivity {

    Button teacher,student,classes,subject,routine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        init();


        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),TeacherActivity.class);
                startActivity(i);
            }
        });

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),StudentActivity.class);
                startActivity(i);
            }
        });

        classes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i=new Intent(getApplicationContext(),ManageClass.class);
                startActivity(i);*/
            }
        });

        subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i=new Intent(getApplicationContext(),ManageSubject.class);
                startActivity(i);*/
            }
        });

        routine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i=new Intent(getApplicationContext(),ManageRoutine.class);
                startActivity(i);*/
            }
        });


    }

    public void init(){
        teacher=findViewById(R.id.teacher);
        student=findViewById(R.id.student);
        classes=findViewById(R.id.classes);
        subject=findViewById(R.id.subject);
        routine=findViewById(R.id.routine);
    }




}
