package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Profile extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    Uri imgUri;
    ImageView viewimage,updateimage;
    TextView name,status;
    StorageReference storageReference,storageReference1;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String UserID;
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        UserID = fAuth.getCurrentUser().getUid();
        getSupportActionBar().setTitle(Html.fromHtml("Profile"));
        storageReference1 = FirebaseStorage.getInstance().getReference(UserID);


        viewimage = findViewById(R.id.viewimage);
        updateimage = findViewById(R.id.updateImage);
        name = findViewById(R.id.EditName);
        status = findViewById(R.id.EditStatus);

        storageReference = FirebaseStorage.getInstance().getReference().child(UserID + "/Picture.jpg");
        try {
            File file = File.createTempFile("Picture", "jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    viewimage.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        documentReference = fStore.collection("users").document(UserID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                name.setText(value.getString("Name"));
                status.setText(value.getString("Status"));
            }
        });
        updateimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filechooser();
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder namedailog= new AlertDialog.Builder(Profile.this);
                namedailog.setTitle("Enter Your Name");
                EditText nameinput=new EditText(Profile.this);
                nameinput.setInputType(InputType.TYPE_CLASS_TEXT);
                namedailog.setView(nameinput);
                namedailog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name=nameinput.getText().toString().trim();
                        DocumentReference documentReference1=fStore.collection("users").document(UserID);
                        documentReference1.update("Name",name);
                    }
                });
                namedailog.show();
            }
        });
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder statusdailog= new AlertDialog.Builder(Profile.this);
                statusdailog.setTitle("Enter Your Status");
                EditText statusinput=new EditText(Profile.this);
                statusinput.setInputType(InputType.TYPE_CLASS_TEXT);
                statusdailog.setView(statusinput);
                statusdailog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String staus=statusinput.getText().toString().trim();
                        DocumentReference documentReference2=fStore.collection("users").document(UserID);
                        documentReference2.update("Status",staus);
                    }
                });
                statusdailog.show();
            }
        });

    }

    private void filechooser() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();
            Picasso.with(this).load(imgUri).into(viewimage);
            upload();
        }
    }
    private String getFileExt(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

    }

    private void upload() {

        if (imgUri != null) {
            StorageReference fileReference = storageReference1.child("Picture." + getFileExt(imgUri));
            fileReference.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(Profile.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Profile.this, "Upload Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show();
        }
    }

}