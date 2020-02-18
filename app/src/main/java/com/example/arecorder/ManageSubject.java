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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class ManageSubject extends AppCompatActivity {

    ListView lv;
    FloatingActionButton fab;

    ArrayList<String> list=new ArrayList<String>();

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_subject);

        init();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogSubject();
            }
        });

        showAllSubject();

    }

    public void init(){
        lv=findViewById(R.id.lv);
        fab=findViewById(R.id.fab);
    }

    public void customDialogSubject(){
        AlertDialog.Builder myDialog= new AlertDialog.Builder(ManageSubject.this);

        LayoutInflater inflater=LayoutInflater.from(ManageSubject.this);
        View add_subject=inflater.inflate(R.layout.add_subject,null);

        final AlertDialog dialog=myDialog.create();

        dialog.setView(add_subject);


        final TextInputLayout subid=add_subject.findViewById(R.id.subid);
        final TextInputLayout branch=add_subject.findViewById(R.id.branch);
        final TextInputLayout section=add_subject.findViewById(R.id.section);
        final TextInputLayout year=add_subject.findViewById(R.id.year);
        final TextInputLayout subject=add_subject.findViewById(R.id.subject);
        final TextInputLayout t_id=add_subject.findViewById(R.id.tid);
        final TextInputLayout total=add_subject.findViewById(R.id.total);
        final Button btn_ins=add_subject.findViewById(R.id.btn_ins);



        btn_ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sid=subid.getEditText().getText().toString().trim();
                String b=branch.getEditText().getText().toString().trim();
                String sec=section.getEditText().getText().toString().trim();
                String y=year.getEditText().getText().toString().trim();
                String sub=subject.getEditText().getText().toString().trim();
                String tid=t_id.getEditText().getText().toString().trim();
                String tclass=total.getEditText().getText().toString().trim();

                int temp=Integer.parseInt(y);
                int id_year=0;

                int year = Calendar.getInstance().get(Calendar.YEAR);
                if(temp==4)
                {
                    id_year=year-4;
                }
                else if(temp==3){
                    id_year=year-3;
                }
                else if(temp==2){
                    id_year=year-2;
                }
                else if(temp==1){
                    id_year=year-1;
                }

                String cid=b+sec+id_year+y;

                mDatabase= FirebaseDatabase.getInstance().getReference().child("Subject");

                DataSubject dataSubject=new DataSubject(sid,cid,sub,tid,tclass," ");
                mDatabase.child(sid).setValue(dataSubject);

                Toast.makeText(getApplicationContext(),"Data Added successfully",Toast.LENGTH_SHORT).show();

                dialog.dismiss();

            }
        });

        dialog.show();

    }

    public void showAllSubject(){
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);



        mDatabase=FirebaseDatabase.getInstance().getReference().child("Subject");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                list.add(dataSnapshot.child("subid").getValue(String.class) +"   "+ dataSnapshot.child("tclass").getValue(String.class));
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
