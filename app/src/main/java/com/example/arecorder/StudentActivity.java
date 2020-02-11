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
import android.widget.EditText;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {

    FloatingActionButton fab;
    ListView lv;

    ArrayList<String> list=new ArrayList<String>();

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase,dref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        init();

        mAuth=FirebaseAuth.getInstance();



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogStudent();
            }
        });

        showAllStudent();

    }

    public void init(){
        fab=findViewById(R.id.fab);
        lv=findViewById(R.id.lv);
    }

    public void customDialogStudent(){
        AlertDialog.Builder myDialog= new AlertDialog.Builder(StudentActivity.this);

        LayoutInflater inflater=LayoutInflater.from(StudentActivity.this);
        View add_student=inflater.inflate(R.layout.add_student,null);

        final AlertDialog dialog=myDialog.create();

        dialog.setView(add_student);

        final TextInputLayout tid=add_student.findViewById(R.id.tid);
        final TextInputLayout name=add_student.findViewById(R.id.name);
        final TextInputLayout department=add_student.findViewById(R.id.department);
        final TextInputLayout section=add_student.findViewById(R.id.section);
        final TextInputLayout email=add_student.findViewById(R.id.email);
        final TextInputLayout password=add_student.findViewById(R.id.password);
        final TextInputLayout joinyear=add_student.findViewById(R.id.joinyear);
        final TextInputLayout contact=add_student.findViewById(R.id.contact);
        final TextInputLayout classroll=add_student.findViewById(R.id.classroll);
        final TextInputLayout registration=add_student.findViewById(R.id.registration);
        final Button btn_ins=add_student.findViewById(R.id.btn_ins);



        btn_ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t_id=tid.getEditText().getText().toString().trim();
                String t_name=name.getEditText().getText().toString().trim();
                String t_dep=department.getEditText().getText().toString().trim();
                String t_sec=section.getEditText().getText().toString().trim();
                String t_email=email.getEditText().getText().toString().trim();
                String t_pass=password.getEditText().getText().toString().trim();
                String t_jyear=joinyear.getEditText().getText().toString().trim();
                String t_contact=contact.getEditText().getText().toString().trim();
                String t_croll=classroll.getEditText().getText().toString().trim();
                String t_registration=registration.getEditText().getText().toString().trim();


                mDatabase= FirebaseDatabase.getInstance().getReference().child("Student").child(t_id);

                /*//Registration start
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
                //Registration end*/

                DataStudent dataStudent=new DataStudent(t_id,t_name,t_dep,t_sec,"","",t_email,t_pass,t_jyear,t_contact,t_croll,t_registration);
                mDatabase.setValue(dataStudent);

                Toast.makeText(getApplicationContext(),"Data Added successfully",Toast.LENGTH_SHORT).show();

                dialog.dismiss();





            }
        });
        dialog.show();






    }

    public void showAllStudent(){

        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //update/delete dialog star

                AlertDialog.Builder myDialog= new AlertDialog.Builder(StudentActivity.this);

                LayoutInflater inflater=LayoutInflater.from(StudentActivity.this);
                View update_delete_student=inflater.inflate(R.layout.update_delete_student,null);

                final AlertDialog dialog=myDialog.create();

                dialog.setView(update_delete_student);

                final EditText tid=update_delete_student.findViewById(R.id.tid);
                final EditText tname=update_delete_student.findViewById(R.id.name);
                final EditText tdepartment=update_delete_student.findViewById(R.id.department);
                final EditText tsec=update_delete_student.findViewById(R.id.section);
                final EditText temail=update_delete_student.findViewById(R.id.email);
                final EditText tpassword=update_delete_student.findViewById(R.id.password);
                final EditText tjoinyear=update_delete_student.findViewById(R.id.joinyear);
                final EditText tcontact=update_delete_student.findViewById(R.id.contact);
                final EditText tcroll=update_delete_student.findViewById(R.id.croll);
                final EditText tregistration=update_delete_student.findViewById(R.id.registration);


                final Button btn_update=update_delete_student.findViewById(R.id.btn_update);
                final Button btn_delete=update_delete_student.findViewById(R.id.btn_delete);

                // Toast.makeText(getApplicationContext(), list.get(position),Toast.LENGTH_SHORT).show();
                dref=FirebaseDatabase.getInstance().getReference().child("Student").child(list.get(position));
                dref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        if(dataSnapshot.equals(null))
                        {
                            Toast.makeText(getApplicationContext(),"No Data found",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String name=dataSnapshot.child("name").getValue().toString();
                            String dep=dataSnapshot.child("department").getValue().toString();
                            String sec=dataSnapshot.child("section").getValue().toString();
                            String email=dataSnapshot.child("email").getValue().toString();
                            String pass=dataSnapshot.child("password").getValue().toString();
                            String jyear=dataSnapshot.child("joinyear").getValue().toString();
                            String contact=dataSnapshot.child("contact").getValue().toString();
                            String croll=dataSnapshot.child("classroll").getValue().toString();
                            String registration=dataSnapshot.child("registration").getValue().toString();




                            tid.setText(list.get(position));
                            tname.setText(name);
                            tdepartment.setText(dep);
                            tsec.setText(sec);
                            temail.setText(email);
                            tpassword.setText(pass);
                            tjoinyear.setText(jyear);
                            tcontact.setText(contact);
                            tcroll.setText(croll);
                            tregistration.setText(registration);

                            btn_update.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    String id=tid.getText().toString().trim();
                                    String name=tname.getText().toString().trim();
                                    String dep=tdepartment.getText().toString().trim();
                                    String sec=tsec.getText().toString().trim();
                                    String email=temail.getText().toString().trim();
                                    String pass=tpassword.getText().toString().trim();
                                    String jyear=tjoinyear.getText().toString().trim();
                                    String con=tcontact.getText().toString().trim();
                                    String croll=tcroll.getText().toString().trim();
                                    String registration=tregistration.getText().toString().trim();

                                    mDatabase= FirebaseDatabase.getInstance().getReference().child("Student").child(id);
                                    DataStudent dataStudent=new DataStudent(id,name,dep,sec,"","",email,pass,jyear,con,croll,registration);
                                    mDatabase.setValue(dataStudent);

                                    Toast.makeText(getApplicationContext(),"Data updated successfully",Toast.LENGTH_SHORT).show();

                                    dialog.dismiss();


                                }
                            });


                            btn_delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    mDatabase= FirebaseDatabase.getInstance().getReference().child("Student");
                                    mDatabase.child(list.get(position)).removeValue();
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"Data deleted successfully",Toast.LENGTH_SHORT);

                                }
                            });
                        }







                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                dialog.show();
                // update/delete dialog end
            }
        });

        mDatabase=FirebaseDatabase.getInstance().getReference().child("Student");
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
