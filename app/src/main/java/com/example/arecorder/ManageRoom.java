package com.example.arecorder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManageRoom extends AppCompatActivity {

    DatabaseReference mDatabase;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_room);

        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               customDialogRoom();
            }
        });
    }

    public void customDialogRoom(){
        AlertDialog.Builder myDialog= new AlertDialog.Builder(ManageRoom.this);

        LayoutInflater inflater=LayoutInflater.from(ManageRoom.this);
        View add_room=inflater.inflate(R.layout.add_room,null);

        final AlertDialog dialog=myDialog.create();

        dialog.setView(add_room);

        final EditText tid=add_room.findViewById(R.id.tid);
        final EditText cid=add_room.findViewById(R.id.cid);
        final EditText sid=add_room.findViewById(R.id.sid);
        final EditText day=add_room.findViewById(R.id.day);
        final EditText period=add_room.findViewById(R.id.period);
        final Button btn_ins=add_room.findViewById(R.id.btn_ins);

        btn_ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String r=tid.getText().toString().trim();
                String c=cid.getText().toString().trim();
                String s=sid.getText().toString().trim();
                String d=day.getText().toString().trim();
                String p=period.getText().toString().trim();
                mDatabase= FirebaseDatabase.getInstance().getReference().child("TeacherRoutine");

                DataRoom dataRoom=new DataRoom(r,d,p,c,s);
                mDatabase.child(r).child(d).child(p).setValue(dataRoom);

                Toast.makeText(getApplicationContext(),"Data Added successfully",Toast.LENGTH_SHORT).show();

                dialog.dismiss();

            }
        });
        dialog.show();
    }
}
