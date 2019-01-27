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

    EditText Name,Pass,email;

    FirebaseAuth firebaseAuth;



   private DatabaseReference Database;




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

                            Database = FirebaseDatabase.getInstance().getReference().push().child(Name.getText().toString());
                            String UserID = firebaseAuth.getCurrentUser().getUid();
                            Database.child("Profile").child("Email").setValue(email.getText().toString());
                            Database.child("Profile").child("Name").setValue(Name.getText().toString());
                            Database.child("Profile").child("Password").setValue(Pass.getText().toString());

                            //Database.child("Password").setValue(Pass.getText().toString());
                            //Database.child("MemberName").setValue(Pass.getText().toString());



                            //Database.push().getParent().child("Password").setValue(Pass.getText().toString());
                           // Database.child("Email").setValue(email.getText().toString());
                          //  Database.child("User").push().setValue(email.getText().toString());
                            //Database.child("Name").push().setValue(Name.getText().toString());
                            //Database.child("Password").push().setValue(Pass.getText().toString());


                            Toast.makeText(NewAcctActivity.this, "Welcome " + Name.getText().toString(),
                                    Toast.LENGTH_LONG).show();





                        }else {

                            Toast.makeText(NewAcctActivity.this, "Not Valid Email Or Password, (6 Character Min for Password)",
                                    Toast.LENGTH_LONG).show();

                        }





                    }
                });

                openMemberActivity();
            }

        });



    } public void openMemberActivity(){
        Intent intent = new Intent(this, MemberActivity.class);

        startActivity(intent);


    }










}
