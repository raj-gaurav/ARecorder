package com.example.arecorder;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class StudentHomePage extends AppCompatActivity {

    TextView name,registration,croll,email,contact,sid,year,date,time,day,period,subject,tid,cid;
    TextInputLayout key;
    Button btn_submit;

    String sid_user,dt,ti,d, classid;

    DatabaseReference mDatabase,dref,dref1;

    /*public StudentHomePage(){

    }
    public StudentHomePage(String id){
        this.classid=id;
    }*/

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);

        init();

        sid_user=getIntent().getExtras().getString("sid");

        putData();
        //Toast.makeText(getApplicationContext()," "+d+" "+classid,Toast.LENGTH_SHORT).show();
        //section 2 start

            section2();

        //section 2 end

        //section 3 start

        try {
            section3();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //section 3 end

    }

    public void init(){
        name=findViewById(R.id.name);
        registration=findViewById(R.id.registration);
        croll=findViewById(R.id.croll);
        email=findViewById(R.id.email);
        contact=findViewById(R.id.contact);
        sid=findViewById(R.id.sid);
        year=findViewById(R.id.year);
        date=findViewById(R.id.date);
        time=findViewById(R.id.time);
        day=findViewById(R.id.day);
        period=findViewById(R.id.period);
        subject=findViewById(R.id.subject);
        tid=findViewById(R.id.tid);
        cid=findViewById(R.id.cid);
        key=findViewById(R.id.key);
        btn_submit=findViewById(R.id.btn_submit);



    }



    public void putData(){
       mDatabase= FirebaseDatabase.getInstance().getReference().child("Student").child(sid_user);
       mDatabase.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String n=dataSnapshot.child("name").getValue().toString();
               String r=dataSnapshot.child("registration").getValue().toString();
               String cr=dataSnapshot.child("classroll").getValue().toString();
               String em=dataSnapshot.child("email").getValue().toString();
               String con=dataSnapshot.child("contact").getValue().toString();
               String stuid=dataSnapshot.child("id").getValue().toString();
               String dep=dataSnapshot.child("department").getValue().toString();
               String sec=dataSnapshot.child("section").getValue().toString();
               String jyear=dataSnapshot.child("joinyear").getValue().toString();
               String y=String.valueOf(Calendar.getInstance().get(Calendar.YEAR)-Integer.parseInt(jyear));
               String syear=String.valueOf(Calendar.getInstance().get(Calendar.YEAR)-Integer.parseInt(y));
               classid=dep+sec+syear+y;





               name.setText(n);
               registration.setText(r);
               croll.setText(cr);
               email.setText(em);
               contact.setText(con);
               sid.setText(stuid);
               year.setText(y);
               cid.setText(classid);




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
        dt = dtf.format(now);
        ti=tf.format(nowt);
        date.setText(dt);
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
    public void section3() throws ParseException {
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

       String c_id=  cid.getText().toString().trim();


           // Toast.makeText(getApplicationContext(),d+" "+p+" "+c_id,Toast.LENGTH_SHORT).show();
        if(p>=1 && p<=8)
        {
            final String pe=String.valueOf(p);
            btn_submit.setEnabled(false);
            period.setText(pe);
            mDatabase= FirebaseDatabase.getInstance().getReference().child("Routine").child(c_id).child(d).child(pe);
            final String[] subid = {"null"};
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    subid[0] =dataSnapshot.child("subid").getValue().toString();
                    subject.setText(subid[0]);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
                /*String t_s=subject.getText().toString().trim();
            dref=FirebaseDatabase.getInstance().getReference().child("Subject").child(t_s);
            dref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String teacherid=dataSnapshot.child("tid").getValue(String.class);
                    //tid.setText(teacherid);
                    long i=  dataSnapshot.getChildrenCount();
                    Toast.makeText(getApplicationContext(),"i= "+i,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });*/
                tid.setText("");
        }
        else if(p==9)
        {
            period.setText("LUNCH");
            subject.setText("-");
            tid.setText("-");
        }
        else{
            period.setText("-");
            subject.setText("-");
            tid.setText("-");
        }









    }
}
