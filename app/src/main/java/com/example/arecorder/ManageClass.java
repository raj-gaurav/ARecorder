package com.example.arecorder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
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
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ManageClass extends AppCompatActivity {

    ListView lv1,lv2,lv3,lv4;
    FloatingActionButton fab;

    ArrayList<String> list1=new ArrayList<String>();
    ArrayList<String> list2=new ArrayList<String>();
    ArrayList<String> list3=new ArrayList<String>();
    ArrayList<String> list4=new ArrayList<String>();

    DatabaseReference mDatabase,dref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_class);

        init();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogClass();
            }
        });

        showFirstYear();
        showSecondYear();
        showThirdYear();
        showFourthYear();
    }


    public void init(){
        lv1=findViewById(R.id.lv1);
        lv2=findViewById(R.id.lv2);
        lv3=findViewById(R.id.lv3);
        lv4=findViewById(R.id.lv4);
        fab=findViewById(R.id.fab);
    }

    public void customDialogClass(){
        AlertDialog.Builder myDialog= new AlertDialog.Builder(ManageClass.this);

        LayoutInflater inflater=LayoutInflater.from(ManageClass.this);
        View add_class=inflater.inflate(R.layout.add_class,null);

        final AlertDialog dialog=myDialog.create();

        dialog.setView(add_class);

        final TextInputLayout branch=add_class.findViewById(R.id.branch);
        final TextInputLayout section=add_class.findViewById(R.id.section);
        final TextInputLayout year=add_class.findViewById(R.id.year);
        final Button btn_ins=add_class.findViewById(R.id.btn_ins);

        btn_ins.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String b=branch.getEditText().getText().toString().trim();
                String s=section.getEditText().getText().toString().trim();
                String y=year.getEditText().getText().toString().trim();

                if(b.isEmpty()){
                    branch.setError("Field can't be empty");
                }
                if(s.isEmpty()){
                    branch.setError("Field can't be empty");
                }
                if(y.isEmpty()){
                    branch.setError("Field can't be empty");
                }
                 if(Integer.parseInt(y)>4 && Integer.parseInt(y) <1)
                 {
                     year.setError("Year should be between 1-4");
                 }
                if(!b.isEmpty() && !s.isEmpty() && !y.isEmpty() && Integer.parseInt(y)<=4 && Integer.parseInt(y) >=1){

                    int temp=Integer.parseInt(y);
                    int id_year = 0;
                    Date date = new Date();
                    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int month = localDate.getMonthValue();
                    int year = Calendar.getInstance().get(Calendar.YEAR);
                    String sem = null;
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

                    String id= b+s+Integer.toString(id_year)+y;
                    
                    if(month>7 && month<=12)
                    {
                        if(temp==1)
                            sem=Integer.toString(1);
                        else if(temp==2)
                            sem=Integer.toString(3);
                        else if (temp==3)
                            sem=Integer.toString(5);
                        else if(temp==4)
                            sem=Integer.toString(7);
                    }
                    else{
                        if(temp==1)
                            sem=Integer.toString(2);
                        else if(temp==2)
                            sem=Integer.toString(4);
                        else if (temp==3)
                            sem=Integer.toString(6);
                        else if(temp==4)
                            sem=Integer.toString(8);
                    }
                    
                    mDatabase= FirebaseDatabase.getInstance().getReference().child("Class");

                    DataClass dataClass=new DataClass(id,b,s,y,sem);
                    mDatabase.child(id).setValue(dataClass);

                    Toast.makeText(getApplicationContext(),"Data Added successfully",Toast.LENGTH_SHORT).show();

                    dialog.dismiss();

                }
            }
        });


        dialog.show();
    }

    public void showFirstYear(){
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list1);
        lv1.setAdapter(adapter);


        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder myDialog= new AlertDialog.Builder(ManageClass.this);

                LayoutInflater inflater=LayoutInflater.from(ManageClass.this);
                View update_delete_class=inflater.inflate(R.layout.update_delete_teacher,null);

                final AlertDialog dialog=myDialog.create();

                dialog.setView(update_delete_class);

                 final EditText cid=(EditText)update_delete_class.findViewById(R.id.cid);
                 EditText branch=update_delete_class.findViewById(R.id.branch);
                 EditText section=update_delete_class.findViewById(R.id.section);
                 EditText year=update_delete_class.findViewById(R.id.year);
                 EditText sem=update_delete_class.findViewById(R.id.sem);
                 Button btn_update=update_delete_class.findViewById(R.id.btn_update);
                 Button btn_delete=update_delete_class.findViewById(R.id.btn_delete);

                final String[] temp=list1.get(position).split("-");

                dref=FirebaseDatabase.getInstance().getReference().child("Class").child(temp[1]);


                dref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         String id=dataSnapshot.child("id").getValue().toString();
                         String b=dataSnapshot.child("branch").getValue().toString();
                         String s=dataSnapshot.child("section").getValue().toString();
                         String y=dataSnapshot.child("year").getValue().toString();
                         String se=dataSnapshot.child("sem").getValue().toString();

                        Toast.makeText(getApplicationContext(),id+b+s+y+se,Toast.LENGTH_SHORT).show();

                       /*cid.setText(id);
                        branch.setText(b);
                        section.setText(s);
                        year.setText(y);
                        sem.setText(se);

                        btn_update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String id=cid.getText().toString().trim();
                                String br=branch.getText().toString().trim();
                                String sec=section.getText().toString().trim();
                                String ye=year.getText().toString().trim();
                                String semester=sem.getText().toString().trim();

                                String[] temp1=list1.get(position).split(".");
                                mDatabase= FirebaseDatabase.getInstance().getReference().child("Class").child(temp1[1]);
                                DataClass dataClass=new DataClass(id,br,sec,ye,semester);
                                mDatabase.setValue(dataClass);

                                Toast.makeText(getApplicationContext(),"Data updated successfully",Toast.LENGTH_SHORT).show();

                                dialog.dismiss();
                            }
                        });

                        btn_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                mDatabase= FirebaseDatabase.getInstance().getReference().child("Class");
                                mDatabase.child(list1.get(position)).removeValue();
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Data deleted successfully",Toast.LENGTH_SHORT);

                            }
                        });*/

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                dialog.show();
            }
        });



        mDatabase=FirebaseDatabase.getInstance().getReference().child("Class");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String class1=dataSnapshot.child("branch").getValue(String.class)+dataSnapshot.child("section").getValue(String.class)+"        "+"-"+dataSnapshot.child("id").getValue(String.class);

                if(dataSnapshot.child("year").getValue(String.class).equals("1"))
                list1.add(class1);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                list1.remove(dataSnapshot.getValue(String.class));
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

    public void showSecondYear(){
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list2);
        lv2.setAdapter(adapter);


        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder myDialog= new AlertDialog.Builder(ManageClass.this);

                LayoutInflater inflater=LayoutInflater.from(ManageClass.this);
                View update_delete_class=inflater.inflate(R.layout.update_delete_teacher,null);

                final AlertDialog dialog=myDialog.create();

                dialog.setView(update_delete_class);

                final EditText cid=update_delete_class.findViewById(R.id.cid);
                final EditText branch=update_delete_class.findViewById(R.id.branch);
                final EditText section=update_delete_class.findViewById(R.id.section);
                final EditText year=update_delete_class.findViewById(R.id.year);
                final EditText sem=update_delete_class.findViewById(R.id.sem);
                final Button btn_update=update_delete_class.findViewById(R.id.btn_update);
                final Button btn_delete=update_delete_class.findViewById(R.id.btn_delete);

                final String[] temp=list2.get(position).split("-");

                dref=FirebaseDatabase.getInstance().getReference().child("Class").child(temp[1]);


                dref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String id=dataSnapshot.child("id").getValue().toString();
                        final String b=dataSnapshot.child("branch").getValue().toString();
                        final String s=dataSnapshot.child("section").getValue().toString();
                        final String y=dataSnapshot.child("year").getValue().toString();
                        final String se=dataSnapshot.child("sem").getValue().toString();

                        Toast.makeText(getApplicationContext(),id+b+s+y+se,Toast.LENGTH_SHORT).show();

                       /*cid.setText(id);
                        branch.setText(b);
                        section.setText(s);
                        year.setText(y);
                        sem.setText(se);

                        btn_update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String id=cid.getText().toString().trim();
                                String br=branch.getText().toString().trim();
                                String sec=section.getText().toString().trim();
                                String ye=year.getText().toString().trim();
                                String semester=sem.getText().toString().trim();

                                String[] temp1=list1.get(position).split(".");
                                mDatabase= FirebaseDatabase.getInstance().getReference().child("Class").child(temp1[1]);
                                DataClass dataClass=new DataClass(id,br,sec,ye,semester);
                                mDatabase.setValue(dataClass);

                                Toast.makeText(getApplicationContext(),"Data updated successfully",Toast.LENGTH_SHORT).show();

                                dialog.dismiss();
                            }
                        });

                        btn_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                mDatabase= FirebaseDatabase.getInstance().getReference().child("Class");
                                mDatabase.child(list1.get(position)).removeValue();
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Data deleted successfully",Toast.LENGTH_SHORT);

                            }
                        });*/

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                dialog.show();
            }
        });



        mDatabase=FirebaseDatabase.getInstance().getReference().child("Class");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String class1=dataSnapshot.child("branch").getValue(String.class)+dataSnapshot.child("section").getValue(String.class)+"        "+"-"+dataSnapshot.child("id").getValue(String.class);

                if(dataSnapshot.child("year").getValue(String.class).equals("2"))
                    list2.add(class1);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                list2.remove(dataSnapshot.getValue(String.class));
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
    public void showThirdYear(){
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list3);
        lv3.setAdapter(adapter);


        lv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder myDialog= new AlertDialog.Builder(ManageClass.this);

                LayoutInflater inflater=LayoutInflater.from(ManageClass.this);
                View update_delete_class=inflater.inflate(R.layout.update_delete_teacher,null);

                final AlertDialog dialog=myDialog.create();

                dialog.setView(update_delete_class);

                final EditText cid=update_delete_class.findViewById(R.id.cid);
                final EditText branch=update_delete_class.findViewById(R.id.branch);
                final EditText section=update_delete_class.findViewById(R.id.section);
                final EditText year=update_delete_class.findViewById(R.id.year);
                final EditText sem=update_delete_class.findViewById(R.id.sem);
                final Button btn_update=update_delete_class.findViewById(R.id.btn_update);
                final Button btn_delete=update_delete_class.findViewById(R.id.btn_delete);

                final String[] temp=list3.get(position).split("-");

                dref=FirebaseDatabase.getInstance().getReference().child("Class").child(temp[1]);


                dref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String id=dataSnapshot.child("id").getValue().toString();
                        final String b=dataSnapshot.child("branch").getValue().toString();
                        final String s=dataSnapshot.child("section").getValue().toString();
                        final String y=dataSnapshot.child("year").getValue().toString();
                        final String se=dataSnapshot.child("sem").getValue().toString();

                        Toast.makeText(getApplicationContext(),id+b+s+y+se,Toast.LENGTH_SHORT).show();

                       /*cid.setText(id);
                        branch.setText(b);
                        section.setText(s);
                        year.setText(y);
                        sem.setText(se);

                        btn_update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String id=cid.getText().toString().trim();
                                String br=branch.getText().toString().trim();
                                String sec=section.getText().toString().trim();
                                String ye=year.getText().toString().trim();
                                String semester=sem.getText().toString().trim();

                                String[] temp1=list1.get(position).split(".");
                                mDatabase= FirebaseDatabase.getInstance().getReference().child("Class").child(temp1[1]);
                                DataClass dataClass=new DataClass(id,br,sec,ye,semester);
                                mDatabase.setValue(dataClass);

                                Toast.makeText(getApplicationContext(),"Data updated successfully",Toast.LENGTH_SHORT).show();

                                dialog.dismiss();
                            }
                        });

                        btn_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                mDatabase= FirebaseDatabase.getInstance().getReference().child("Class");
                                mDatabase.child(list1.get(position)).removeValue();
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Data deleted successfully",Toast.LENGTH_SHORT);

                            }
                        });*/

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                dialog.show();
            }
        });



        mDatabase=FirebaseDatabase.getInstance().getReference().child("Class");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String class1=dataSnapshot.child("branch").getValue(String.class)+dataSnapshot.child("section").getValue(String.class)+"        "+"-"+dataSnapshot.child("id").getValue(String.class);

                if(dataSnapshot.child("year").getValue(String.class).equals("3"))
                    list3.add(class1);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                list3.remove(dataSnapshot.getValue(String.class));
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
    public void showFourthYear(){
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list4);
        lv4.setAdapter(adapter);


        lv4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder myDialog= new AlertDialog.Builder(ManageClass.this);

                LayoutInflater inflater=LayoutInflater.from(ManageClass.this);
                View update_delete_class=inflater.inflate(R.layout.update_delete_teacher,null);

                final AlertDialog dialog=myDialog.create();

                dialog.setView(update_delete_class);

                final EditText cid=update_delete_class.findViewById(R.id.cid);
                final EditText branch=update_delete_class.findViewById(R.id.branch);
                final EditText section=update_delete_class.findViewById(R.id.section);
                final EditText year=update_delete_class.findViewById(R.id.year);
                final EditText sem=update_delete_class.findViewById(R.id.sem);
                final Button btn_update=update_delete_class.findViewById(R.id.btn_update);
                final Button btn_delete=update_delete_class.findViewById(R.id.btn_delete);

                final String[] temp=list1.get(position).split("-");

                dref=FirebaseDatabase.getInstance().getReference().child("Class").child(temp[1]);


                dref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String id=dataSnapshot.child("id").getValue().toString();
                        final String b=dataSnapshot.child("branch").getValue().toString();
                        final String s=dataSnapshot.child("section").getValue().toString();
                        final String y=dataSnapshot.child("year").getValue().toString();
                        final String se=dataSnapshot.child("sem").getValue().toString();

                        Toast.makeText(getApplicationContext(),id+b+s+y+se,Toast.LENGTH_SHORT).show();

                       /*cid.setText(id);
                        branch.setText(b);
                        section.setText(s);
                        year.setText(y);
                        sem.setText(se);

                        btn_update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String id=cid.getText().toString().trim();
                                String br=branch.getText().toString().trim();
                                String sec=section.getText().toString().trim();
                                String ye=year.getText().toString().trim();
                                String semester=sem.getText().toString().trim();

                                String[] temp1=list1.get(position).split(".");
                                mDatabase= FirebaseDatabase.getInstance().getReference().child("Class").child(temp1[1]);
                                DataClass dataClass=new DataClass(id,br,sec,ye,semester);
                                mDatabase.setValue(dataClass);

                                Toast.makeText(getApplicationContext(),"Data updated successfully",Toast.LENGTH_SHORT).show();

                                dialog.dismiss();
                            }
                        });

                        btn_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                mDatabase= FirebaseDatabase.getInstance().getReference().child("Class");
                                mDatabase.child(list1.get(position)).removeValue();
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Data deleted successfully",Toast.LENGTH_SHORT);

                            }
                        });*/

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                dialog.show();
            }
        });



        mDatabase=FirebaseDatabase.getInstance().getReference().child("Class");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String class1=dataSnapshot.child("branch").getValue(String.class)+dataSnapshot.child("section").getValue(String.class)+"        "+"-"+dataSnapshot.child("id").getValue(String.class);

                if(dataSnapshot.child("year").getValue(String.class).equals("4"))
                    list4.add(class1);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                list4.remove(dataSnapshot.getValue(String.class));
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
