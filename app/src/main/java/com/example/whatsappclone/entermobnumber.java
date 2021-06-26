package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class entermobnumber extends AppCompatActivity {

    EditText phonenumber;
    Button getotp;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseUser userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entermobnumber);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        phonenumber = findViewById(R.id.Input_mobile_number);
        getotp = findViewById(R.id.buttongetotp);
        progressBar = findViewById(R.id.otp_sending_progressbar);
        fAuth=FirebaseAuth.getInstance();

        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phnumber = phonenumber.getText().toString().trim();
                if (TextUtils.isEmpty(phnumber)) {
                    phonenumber.setError("Please Enter Complete Phone Number!!!!");
                    return;
                }
                if (phnumber.length() < 10) {
                    phonenumber.setError("Phone Number Cannot be less then 10 digits!!!!");
                    return;
                }
                if (phnumber.length() > 10) {
                    phonenumber.setError("Phone Number Cannot be more then 10 digits!!!!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                getotp.setVisibility(View.INVISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + phnumber,
                        60,
                        TimeUnit.SECONDS,
                        entermobnumber.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                                getotp.setVisibility(View.VISIBLE);
                            }
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                getotp.setVisibility(View.VISIBLE);
                                Toast.makeText(entermobnumber.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progressBar.setVisibility(View.GONE);
                                getotp.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(getApplicationContext(), Verificationonotp.class);
                                intent.putExtra("mobile", phnumber);
                                intent.putExtra("backendotp",s);
                                startActivity(intent);
                            }
                        }
                );
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        userid=fAuth.getCurrentUser();
        if(userid!=null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}