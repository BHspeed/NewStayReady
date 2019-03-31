package com.offerup.newtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button NewAct = (Button) findViewById(R.id.btnNewAct);
        Button SignIn = (Button) findViewById(R.id.btnSignIn);


        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignInActivity();
            }
        });

        NewAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewAcctActivity();
            }
        });
    }
    public void openNewAcctActivity(){
        Intent intent = new Intent(this, NewAcctActivity.class);
        startActivity(intent);
        finish();

    }
    public void openSignInActivity(){
        Intent intent1 = new Intent(this, SignInActivity.class );
        startActivity(intent1);
        finish();
    }
}
