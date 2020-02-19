package com.example.arecorder;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class StudentHomePage extends AppCompatActivity {

    TextView name,registration,croll,email,contact,sid,year,date,time,day,period,subject,tid,cid;
    TextInputLayout key;
    Button btn_submit;

    TextInputEditText edtxt_key;

    String sid_user,dt,ti,d, classid;

    DatabaseReference mDatabase,dref;

    static StudentHomePage instance;
    LocationRequest locationRequest;

    //TextView txt_location;


    FusedLocationProviderClient fusedLocationProviderClient;

    public static StudentHomePage getInstance(){
        return instance;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);

        init();

        sid_user=getIntent().getExtras().getString("sid");
        btn_submit.setEnabled(false);

        instance=this;
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
        edtxt_key=findViewById(R.id.edtxt_key);



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

       final String c_id=  cid.getText().toString().trim();


           // Toast.makeText(getApplicationContext(),d+" "+p+" "+c_id,Toast.LENGTH_SHORT).show();
        if(p>=1 && p<=8 )
        {
            final String pe=String.valueOf(p);
            period.setText(pe);

            if(d.equals("SUNDAY") ){
                period.setText("-");
                subject.setText("-");
                tid.setText("-");
            }
            else{
                mDatabase= FirebaseDatabase.getInstance().getReference().child("Routine").child(c_id).child(d).child(pe);

                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String subid =dataSnapshot.child("subid").getValue().toString();
                        subject.setText(subid);
                        if(!subid.equals("LIB"))
                        setTeacherId(subid,c_id);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }


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

    public void setTeacherId(final String t_s,final String c_id){


            dref=FirebaseDatabase.getInstance().getReference().child("Subject").child(t_s);
            dref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String teacherid=dataSnapshot.child("tid").getValue(String.class);
                    tid.setText(teacherid);
                    getStudentLocation(teacherid,t_s,c_id);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }

    public void updateLocation(){

        buildLocationRequest();

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,getPendingIntent());

    }

    private PendingIntent getPendingIntent() {

        Intent intent=new Intent(StudentHomePage.this,StudentLocationService.class);
        intent.setAction(StudentLocationService.ACTION_PROCESS_UPDATE);

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
        StudentHomePage.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*txt_location.setText(value);*/

                mDatabase=FirebaseDatabase.getInstance().getReference().child("Student").child(sid_user);
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String name=dataSnapshot.child("name").getValue().toString();
                        String dep=dataSnapshot.child("department").getValue().toString();
                        String sec=dataSnapshot.child("section").getValue().toString();
                        String email=dataSnapshot.child("email").getValue().toString();
                        String pass=dataSnapshot.child("password").getValue().toString();
                        String jyear=dataSnapshot.child("joinyear").getValue().toString();
                        String contact=dataSnapshot.child("contact").getValue().toString();
                        String croll=dataSnapshot.child("classroll").getValue().toString();
                        String registration=dataSnapshot.child("registration").getValue().toString();


                        mDatabase= FirebaseDatabase.getInstance().getReference().child("Student").child(sid_user);
                        DataStudent dataStudent=new DataStudent(sid_user,name,dep,sec,latitude,longitude,email,pass,jyear,contact,croll,registration);
                        mDatabase.setValue(dataStudent);

                        //Toast.makeText(getApplicationContext(),"Data updated successfully",Toast.LENGTH_SHORT).show();

                        //Toast.makeText(getApplicationContext(),"<---- till here ---->",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
    }

    public void getStudentLocation(final String t_id,final String t_s,final String c_id){

        final String s_id=sid_user;

        //Toast.makeText(getApplicationContext(),t_id+" "+s_id,Toast.LENGTH_SHORT).show();
        mDatabase=FirebaseDatabase.getInstance().getReference().child("Student").child(s_id);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 String s_lat=dataSnapshot.child("latitude").getValue().toString();
                 String s_lon=dataSnapshot.child("longitude").getValue().toString();
                 getTeacherLocation(s_lat,s_lon,t_id,t_s,c_id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public void getTeacherLocation(final String s_lat, final String s_lon,final String t_id,final String t_s,final String c_id){
        dref=FirebaseDatabase.getInstance().getReference().child("Teacher").child(t_id);
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String t_lat=dataSnapshot.child("latitude").getValue(String.class);
                String t_lon=dataSnapshot.child("longitude").getValue(String.class);
                //Toast.makeText(getApplicationContext(),s_lat+" "+s_lon,Toast.LENGTH_SHORT).show();
                calculateDistance(s_lat,s_lon,t_lat,t_lon,t_s,c_id);
                //
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void calculateDistance(String s_lat,String s_lon,String t_lat,String t_lon,final String t_s,final String c_id){
        //

        double stu_lat,stu_lon,tea_lat,tea_lon;
        tea_lat=Double.parseDouble(t_lat);
        tea_lon=Double.parseDouble(t_lon);
        stu_lat=Double.parseDouble(s_lat);
        stu_lon=Double.parseDouble(s_lon);
        float[] results = new float[1];
        Location.distanceBetween(tea_lat, tea_lon,
                stu_lat, stu_lon, results);
        float distance = results[0];

        //Toast.makeText(getApplicationContext(),String.valueOf(distance),Toast.LENGTH_SHORT ).show();
        float fix_dis= (float) 3.0;

        if(distance<=fix_dis){
            btn_submit.setEnabled(true);
            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    validateKey(t_s,c_id);
                }
            });
        }


    }



    public void validateKey(final String subid, final String classid){
        mDatabase=FirebaseDatabase.getInstance().getReference().child("Subject").child(subid);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key1=dataSnapshot.child("key").getValue().toString();
                if(edtxt_key.getText().toString().trim().equals(key1)){
                    makeStudentAttendance(subid,classid);
                }
                else{
                    key.setError("Invalid Key");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void makeStudentAttendance(final String subid, final String classid){
        LocalDate currentdate = LocalDate.now();
        final Month currentMonth = currentdate.getMonth();
        mDatabase=FirebaseDatabase.getInstance().getReference().child("Attendance").child(classid).child(sid_user).child(String.valueOf(currentMonth)).child(subid);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int i= (int) dataSnapshot.getChildrenCount();
                if(i==0){
                    getTeacherID(classid,String.valueOf(currentMonth),"0",sid_user,subid,"0");
                }
                else{
                    String c_id=dataSnapshot.child("c_id").getValue().toString();
                    String month=dataSnapshot.child("month").getValue().toString();
                    String present=dataSnapshot.child("present").getValue().toString();
                    String sid=dataSnapshot.child("sid").getValue().toString();
                    String sub_id=dataSnapshot.child("subid").getValue().toString();
                    String tid=dataSnapshot.child("tid").getValue().toString();
                    String total=dataSnapshot.child("total").getValue().toString();


                    incrementAttendance(c_id,month,present,sid,sub_id,tid,total);
                }
                Toast.makeText(getApplicationContext(), classid+" "+sid_user+" "+String.valueOf(currentMonth)+" "+subid+" "+String.valueOf(i), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void incrementAttendance(String cid,String mon,String pre,String sid,String subid,String tid,String total){
        int z=Integer.parseInt(pre)+1;
        mDatabase=FirebaseDatabase.getInstance().getReference().child(cid).child(sid).child(mon).child(subid);
        DataAttendance dataAttendance=new DataAttendance(cid,sid,mon,subid,tid,String.valueOf(z),"0");
        mDatabase.setValue(dataAttendance);
    }

    public void getTeacherID(final String cid, final String mon, final String pre, final String sid, final String subid, final String total){
        mDatabase=FirebaseDatabase.getInstance().getReference().child("Subject").child(subid);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String tid=dataSnapshot.child("tid").getValue().toString();
                addAttributes(cid,mon,pre,sid,subid,tid,total);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addAttributes(String cid,String mon,String pre,String sid,String subid,String tid,String total){
        mDatabase=FirebaseDatabase.getInstance().getReference().child("Attendace").child(cid).child(sid).child(mon).child(subid);
        DataAttendance dataAttendance=new DataAttendance(cid,sid,mon,subid,tid,pre,total);
        mDatabase.setValue(dataAttendance);
    }

}
