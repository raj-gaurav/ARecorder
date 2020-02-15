package com.example.arecorder;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class TeacherHomePage extends AppCompatActivity {

    TextView name,department,jyear,email,contact,tid,date,time,day,period,subid,classid,key;
    Button btn_gen;

    String t_id,da,ti,d;

    DatabaseReference mDatabase;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home_page);

        init();
        t_id=getIntent().getExtras().getString("tid");
        section1();
        section2();
        section3();
    }

    public void init(){
        name=findViewById(R.id.name);
        department=findViewById(R.id.department);
        jyear=findViewById(R.id.jyear);
        email=findViewById(R.id.email);
        contact=findViewById(R.id.contact);
        tid=findViewById(R.id.tid);
        date=findViewById(R.id.date);
        time=findViewById(R.id.time);
        day=findViewById(R.id.day);
        period=findViewById(R.id.period);
        subid=findViewById(R.id.subid);
        classid=findViewById(R.id.classid);
        key=findViewById(R.id.key);
        btn_gen=findViewById(R.id.btn_gen);
    }
    public void section1(){
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Teacher").child(t_id);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String n=dataSnapshot.child("name").getValue().toString();
                String d=dataSnapshot.child("department").getValue().toString();
                String jy=dataSnapshot.child("joinyear").getValue().toString();
                String e=dataSnapshot.child("email").getValue().toString();
                String c=dataSnapshot.child("contact").getValue().toString();


                name.setText(n);
                department.setText(d);
                jyear.setText(jy);
                email.setText(e);
                contact.setText(c);
                tid.setText(t_id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void section2(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm");

        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        LocalDate now = LocalDate.now();
        LocalTime nowt=LocalTime.now();
        da = dtf.format(now);
        ti=tf.format(nowt);
        date.setText(da);
        time.setText(ti);

        if(dayOfWeek==1)
            d="SUNDAY";
        else if(dayOfWeek==2)
            d="MONDAY";
        else if(dayOfWeek==3)
            d="TUESDAY";
        else if(dayOfWeek==4)
            d="WEDNESDAY";
        else if(dayOfWeek==5)
            d="THURSDAY";
        else if(dayOfWeek==6)
            d="FRIDAY";
        else if(dayOfWeek==7)
            d="SATURDAY";


        day.setText(d);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void section3(){
        String t1="09:30:00+05:30";
        String t2="10:20:00+05:30";
        String t3="11:10:00+05:30";
        String t4="12:00:00+05:30";
        String t5="12:50:00+05:30";
        String t6="13:40:00+05:30";
        String t7="14:30:00+05:30";
        String t8="15:20:00+05:30";
        String t9="16:10:00+05:30";
        String t10="17:00:00+05:30";

        DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime nowt=LocalTime.now();
        ti=tf.format(nowt);
        OffsetTime cu_t=OffsetTime.parse(ti+"+05:30");
        OffsetTime p1=OffsetTime.parse(t1);
        OffsetTime p2=OffsetTime.parse(t2);
        OffsetTime p3=OffsetTime.parse(t3);
        OffsetTime p4=OffsetTime.parse(t4);
        OffsetTime p5=OffsetTime.parse(t5);
        OffsetTime p6=OffsetTime.parse(t6);
        OffsetTime p7=OffsetTime.parse(t7);
        OffsetTime p8=OffsetTime.parse(t8);
        OffsetTime p9=OffsetTime.parse(t9);
        OffsetTime p10=OffsetTime.parse(t10);
        int p=0;

        if(cu_t.isAfter(p1) && cu_t.isBefore(p2))
            p=1;
        else if(cu_t.isAfter(p2) && cu_t.isBefore(p3))
            p=2;
        else if(cu_t.isAfter(p3) && cu_t.isBefore(p4))
            p=3;
        else if(cu_t.isAfter(p4) && cu_t.isBefore(p5))
            p=4;
        else if(cu_t.isAfter(p6) && cu_t.isBefore(p7))
            p=5;
        else if(cu_t.isAfter(p7) && cu_t.isBefore(p8))
            p=6;
        else if(cu_t.isAfter(p8) && cu_t.isBefore(p9))
            p=7;
        else if(cu_t.isAfter(p9) && cu_t.isBefore(p10))
            p=8;
        else if(cu_t.isAfter(p5) && cu_t.isBefore(p6))
            p=9;
        else
            p=10;
        if(p>=1 && p<=8)
        {
            final String pe=String.valueOf(p);
            period.setText(pe);
            mDatabase= FirebaseDatabase.getInstance().getReference().child("TeacherRoutine").child(t_id).child(d).child(pe);
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String s=dataSnapshot.child("sid").getValue().toString();
                    String c=dataSnapshot.child("cid").getValue().toString();
                    subid.setText(s);
                    classid.setText(c);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            btn_gen.setEnabled(true);

        }
        else if(p==9){
            period.setText("LUNCH");
            subid.setText("-");
            classid.setText("-");
            btn_gen.setEnabled(false);
        }
        else{
            period.setText("-");
            subid.setText("-");
            classid.setText("-");
            btn_gen.setEnabled(false);
        }
    }
}
