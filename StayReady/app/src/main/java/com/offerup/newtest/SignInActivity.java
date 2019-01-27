package com.offerup.newtest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignInActivity extends AppCompatActivity {

    EditText email,Password;

   Button signIn;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        firebaseAuth = FirebaseAuth.getInstance();

        email =(EditText)findViewById(R.id.editTextSignIn);
        Password = (EditText)findViewById(R.id.editTextPassword);
        signIn = (Button) findViewById(R.id.btnSignIn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),
                        Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(SignInActivity.this, "Logged In",
                                        Toast.LENGTH_LONG).show();

                                openMemberActivity();


                            }else {

                                    Toast.makeText(SignInActivity.this, "Username/Password Incorrect",
                                            Toast.LENGTH_LONG).show();

                            }


                    }

                });


            }




        });




    }


    private void updateUI(FirebaseUser user) {

        if (user.isEmailVerified()){
            Toast.makeText(SignInActivity.this, "User already Logged In", Toast.LENGTH_LONG);
        }
    }

    public void openMemberActivity(){
        Intent intent = new Intent(this, MemberActivity.class);

        startActivity(intent);


    }

}