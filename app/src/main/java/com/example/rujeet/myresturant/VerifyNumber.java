package com.example.rujeet.myresturant;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;



import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import mehdi.sakout.fancybuttons.FancyButton;

public class VerifyNumber extends AppCompatActivity {

    private DatabaseReference  databaseReference;
    private FirebaseAuth firebaseAuth;
    private TextInputLayout textInputotp;
    private TextInputEditText OtpeditText;
    private ProgressBar progressBar;
    private FancyButton verifyotp;
    private String verificationId;
    public String PhoneKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_number);

        firebaseAuth = FirebaseAuth.getInstance();

        textInputotp = findViewById(R.id.Enter_Otp);
        OtpeditText = findViewById(R.id.otp_editText);
        verifyotp = findViewById(R.id.Verify_Otp);
        progressBar = findViewById(R.id.progressbar_otp);
        progressBar.setVisibility(View.INVISIBLE);

        String PhoneNumber = getIntent().getStringExtra("PhoneNumber");
        sendVerificationCode(PhoneNumber);

        verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Code = textInputotp.getEditText().getText().toString();
                if (Code.isEmpty() || Code.length()<6){
                    textInputotp.setError("plz enter Otp");
                    textInputotp.requestFocus();
                    return;
                }
                verifyVerificationCode(Code);
            }
        });

    }

    private void sendVerificationCode(String phoneNumber) {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                Callbacks
        );
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            Callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String Code = phoneAuthCredential.getSmsCode();
            if (Code != null){
                OtpeditText.setText(Code);
                verifyVerificationCode(Code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }
    };

    private void verifyVerificationCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

              if (task.isSuccessful()){
                  FirebaseUser user = task.getResult().getUser();
                  PhoneKey = user.getPhoneNumber();
                  Log.e("Firebaseuser:",PhoneKey);

                  databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

                  if (databaseReference !=null){
                      Log.e("DatabaseReference :","is not null");
                  }
                  else {
                      Log.e("DatabaseReference :","is null");
                  }


                  databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                          Log.e("datasnapshot", dataSnapshot.getChildren().toString());

                          if (dataSnapshot.hasChild(firebaseAuth.getCurrentUser().getPhoneNumber()) == true) {
                                  Intent intent1 = new Intent(VerifyNumber.this, Navigation_Drawer.class);
                                  intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                  startActivity(intent1);
                          } else {
                                  Intent intent2 = new Intent(VerifyNumber.this, Register_Activity.class);
                                  intent2.putExtra("Phonekey", PhoneKey);
                                  intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                  startActivity(intent2);
                              }
                          }


                      @Override
                      public void onCancelled(@NonNull DatabaseError databaseError) {

                      }
                  });

              }

              else {
                  Toast.makeText(VerifyNumber.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
              }
            }
        });
    }
}
