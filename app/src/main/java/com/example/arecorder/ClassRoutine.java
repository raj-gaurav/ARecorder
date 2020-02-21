package com.example.arecorder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ClassRoutine extends AppCompatActivity {

    ListView lv;
    FloatingActionButton fab;
    Toolbar toolbar;
    DatabaseReference mDatabase;

    ArrayList<String> list=new ArrayList<String>();

    String id=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_routine);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Class Routine");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lv=findViewById(R.id.lv);
        fab=findViewById(R.id.fab);

        id=getIntent().getExtras().getString("cid");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogRoutine();
            }
        });

    }

    public void customDialogRoutine(){
        AlertDialog.Builder myDialog= new AlertDialog.Builder(ClassRoutine.this);

        LayoutInflater inflater=LayoutInflater.from(ClassRoutine.this);
        View add_routine=inflater.inflate(R.layout.add_routine,null);

        final AlertDialog dialog=myDialog.create();

        dialog.setView(add_routine);

        final EditText rid=add_routine.findViewById(R.id.cid);
        final TextInputLayout day=add_routine.findViewById(R.id.day);
        final TextInputLayout period=add_routine.findViewById(R.id.period);
        final TextInputLayout subid=add_routine.findViewById(R.id.subid);
        final Button btn_ins=add_routine.findViewById(R.id.btn_ins);

        rid.setText(id);


        btn_ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String r=id;
                String d=day.getEditText().getText().toString().trim();
                String p=period.getEditText().getText().toString().trim();
                String s=subid.getEditText().getText().toString().trim();




                mDatabase= FirebaseDatabase.getInstance().getReference().child("Routine");

                DataRoutine dataRoutine=new DataRoutine(r,d,p,s,"0");
                mDatabase.child(r).child(d).child(p).setValue(dataRoutine);

                Toast.makeText(getApplicationContext(),"Data Added successfully",Toast.LENGTH_SHORT).show();

                dialog.dismiss();




            }
        });



        dialog.show();
    }

    public void showAllRoutine(){
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);

        mDatabase=FirebaseDatabase.getInstance().getReference().child("Routine");



        /*mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                list.add(dataSnapshot.child("rid").getValue(String.class));
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
        });*/
    }


}
