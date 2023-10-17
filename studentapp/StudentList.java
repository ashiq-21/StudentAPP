package com.example.studentapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import java.util.ArrayList;


public class StudentList extends AppCompatActivity {

    private Button create, exit;
    private ListView lvEvents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        lvEvents=findViewById(R.id.listEvents);
        create =findViewById(R.id.c_new);
        exit = findViewById(R.id.exit);
        loadData();
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentList.this, create.class);
                startActivity(i);
            }
        });


    }
    ArrayList<Student> students;
    CustomEventAdapter adapter;
    private void loadData(){
        students = new ArrayList<>();
        KeyValueDB db = new KeyValueDB(this);
        Cursor rows = db.execute("SELECT * FROM key_value_pairs");
        if (rows.getCount() == 0) {
            return;
        }
        while (rows.moveToNext()) {
            String key = rows.getString(0);
            String eventData = rows.getString(1);
            String[] fieldValues = eventData.split("__");
            String name = fieldValues[0];
            String dateTime = fieldValues[1];
            String place = fieldValues[2];
            String description = fieldValues[3];
            String image1 = fieldValues[4];
            Student e = new Student(key, name, place, dateTime, description,image1);
            students.add(e);
        }
        db.close();
        adapter = new CustomEventAdapter(this, students);
        lvEvents.setAdapter(adapter);

        // handle the click on an event-list item
        lvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
//                String item = (String) parent.getItemAtPosition(position);
                System.out.println(position);
                Intent i = new Intent(StudentList.this, show.class);
                i.putExtra("memory", students.get(position).key);
                startActivity(i);
            }
        });

        lvEvents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String message = "Do you want to delete"+ students.get(position).name +" ?";
                System.out.println(message);
                showDialog(message, "Deleted", students.get(position).key);
                return true;
            }
        });
    }
    private void showDialog(String message, String title, String key)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        KeyValueDB db = new KeyValueDB(StudentList.this);
                        db.deleteDataByKey(key);
                        db.close();
                        adapter.notifyDataSetChanged();
                        Intent i = new Intent(StudentList.this, StudentList.class);
                        startActivity(i);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }



}