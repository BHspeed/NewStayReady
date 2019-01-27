package com.offerup.newtest;

import android.arch.core.util.Function;
import android.content.Intent;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.lang.ref.Reference;
import java.security.PublicKey;
import java.sql.Ref;

public class MemberActivity extends AppCompatActivity {

    DatabaseReference DB = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        if(getActionBar()!=null){
            getActionBar().setTitle("New Member User");
        }
    }


}

