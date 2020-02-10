package com.example.arecorder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TeacherActivity extends AppCompatActivity {

    FloatingActionButton fab;
    ListView lv;

    ArrayList<String> list=new ArrayList<String>();

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        init();

        mAuth=FirebaseAuth.getInstance();



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogTeacher();
            }
        });

        showAllTeacher();
    }

    public void init(){
        fab=findViewById(R.id.fab);
        lv=findViewById(R.id.lv);
    }

    public void customDialogTeacher(){
        AlertDialog.Builder myDialog= new AlertDialog.Builder(TeacherActivity.this);

        LayoutInflater inflater=LayoutInflater.from(TeacherActivity.this);
        View add_teacher=inflater.inflate(R.layout.add_teacher,null);

        final AlertDialog dialog=myDialog.create();

        dialog.setView(add_teacher);

        final TextInputLayout tid=add_teacher.findViewById(R.id.tid);
        final TextInputLayout name=add_teacher.findViewById(R.id.name);
        final TextInputLayout department=add_teacher.findViewById(R.id.department);
        final TextInputLayout email=add_teacher.findViewById(R.id.email);
        final TextInputLayout password=add_teacher.findViewById(R.id.password);
        final TextInputLayout joinyear=add_teacher.findViewById(R.id.joinyear);
        final TextInputLayout contact=add_teacher.findViewById(R.id.contact);
        final Button btn_ins=add_teacher.findViewById(R.id.btn_ins);



        btn_ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t_id=tid.getEditText().getText().toString().trim();
                String t_name=name.getEditText().getText().toString().trim();
                String t_dep=department.getEditText().getText().toString().trim();
                String t_email=email.getEditText().getText().toString().trim();
                String t_pass=password.getEditText().getText().toString().trim();
                String t_jyear=joinyear.getEditText().getText().toString().trim();
                String t_contact=contact.getEditText().getText().toString().trim();

                mDatabase= FirebaseDatabase.getInstance().getReference().child("Teacher").child(t_id);

                //Registration start
                mAuth.createUserWithEmailAndPassword(t_email,t_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"successfully registered", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Registration Incomplete", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                //Registration end

                DataTeacher dataTeacher=new DataTeacher(t_id,t_name,t_dep,"","",t_email,t_pass,t_jyear,t_contact);
                mDatabase.setValue(dataTeacher);

                Toast.makeText(getApplicationContext(),"Data Added successfully",Toast.LENGTH_SHORT).show();

                dialog.dismiss();





            }
        });
        dialog.show();






    }

    public void showAllTeacher(){

        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);

         lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

             }
         });

        mDatabase=FirebaseDatabase.getInstance().getReference().child("Teacher");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                list.add(dataSnapshot.child("id").getValue(String.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                list.remove(dataSnapshot.getValue(String.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
}
