package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Verificationonotp extends AppCompatActivity {
    EditText input1, input2, input3, input4, input5, input6;
    TextView shownumber;
    Button verify;
    String getOTPbackend;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificationonotp);

        input1 = findViewById(R.id.inputotp1);
        input2 = findViewById(R.id.inputotp2);
        input3 = findViewById(R.id.inputotp3);
        input4 = findViewById(R.id.inputotp4);
        input5 = findViewById(R.id.inputotp5);
        input6 = findViewById(R.id.inputotp6);
        shownumber = findViewById(R.id.mobiledisplay);
        verify = findViewById(R.id.verifyotp);
        progressBar = findViewById(R.id.progressbar_verifing_otp);


        shownumber.setText(String.format("+91 %s", getIntent().getStringExtra("mobile")));
        getOTPbackend = getIntent().getStringExtra("backendotp");


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp1 = input1.getText().toString().trim();
                String otp2 = input2.getText().toString().trim();
                String otp3 = input3.getText().toString().trim();
                String otp4 = input4.getText().toString().trim();
                String otp5 = input5.getText().toString().trim();
                String otp6 = input6.getText().toString().trim();


                if (!TextUtils.isEmpty(otp1) || !TextUtils.isEmpty(otp2) || !TextUtils.isEmpty(otp3)
                        || !TextUtils.isEmpty(otp4) || !TextUtils.isEmpty(otp5) || !TextUtils.isEmpty(otp6)) {
                    String otp = otp1 + otp2 + otp3 + otp4 + otp5 + otp6;


                    if (getOTPbackend != null) {
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getOTPbackend, otp);
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                verify.setVisibility(View.VISIBLE);
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                } else {
                                    Toast.makeText(Verificationonotp.this, "Enter Correct OTP", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(Verificationonotp.this, "Check Internet", Toast.LENGTH_SHORT).show();
                    }
                    // Toast.makeText(Verificationonotp.this, "Enter Complete OTP", Toast.LENGTH_SHORT).show();
                }

            }
        });

        deleteOTP();
        numberotpmove();
        findViewById(R.id.resendotp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        Verificationonotp.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(Verificationonotp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                getOTPbackend=newbackendotp;
                                Toast.makeText(Verificationonotp.this, "OTP resent successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });

    }
    private void deleteOTP()
    {
        input6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    input5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    input4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        input4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    input3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    input2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    input1.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void numberotpmove() {
        input1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    input2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    input3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    input4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    input5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    input6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}