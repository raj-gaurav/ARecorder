package com.example.arecorder;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Random;

public class TeacherHomePage extends AppCompatActivity {

    TextView name,department,jyear,email,contact,tid,date,time,day,period,subid,classid,key,lat,lon;
    Button btn_gen;

    String t_id,da,ti,d;
    int count=0;
    DatabaseReference mDatabase;

    static TeacherHomePage instance;
    LocationRequest locationRequest;

    //TextView txt_location;


    FusedLocationProviderClient fusedLocationProviderClient;

    public static TeacherHomePage getInstance(){
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home_page);

        init();
        btn_gen.setEnabled(false);
        t_id=getIntent().getExtras().getString("tid");


        instance=this;
        section1();
        section2();
        section3();

        Dexter.withActivity(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        updateLocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(getApplicationContext(),"You must accept this location",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();




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
        lat=findViewById(R.id.lat);
        lon=findViewById(R.id.lon);
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
            d="Sunday";
        else if(dayOfWeek==2)
            d="Monday";
        else if(dayOfWeek==3)
            d="Tuesday";
        else if(dayOfWeek==4)
            d="Wednesday";
        else if(dayOfWeek==5)
            d="Thursday";
        else if(dayOfWeek==6)
            d="Friday";
        else if(dayOfWeek==7)
            d="Saturday";


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

            if(d.equals("SUNDAY")){
                period.setText("-");
                subid.setText("-");
                classid.setText("-");
            }
            else
            {
                mDatabase= FirebaseDatabase.getInstance().getReference().child("TeacherRoutine").child(t_id).child(d).child(pe);
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String s=dataSnapshot.child("sid").getValue(String.class);
                        String c=dataSnapshot.child("cid").getValue(String.class);
                        /*long z=dataSnapshot.getChildrenCount();*/
                        //Toast.makeText(getApplicationContext(),s+" "+c,Toast.LENGTH_SHORT).show();
                        subid.setText(s);
                        classid.setText(c);
                        //Toast.makeText(getApplicationContext())
                        btn_gen.setEnabled(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                    if(count!=0){
                        btn_gen.setEnabled(false);
                    }
                    else{
                        btn_gen.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int rand=(int) (1015620.0*Math.random());
                                final String k=subid.getText().toString().trim()+classid.getText().toString().trim()+String.valueOf(rand);
                                key.setText(k);
                                mDatabase=FirebaseDatabase.getInstance().getReference().child("Subject").child(subid.getText().toString().trim());
                                mDatabase.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String cid=dataSnapshot.child("cid").getValue().toString();
                                        String subid=dataSnapshot.child("subid").getValue().toString();
                                        String subject=dataSnapshot.child("subject").getValue().toString();
                                        String tclass=dataSnapshot.child("tclass").getValue().toString();
                                        String tid=dataSnapshot.child("tid").getValue().toString();

                                        incrementTotalClass(cid,subid,subject,tclass,tid,k);
                                        count++;
                                        btn_gen.setEnabled(false);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                    }





            }




        }
        else if(p==9){
            period.setText("LUNCH");
            subid.setText("-");
            classid.setText("-");

        }
        else{
            period.setText("-");
            subid.setText("-");
            classid.setText("-");

        }
    }

    public void incrementTotalClass(String cid, String subid,String subject,String tclass,String tid,String k){

        int z=0;
        mDatabase=FirebaseDatabase.getInstance().getReference().child("Subject").child(subid);
        DataSubject dataSubject=new DataSubject(subid,cid,subject,tid,String.valueOf(z),k);
        mDatabase.setValue(dataSubject);
    }

    public void updateLocation(){

        buildLocationRequest();

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,getPendingIntent());

    }

    private PendingIntent getPendingIntent() {

        Intent intent=new Intent(TeacherHomePage.this,MyLocationService.class);
        intent.setAction(MyLocationService.ACTION_PROCESS_UPDATE);

        return PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void buildLocationRequest() {
        locationRequest=new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10f);
    }

    public void updateTextView(final String latitude,final String longitude){
        TeacherHomePage.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*txt_location.setText(value);*/
                lat.setText(latitude);
                lon.setText(longitude);
                mDatabase=FirebaseDatabase.getInstance().getReference().child("Teacher").child(t_id);
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String name=dataSnapshot.child("name").getValue().toString();
                        String dep=dataSnapshot.child("department").getValue().toString();
                        String email=dataSnapshot.child("email").getValue().toString();
                        String pass=dataSnapshot.child("password").getValue().toString();
                        String jyear=dataSnapshot.child("joinyear").getValue().toString();
                        String contact=dataSnapshot.child("contact").getValue().toString();


                        mDatabase= FirebaseDatabase.getInstance().getReference().child("Teacher").child(t_id);
                        DataTeacher dataTeacher=new DataTeacher(t_id,name,dep,latitude,longitude,email,pass,jyear,contact);
                        mDatabase.setValue(dataTeacher);

                        //Toast.makeText(getApplicationContext(),"<---- till here ---->",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
    }


}
