package com.example.forher;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class add extends AppCompatActivity {
    private EditText name,place,phone;
    private String Dname, Dplace, Dphone,saveCurrentDate,saveCurrentTime;
    private Button AddNew;
    private String EquipRandomKey;
    private static final int PICK_FILE=1;
    private ImageView plImage;
    Uri ImageUri;
    StorageReference Folder;
    String ImagePath;

    private DatabaseReference Ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Ref= FirebaseDatabase.getInstance().getReference().child("Problems");
        plImage=findViewById(R.id.Image);

        AddNew=(Button) findViewById(R.id.AddNew);
        name=(EditText)findViewById(R.id.name);
        place=(EditText)findViewById(R.id.place);
        phone=(EditText) findViewById(R.id.phone);
        plImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePictures();
            }
        });
        AddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateEquipData();
            }
        });

    }
    private void choosePictures() {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,PICK_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!=null )
        {
            ImageUri= data.getData();
            Folder= FirebaseStorage.getInstance().getReference().child("Place Photos");
            plImage.setImageURI(ImageUri);

            ValidateEquipData();
        }
    }

    private void ValidateEquipData() {
        Dname = name.getText().toString();
        Dplace = place.getText().toString();
        Dphone = phone.getText().toString();

        if (TextUtils.isEmpty(Dname))      {
            Toast.makeText(this, "Please write your name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Dplace))
        {
            Toast.makeText(this, "Please write Place or address details", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Dphone))
        {
            Toast.makeText(this, "Please Enter Phone number", Toast.LENGTH_SHORT).show();
        }

        else
        {
            StoreEquipInfo();
        }
    }

    private void StoreEquipInfo() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        EquipRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference file_name=Folder.child("image"+ImageUri.getLastPathSegment()+ EquipRandomKey +".jpg");
        file_name.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                file_name.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ImagePath=String.valueOf(uri);
                        SaveEquipInfoTodatabase();
                    }
                });

            }
        });

    }

    private void SaveEquipInfoTodatabase() {
        HashMap<String, Object> Probmap=new HashMap<>();
        Probmap.put("Eid",EquipRandomKey);
        Probmap.put("name",Dname);
        Probmap.put("image",ImagePath);
        Probmap.put("date",saveCurrentDate+" "+saveCurrentTime);
        Probmap.put("place",Dplace);
        Probmap.put("phone",Dphone);

        Ref.child(EquipRandomKey).updateChildren(Probmap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(com.example.forher.add.this, "Details is added Successfully ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else
                        {
                            String message = task.getException().toString();
                            Toast.makeText(com.example.forher.add.this, "Error"+message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}
