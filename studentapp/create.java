package com.example.studentapp;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class create extends AppCompatActivity {
    private EditText name,place,dob,description;
    private Button cancel,save;
    private ImageView image1;
    String key="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        name=findViewById(R.id.name);
        place=findViewById(R.id.place);
        dob=findViewById(R.id.dob);
        description=findViewById(R.id.description);
        image1=findViewById(R.id.image1);
        save=findViewById(R.id.save);
        cancel=findViewById(R.id.cancel);
        Intent i=getIntent();
        if(i.hasExtra("keyforupdate")){
            key = i.getStringExtra("keyforupdate");
            KeyValueDB db = new KeyValueDB(create.this);
            String value = db.getValueByKey(key);
            db.close();
            String[] fieldValues = value.split("__");
            String name1=fieldValues[0];
            String date1=fieldValues[1];
            String place1=fieldValues[2];
            String description1=fieldValues[3];
            String image11=fieldValues[4];
            name.setText(name1);
            place.setText(place1);
            dob.setText(date1);
            description.setText(description1);
            try {
                Handler handler = new Handler();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            byte[] encodeByte1 = Base64.decode(image11, Base64.DEFAULT);
                            Bitmap bitmap1 = BitmapFactory.decodeByteArray(encodeByte1, 0, encodeByte1.length);

                            @Override
                            public void run() {
                                image1.setImageBitmap(bitmap1);
                            }
                        });
                    }
                }).start();
            }
            catch (Exception e){
                Toast.makeText(create.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n=1;
                if(checkAndRequestPermission()){
                    takePictureFromGallery(n);
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            //            String ex=ex_key;
            @Override
            public void onClick(View view) {
                if (key.equals("")) {
                    String Name = name.getText().toString().trim();
                    String Dob = dob.getText().toString().trim();
                    String Place = place.getText().toString().trim();
                    String Description = description.getText().toString().trim();
                    ImageView image1 = findViewById(R.id.image1);
                    int flag = 0;
                    String errmsg = "";
                    if (Name.equals("")) {
                        errmsg+="Empty Name\n";
                        flag = 1;
                    }
                    if (Name.length() < 5) {
                        flag = 1;
                        errmsg+="Name must have 5 character\n";
                    }
                    if (Dob.equals("")) {
                        errmsg+="Empty Date\n";
                        flag = 1;
                    } else {
                        String[] dateof = Dob.split("/");
                        if (dateof.length > 3) {
                            errmsg+="Date is Not correct\n";
                            flag = 1;
                        } else {
                            if (Integer.parseInt(dateof[0]) > 31 || Integer.parseInt(dateof[1]) > 12 || Integer.parseInt(dateof[2]) > 2023) {
                                errmsg+="Date is Not correct\n";
                                flag = 1;
                            }
                        }
                    }
                    if (Place.equals("")) {
                        flag = 1;
                        errmsg+="Empty Place\n";
                    }
                    if (image1.getDrawable() == null) {
                        errmsg+="Image field is empty\n";
                        flag = 1;
                    }
                    if (Description.equals("")) {
                        flag = 1;
                        errmsg+="Add Description\n";
                    }
                    if (flag == 0) {
                        BitmapDrawable drawable1 = (BitmapDrawable) image1.getDrawable();
                        Bitmap bitmap1 = drawable1.getBitmap();
                        ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
                        bitmap1.compress(Bitmap.CompressFormat.JPEG, 25, outputStream1);
                        byte[] imageBytes1 = outputStream1.toByteArray();
                        String selectedImgBase64Str1 = Base64.encodeToString(imageBytes1, Base64.DEFAULT);
                        try {
                            String key = Name + System.currentTimeMillis();
                            String value = Name + "__" + Dob + "__" + Place + "__" + Description + "__" + selectedImgBase64Str1;
                            Toast.makeText(create.this, value, Toast.LENGTH_SHORT).show();
                            KeyValueDB db = new KeyValueDB(create.this);
                            db.insertKeyValue(key, value);
                            db.close();
                            Intent i = new Intent(create.this, StudentList.class);
                            startActivity(i);
                        } catch (Exception e) {
                            Toast.makeText(create.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(create.this, errmsg, Toast.LENGTH_SHORT).show();
                        Toast.makeText(create.this, "fillup all field", Toast.LENGTH_SHORT).show();
                    }
                } 
                else 
                {
                    String Name = name.getText().toString().trim();
                    String Dob = dob.getText().toString().trim();
                    String Place = place.getText().toString().trim();
                    String Description = description.getText().toString().trim();
                    ImageView image1 = findViewById(R.id.image1);
                    int flag = 0;
                    if (flag == 0) {
                        BitmapDrawable drawable1 = (BitmapDrawable) image1.getDrawable();
                        Bitmap bitmap1 = drawable1.getBitmap();
                        ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
                        bitmap1.compress(Bitmap.CompressFormat.JPEG, 25, outputStream1);
                        byte[] imageBytes1 = outputStream1.toByteArray();
                        String selectedImgBase64Str1 = Base64.encodeToString(imageBytes1, Base64.DEFAULT);
                        try {
                            String value = Name + "__" + Dob + "__" + Place + "__" + Description + "__" + selectedImgBase64Str1;
                            Toast.makeText(create.this, value, Toast.LENGTH_SHORT).show();
                            KeyValueDB db = new KeyValueDB(create.this);
                            db.updateValueByKey(key, value);
                            db.close();
                            Intent i = new Intent(create.this, StudentList.class);
                            startActivity(i);
                        } catch (Exception e) 
                        {
                            Toast.makeText(create.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private boolean checkAndRequestPermission(){
        if(Build.VERSION.SDK_INT >= 23){
            int cameraPermission = ActivityCompat.checkSelfPermission(create.this, android.Manifest.permission.CAMERA);
            if(cameraPermission == PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(create.this, new String[]{Manifest.permission.CAMERA}, 20);
                return false;
            }
        }
        return true;
    }
    private void takePictureFromGallery(int n){
        Intent pickPhoto1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(n==1){
            startActivityForResult(pickPhoto1, 1);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode == RESULT_OK){
                Uri selectedImageUri = data.getData();
                image1.setImageURI(selectedImageUri);
            }
        }
    }
}