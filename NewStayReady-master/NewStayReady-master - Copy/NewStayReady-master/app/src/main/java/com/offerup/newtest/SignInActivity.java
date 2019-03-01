package com.offerup.newtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignInActivity extends AppCompatActivity {

    private EditText email, password;
    private Button signIn;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initializeView();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!email.getText().toString().equals("")  && !password.getText().toString().equals("")){
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignInActivity.this, "Logged In", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                openMemberActivity();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(SignInActivity.this, "Username/password Incorrect", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else if(email.getText().toString().equals("")){
                    email.setError("Please make sure to enter in the username before logging in");
                } else if(password.getText().toString().equals("")){
                    password.setError("Please make sure to enter in your password before logging in");
                }
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

    private void initializeView(){
        firebaseAuth = FirebaseAuth.getInstance();
        email =(EditText)findViewById(R.id.editTextSignIn);
        password = (EditText)findViewById(R.id.editTextPassword);
        signIn = (Button) findViewById(R.id.btnSignIn);
        progressBar = findViewById(R.id.progressBar);
    }

}