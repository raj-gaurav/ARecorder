package com.example.arecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    TextView txt,fpass;
    TextInputLayout username, password;
    Button login;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
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
                if(value.equals("1")){
                    adminAction();
                }
            }
        });
    }

    public void init(){
        txt=findViewById(R.id.txt);
        fpass=findViewById(R.id.fpass);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        login=findViewById(R.id.btnLogin);
    }

    public void adminAction()
    {
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
            finish();
        }
    }


}
