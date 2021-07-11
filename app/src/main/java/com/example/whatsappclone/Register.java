package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    ImageView Add_Img;
    EditText Add_Name,Add_status;
    Button Done;
    Uri ImageUri;
    String UserId;
    StorageReference storageReference;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Add_Img = findViewById(R.id.Add_Img);
        Add_Name = findViewById(R.id.Enter_Name);
        Done = findViewById(R.id.DONE);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        Add_status=findViewById(R.id.StatusId);

        UserId = fAuth.getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference(UserId);


        Add_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

    }

    private String getFileExt(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

    }

    private void uploadFile() {

        if (ImageUri != null) {
            StorageReference fileReference = storageReference.child( "Picture." + getFileExt(ImageUri));
            fileReference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(Register.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Register.this, "Upload Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            documentReference=fStore.collection("users").document(UserId);
            Map<String,String> user=new HashMap<>();
            user.put("Name",Add_Name.getText().toString().trim());
            user.put("Status",Add_status.getText().toString().trim());
            documentReference.set(user);
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        else {
            Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            ImageUri = data.getData();
            Picasso.with(this).load(ImageUri).into(Add_Img);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}