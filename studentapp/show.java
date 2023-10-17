package com.example.studentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class show extends AppCompatActivity {
    private TextView name, place, dob,Description;
    Button back,edit;
    private ImageView Image1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        name =findViewById(R.id.name);
        place =findViewById(R.id.place);
        dob =findViewById(R.id.dob);
        Description=findViewById(R.id.description);
        Image1=findViewById(R.id.image1);
        back=findViewById(R.id.back);
        edit=findViewById(R.id.edit);
        Intent i = getIntent();
        if(i.hasExtra("memory")){
            String key = i.getStringExtra("memory");
            KeyValueDB db = new KeyValueDB(show.this);
            String value = db.getValueByKey(key);
            db.close();
            String[] fieldValues = value.split("__");
            String name=fieldValues[0];
            String date=fieldValues[1];
            String place=fieldValues[2];
            String description=fieldValues[3];
            String image1=fieldValues[4];
            this.name.setText(name);
            dob.setText(date);
            this.place.setText(place);
            Description.setText(description);
            try {
                Handler handler = new Handler();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            byte[] encodeByte1 = Base64.decode(image1, Base64.DEFAULT);
                            Bitmap bitmap1 = BitmapFactory.decodeByteArray(encodeByte1, 0, encodeByte1.length);
                            @Override
                            public void run() {
                                Image1.setImageBitmap(bitmap1);
                            }
                        });
                    }
                }).start();
            }
            catch (Exception e){
                Toast.makeText(show.this, e.toString(), Toast.LENGTH_SHORT).show();
            }

        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = i.getStringExtra("memory");
                Intent m=new Intent(show.this, create.class);
                m.putExtra("keyforupdate",key);
                startActivity(m);
            }
        });
    }
}