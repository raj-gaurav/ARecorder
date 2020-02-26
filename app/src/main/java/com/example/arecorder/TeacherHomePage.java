package com.example.arecorder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.Manifest;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class TeacherHomePage extends AppCompatActivity {

    TextView name,department,jyear,email,contact,tid,date,time,day,period,subid,classid,key,lat,lon,man;
    Button btn_gen;

    String t_id,da,ti,d,flag,f,key1;
    int count=0;
    DatabaseReference mDatabase,ref,dref;
    Toolbar toolbar;
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
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Teacher Home");
        btn_gen.setEnabled(false);
        t_id=getIntent().getExtras().getString("tid");


        instance=this;
        section1();
        section2();
        section3();
        Toast.makeText(getApplicationContext(), subid.getText().toString().trim(), Toast.LENGTH_SHORT).show();

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

        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeManaualAttendance();
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.logout)
        {
            Intent i=new Intent(getApplicationContext(),Login.class);
            i.putExtra("key","2");
            startActivity(i);
            finish();
        }

        return true;
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
        man=findViewById(R.id.manAtt);

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

        setFlagBitgettingSID(t_id,d,String.valueOf(p));
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

                        dref=FirebaseDatabase.getInstance().getReference().child("Subject").child(s);
                        dref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               String f=dataSnapshot.child("flag").getValue(String.class);
                               String k=dataSnapshot.child("key").getValue(String.class);
                               if(f.equals("0")){
                                   btn_gen.setEnabled(false);
                                   btn_gen.setText("Key Generated");
                                   key.setText(k);
                               }
                               else
                               {
                                   btn_gen.setEnabled(true);

                               }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });






                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                        btn_gen.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int rand=(int) (1015620.0*Math.random());
                                final String k=subid.getText().toString().trim()+classid.getText().toString().trim()+String.valueOf(rand);
                                key.setText(String.valueOf(rand));
                                mDatabase=FirebaseDatabase.getInstance().getReference().child("Subject").child(subid.getText().toString().trim());
                                mDatabase.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String cid=dataSnapshot.child("cid").getValue().toString();
                                        String subid=dataSnapshot.child("subid").getValue().toString();
                                        String subject=dataSnapshot.child("subject").getValue().toString();
                                        String tclass=dataSnapshot.child("tclass").getValue().toString();
                                        String tid=dataSnapshot.child("tid").getValue().toString();
                                        flag=dataSnapshot.child("flag").getValue(String.class);
                                        /*String date=dataSnapshot.child("date").getValue(String.class);
                                        String period=dataSnapshot.child("period").getValue(String.class);*/
                                        //int t=Integer.parseInt(tclass);

                                        //DatabaseReference ref=FirebaseDatabase.getInstance().getReference();
                                        incrementTotalClass(cid,subid,subject,tclass,tid,k,flag);
                                        count++;
                                        btn_gen.setEnabled(false);
                                        btn_gen.setText("Key Generated.");
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        });






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

    public void incrementTotalClass(String cid, String subid, String subject, String tclass, String tid, String k, String flag){

        if(flag.equals("1")){
            flag="0";
            int z = Integer.parseInt(tclass);
            z+=1;
            Toast.makeText(getApplicationContext(),String.valueOf(z),Toast.LENGTH_SHORT).show();
            mDatabase=FirebaseDatabase.getInstance().getReference().child("Subject").child(subid);
            DataSubject dataSubject=new DataSubject(subid,cid,subject,tid,String.valueOf(z),k,flag,da,period.getText().toString().trim());
            mDatabase.setValue(dataSubject);



        }




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
                /*lat.setText(latitude);
                lon.setText(longitude);*/
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

    public void setFlagBitgettingSID(String tid,String day,String pe){
        int per=Integer.parseInt(pe);
        if(per>=1&&per<=8){
            mDatabase=FirebaseDatabase.getInstance().getReference().child("TeacherRoutine").child(tid).child(day).child(pe);
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String sid=dataSnapshot.child("sid").getValue(String.class);


                        setFlagBit(sid);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    public void setFlagBit(String sid) {
        ref=FirebaseDatabase.getInstance().getReference().child("Subject").child(sid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String cid=dataSnapshot.child("cid").getValue().toString();
                String subid=dataSnapshot.child("subid").getValue().toString();
                String subject=dataSnapshot.child("subject").getValue().toString();
                String tclass=dataSnapshot.child("tclass").getValue().toString();
                String tid=dataSnapshot.child("tid").getValue().toString();
                String key=dataSnapshot.child("key").getValue().toString();
                String flag=dataSnapshot.child("flag").getValue(String.class);
                String date=dataSnapshot.child("date").getValue(String.class);
                String period1=dataSnapshot.child("period").getValue(String.class);

                if(date==null || period1==null){
                    date="00/00/000";
                    period1="0";

                }



                try{
                    SimpleDateFormat sdfo= new SimpleDateFormat("dd/MM/yyyy");

                    Date d1 = sdfo.parse(date);
                    Date d2 = sdfo.parse(da);
                    //Toast.makeText(getApplicationContext(), date+ " "+ da, Toast.LENGTH_SHORT).show();
                    if(d1.compareTo(d2)==0 && period1.equals(period.getText().toString().trim())){
                        DataSubject dataSubject=new DataSubject(subid,cid,subject,tid,tclass,key,flag,date,period1);
                        ref.setValue(dataSubject);
                    }
                    else{
                        DataSubject dataSubject=new DataSubject(subid,cid,subject,tid,"0","","1",da,period.getText().toString().trim());
                        ref.setValue(dataSubject);
                    }
                }catch(ParseException e){
                    e.printStackTrace();
                 //   Toast.makeText(getApplicationContext(), (CharSequence) e, Toast.LENGTH_SHORT).show();
                }






            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void makeManaualAttendance(){
        AlertDialog.Builder myDialog= new AlertDialog.Builder(TeacherHomePage.this);

        LayoutInflater inflater=LayoutInflater.from(TeacherHomePage.this);

        View manual_attendance=inflater.inflate(R.layout.manual_attendance,null);

        final AlertDialog dialog=myDialog.create();

        dialog.setView(manual_attendance);

        final EditText stu=manual_attendance.findViewById(R.id.stu_id);
        final EditText sub=manual_attendance.findViewById(R.id.sub_id);
        final EditText tot=manual_attendance.findViewById(R.id.total_class);
        Button btn_makeAtt=manual_attendance.findViewById(R.id.btn_makeAtt);

        sub.setText(subid.getText().toString().trim());
        btn_makeAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String st=stu.getText().toString().trim();
                final String su=sub.getText().toString().trim();
                final String to=tot.getText().toString().trim();

                if(st.isEmpty() || su.isEmpty() || su.equals("-") || to.isEmpty()){
                    stu.setError("Field can't be empty");
                    sub.setError("Field can't be empty");
                }
                else{
                    DatabaseReference m;
                    m=FirebaseDatabase.getInstance().getReference().child("Subject").child(su);
                    m.addValueEventListener(new ValueEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String c=dataSnapshot.child("cid").getValue(String.class);
                            LocalDate currentdate = LocalDate.now();
                            Month currentMonth = currentdate.getMonth();
                            final String mon=String.valueOf(currentMonth);
                            final DatabaseReference y=FirebaseDatabase.getInstance().getReference().child("Attendace").child(c).child(st).child(mon).child(su);

                            y.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if(dataSnapshot.getChildrenCount()==0){
                                       Toast.makeText(getApplicationContext(),"First Class",Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        final String cid=dataSnapshot.child("c_id").getValue(String.class);
                                        final String sid=dataSnapshot.child("sid").getValue(String.class);
                                        final String mon=dataSnapshot.child("month").getValue(String.class);
                                        final String subid=dataSnapshot.child("subid").getValue(String.class);
                                        final String tid=dataSnapshot.child("tid").getValue(String.class);
                                        final String present=dataSnapshot.child("present").getValue(String.class);
                                        final String total=dataSnapshot.child("total").getValue(String.class);
                                        final String flag=dataSnapshot.child("flag").getValue(String.class);
                                        final String date=dataSnapshot.child("date").getValue(String.class);
                                        final String period=dataSnapshot.child("period").getValue(String.class);

                                        int z=Integer.parseInt(present);
                                        final String zi=String.valueOf(z+Integer.parseInt(to));

                                   /* AlertDialog.Builder builder=new AlertDialog.Builder(TeacherHomePage.this);
                                    builder.setCancelable(false);
                                    builder.setTitle("Confirmation");
                                    builder.setMessage("Are you sure you want to make attendance for "+sid);
                                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Toast.makeText(getApplicationContext(),zi,Toast.LENGTH_LONG).show();

                                        }
                                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    });*/

                                        if(total.equals("0")){
                                            DataAttendance dataAttendance=new DataAttendance(cid,sid,mon,subid,tid,zi,"1",flag,date,period);
                                            y.setValue(dataAttendance);
                                            dialog.dismiss();
                                        }
                                        else{

                                        }
                                    }





                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }


            }
        });



        dialog.show();
    }


}
