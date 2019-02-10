package com.offerup.newtest;

import android.arch.core.util.Function;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

public class NewAcctActivity extends AppCompatActivity {
    String NUID;
    EditText Name, Pass, email;

    FirebaseAuth firebaseAuth;

    private FirebaseDatabase mFireDatabase;
    private DatabaseReference myRef, myRef2;



    private DatabaseReference Database, Database2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_acct);
        firebaseAuth = FirebaseAuth.getInstance();
        Button Submit = (Button) findViewById(R.id.btnSubmit);
         Name = (EditText)findViewById(R.id.editTextName);
         Pass = (EditText)findViewById(R.id.editTextPass);
         email = (EditText)findViewById(R.id.editTextEmail);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),
                        Pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            NUID = firebaseAuth.getUid();
                            mFireDatabase = FirebaseDatabase.getInstance();
                            myRef = mFireDatabase.getReference().getRoot();
                            myRef2 = myRef.child(NUID);
                            myRef =  FirebaseDatabase.getInstance().getReference();

                            myRef2.child("Email").setValue(email.getText().toString());
                            myRef2.child("Name").setValue(Name.getText().toString());
                            myRef2.child("Password").setValue(Pass.getText().toString());


                            Toast.makeText(NewAcctActivity.this, "Welcome",
                                    Toast.LENGTH_LONG).show();




                            openMemberActivity();
                        }else if (Pass.length() < 6){
                            Pass.setError("password is not valid, 6 Character Min for password");
                        } else if(!email.getText().toString().contains("@") || !email.getText().toString().contains(".com")){
                            email.setError("Email is not valid");
                        }else if(!task.isSuccessful()){
                            Toast.makeText(getBaseContext(), email.getText().toString()+" already exists, try a different name" , Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }

        });

    } public void openMemberActivity(){
        Intent intent = new Intent(this, MemberActivity.class);
        intent.putExtra("userId", NUID);
        startActivity(intent);
    }










}
