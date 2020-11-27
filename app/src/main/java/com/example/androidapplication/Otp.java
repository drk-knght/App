package com.example.androidapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class Otp extends AppCompatActivity {

    FirebaseAuth fAuth;
    EditText phoneNumber,codeEnter;
    Button nextBtn;
    ProgressBar progressBar;
    TextView state;
    CountryCodePicker codePicker;
    String verificationId;
    PhoneAuthProvider.ForceResendingToken token;
    Boolean verificationInProgress=false;
    FirebaseAuth mAuth;
    FirebaseUser user=mAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        fAuth=FirebaseAuth.getInstance();
        phoneNumber=findViewById(R.id.phone);
        codeEnter=findViewById(R.id.codeEnter);
        progressBar=findViewById(R.id.progressBar);
        nextBtn=findViewById(R.id.nextBtn);
        state=findViewById(R.id.state);
        codePicker=findViewById(R.id.ccp);


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!verificationInProgress){
                    if(!phoneNumber.getText().toString().isEmpty() && phoneNumber.getText().toString().length()==10){
                        String phoneNum="+"+codePicker.getSelectedCountryCode()+phoneNumber.getText().toString();
                        progressBar.setVisibility(View.VISIBLE);
                        state.setText("Sending OTP");
                        state.setVisibility(View.VISIBLE);
                        requestOtp(phoneNum);
                    }
                    else
                    {
                        phoneNumber.setError("Phone Number is not valid");
                    }
                }
                else
                {
                      String userOTP=codeEnter.getText().toString();
                      if(!userOTP.isEmpty() && userOTP.length()==6){
                          PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId,userOTP);
                          verifyAuth(credential);
                          String Val="+"+codePicker.getSelectedCountryCode()+phoneNumber.getText().toString();
                          MobileHelperClass mo=new MobileHelperClass(Val);
                          DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                          rootRef.child("PhoneNo").child(user.getDisplayName()).setValue(mo);
                          Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                          startActivity(intent);
                          finish();
                      }
                      else
                      {
                          codeEnter.setError("Valid OTP is required.");
                      }
                }
            }
        });
    }

    private void verifyAuth(PhoneAuthCredential credential){
        fAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Otp.this, "Authentication is Successful", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(Otp.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void requestOtp(String phoneNum){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNum, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                progressBar.setVisibility(View.GONE);
                state.setVisibility(View.GONE);
                codeEnter.setVisibility(View.VISIBLE);
                verificationId=s;
                token=forceResendingToken;
                nextBtn.setText("Verify");
                verificationInProgress=true;
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(Otp.this, "Cannot create the Account"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}