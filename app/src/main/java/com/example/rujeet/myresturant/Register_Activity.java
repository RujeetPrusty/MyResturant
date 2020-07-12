package com.example.rujeet.myresturant;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

import mehdi.sakout.fancybuttons.FancyButton;



public class Register_Activity extends AppCompatActivity {


    private TextInputLayout Name, Email;
    private FancyButton CreateAccount;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);

        Name = findViewById(R.id.register_username);
        Email = findViewById(R.id.register_email);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        CreateAccount = findViewById(R.id.create_account);

        Intent intent = getIntent();
        final String UserIdPhone = intent.getStringExtra("Phonekey");
        Toast.makeText(getApplicationContext(),UserIdPhone,Toast.LENGTH_LONG).show();


        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserName = Name.getEditText().getText().toString();
                String UserEmail = Email.getEditText().getText().toString();

                if (UserName.isEmpty()){
                    Name.setError("Plz Enter Username");
                    return;
                }
                if (UserEmail.isEmpty() ){
                    Email.setError("Email field cant be empty..");
                    return;
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(UserEmail).matches()){
                    Email.setError("Plz enter Valid email..");
                    return;
                }
                HashMap<String, String> UserData = new HashMap<String, String>();
                UserData.put("Name",UserName);
                UserData.put("Email",UserEmail);

                databaseReference.child(UserIdPhone).setValue(UserData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            Toast.makeText(getApplicationContext(),"Account Created",Toast.LENGTH_LONG).show();

                            Intent intent1 = new Intent(Register_Activity.this,Navigation_Drawer.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent1);
                        }
                        else {

                            Toast.makeText(getApplicationContext(),"Account Not Created",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }
}
