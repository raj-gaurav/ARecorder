package com.example.arecorder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.appcompat.widget.Toolbar;

public class Login extends AppCompatActivity {

    TextView txt,fpass;
    TextInputLayout username, password;
    Button login;
    String value;
    Toolbar toolbar;

    FirebaseAuth mauth;
    private ProgressDialog mdialog;
    DatabaseReference dref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mauth=FirebaseAuth.getInstance();
        mdialog=new ProgressDialog(this);
        value=getIntent().getExtras().getString("key");
        if(value.equals("1"))
        {
            txt.setText("ADMIN LOGIN");

        }
        else if(value.equals("2"))
        {
            txt.setText("TEACHER LOGIN");
        }
        else if(value.equals("3")){
            txt.setText("STUDENT LOGIN");

        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdialog.setMessage("Logging in...");
                mdialog.show();
                if(value.equals("1")){
                    adminAction();
                }
                else if(value.equals("2")){
                    teacherAction();
                }
                else if(value.equals("3")){
                    studentAction();
                }
            }
        });
    }

    public void init(){
        toolbar=findViewById(R.id.toolbar);
        txt=findViewById(R.id.txt);
        fpass=findViewById(R.id.fpass);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        login=findViewById(R.id.btnLogin);
    }

    public void adminAction(){
        int a=0,b=0;
        String user=username.getEditText().getText().toString().trim();
        String pass=password.getEditText().getText().toString().trim();
        if(user.isEmpty()){
            username.setError("Field can't be empty");
        }
        else if(user.equals("rajgauravraj97") ){
            username.setError(null);
            a=1;
        }
        else{
            username.setError("Invalid Username");
        }
        if(pass.isEmpty()){
            password.setError("Field can't be empty");
        }
        else if(pass.equals("123456789")){
            password.setError(null);
            b=1;
        }
        else{
            password.setError("Invalid Password");
        }

        if(a==1 && b==1)
        {

            Intent i=new Intent(getApplicationContext(),Admin.class);
            startActivity(i);
            mdialog.dismiss();
            finish();
        }
        else{
            mdialog.dismiss();
        }
    }

    public void teacherAction(){


        final String user=username.getEditText().getText().toString().trim();
        final String pass=password.getEditText().getText().toString().trim();

        if(user.isEmpty())
        {
            username.setError("Field can't be empty");
        }
         if(pass.isEmpty()){
            password.setError("Field can't be empty");
        }
        if(!user.isEmpty() && !pass.isEmpty()) {

            dref=FirebaseDatabase.getInstance().getReference().child("Teacher").child(user);

            if(dref.equals(null)){
                username.setError("Invalid");
            }
            else{
                dref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Toast.makeText(getApplicationContext(),"Here.....",Toast.LENGTH_SHORT).show();
                        String username1=dataSnapshot.child("id").getValue(String.class);
                        String password1=dataSnapshot.child("password").getValue(String.class);
                        String email=dataSnapshot.child("email").getValue(String.class);

                        if(user.equals(username1) && pass.equals(password1)){

                            Intent i=new Intent(getApplicationContext(),TeacherHomePage.class);
                            i.putExtra("tid",user);
                            startActivity(i);
                            mdialog.dismiss();
                            //Toast.makeText(getApplicationContext(),username1+" "+password1,Toast.LENGTH_SHORT).show();
                        }
                        else{
                            username.setError("Invalid Username");
                            password.setError("Invalid Password");
                            mdialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    public void studentAction(){


        final String user=username.getEditText().getText().toString().trim();
        final String pass=password.getEditText().getText().toString().trim();

        if(user.isEmpty())
        {
            username.setError("Field can't be empty");
        }
        if(pass.isEmpty()){
            password.setError("Field can't be empty");
        }
        if(!user.isEmpty() && !pass.isEmpty()) {

            dref=FirebaseDatabase.getInstance().getReference().child("Student").child(user);

            if(dref.equals(null)){
                username.setError("Invalid");
            }
            else{
                dref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //Toast.makeText(getApplicationContext(),"Here.....",Toast.LENGTH_SHORT).show();
                        String username1=dataSnapshot.child("id").getValue().toString();
                        String password1=dataSnapshot.child("password").getValue().toString();
                        String email=dataSnapshot.child("email").getValue().toString();

                        if(user.equals(username1) && pass.equals(password1)){
                            username.setError(null);
                            password.setError(null);
                            Intent i=new Intent(getApplicationContext(),StudentHomePage.class);
                            i.putExtra("sid",user);
                            startActivity(i);
                            mdialog.dismiss();
                        }
                        else{
                            username.setError("Invalid Username");
                            password.setError("Invalid Password");
                            mdialog.dismiss();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }
    }


}



