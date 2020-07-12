package com.example.rujeet.myresturant;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import mehdi.sakout.fancybuttons.FancyButton;

public class Otp_Activity extends AppCompatActivity {
    private TextInputLayout textInputNumber;
    private FancyButton SendOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        textInputNumber = findViewById(R.id.PhoneNumber);
        SendOtp = findViewById(R.id.Send_otp);

        SendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Mobile = textInputNumber.getEditText().getText().toString();

                if (Mobile.isEmpty()  || Mobile.length()<10){
                    textInputNumber.setError("Plz enter valid phonenumber");
                    textInputNumber.requestFocus();
                    return;
                }

                String PhoneNumber= "+91" + Mobile;
                Intent intent1 = new Intent(Otp_Activity.this,VerifyNumber.class);
                intent1.putExtra("PhoneNumber",PhoneNumber);
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null){

            Intent intent1 = new Intent(Otp_Activity.this,Navigation_Drawer.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent1);
        }
    }
}
