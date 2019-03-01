package com.offerup.newtest;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import android.arch.core.util.Function;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.hardware.usb.UsbRequest;
import android.media.Image;
import android.net.Uri;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Message;
import android.os.TestLooperManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.lang.ref.Reference;
import java.security.PublicKey;
import java.sql.Ref;
import java.util.Objects;
import java.util.jar.Attributes;

public class MemberActivity extends AppCompatActivity {

    ToggleButton Membership;
    String NUID;
    CheckBox FightY, FightN;
   static EditText Height, Weight;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase mFireDatabase;
    DatabaseReference myRef, myRef2;
    TextView User, FightOr;;
    ImageView websitelogo;



    String Url = "https://www.stayreadysport.com";




    //DatabaseReference dbName = FirebaseDatabase.getInstance().getReference("Profile").child("Name");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        firebaseAuth = FirebaseAuth.getInstance();
        Membership = findViewById(R.id.toggleButton);
        NUID = firebaseAuth.getCurrentUser().getUid();

        mFireDatabase = FirebaseDatabase.getInstance();
        myRef = mFireDatabase.getReference(NUID);


        FightY = findViewById(R.id.checkBoxFightY);
        FightN = findViewById(R.id.checkBoxFightN);
        FightOr = findViewById(R.id.textViewFight);

        websitelogo = (ImageView) findViewById(R.id.imageView7);

        Weight = findViewById(R.id.editTextWeight);
        Height = findViewById(R.id.editTextHeight);

        FightN.setVisibility(findViewById(R.id.checkBoxFightN).INVISIBLE);
        FightY.setVisibility(findViewById(R.id.checkBoxFightY).INVISIBLE);
        FightOr.setVisibility(findViewById(R.id.textViewFight).INVISIBLE);


        FightY.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (FightY.isChecked()) {
                    final AlertDialog.Builder Sure = new AlertDialog.Builder(MemberActivity.this);
                    Sure.setMessage("Are you sure you want to fight?");
                    Sure.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    Sure.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            myRef.child("Fighter").setValue("Yes");

                            setVisibleGone();

                        }


                    });
                    Sure.show();
                    FightN.setChecked(false);
                }
                if (FightN.isChecked()){
                    FightY.setChecked(false);

                }
            }
        });
        FightN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (FightN.isChecked()) {
                    final AlertDialog.Builder Nope = new AlertDialog.Builder(MemberActivity.this);
                    Nope.setMessage("Are you sure you do NOT want to fight?");
                    Nope.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });

                    Nope.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            myRef.child("Fighter").setValue("No");
                            setVisibleGone();

                        }


                    });
                    Nope.show();

                    FightY.setChecked(false);

                }
                if (FightY.isChecked()){
                    FightN.setChecked(false);
                }
            }

        });


        Height.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (Height.length() == 3 && Weight.length() == 3) {

                    myRef.child("Height").setValue(Height.getText().toString());
                    Height.setVisibility(findViewById(R.id.editTextHeight).INVISIBLE);

                    myRef.child("Weight").setValue(Weight.getText().toString());
                    Weight.setVisibility(findViewById(R.id.editTextWeight).INVISIBLE);
                    FightN.setVisibility(findViewById(R.id.checkBoxFightN).VISIBLE);
                    FightY.setVisibility(findViewById(R.id.checkBoxFightY).VISIBLE);
                    FightOr.setVisibility(findViewById(R.id.textViewFight).VISIBLE);


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String Name = dataSnapshot.child("Name").getValue().toString();
                User = findViewById(R.id.textView2);
                User.setText(Name);
                getStats();
                if (dataSnapshot.child("Height").exists()){
                    String Height = dataSnapshot.child("Height").getValue().toString();
                    MemberActivity.Height.setText(Height);
                }
                if (dataSnapshot.child("Weight").exists()){
                    String Weight = dataSnapshot.child("Weight").getValue().toString();
                   MemberActivity.Weight.setText(Weight);
                }
                if (Height.length() <= 2 && Weight.length() <= 2) {
                    AlertDialog.Builder Msgbox = new AlertDialog.Builder(MemberActivity.this);
                    final AlertDialog.Builder Msgbox1 = new AlertDialog.Builder(MemberActivity.this);
                    Msgbox.setTitle("Missing Info");
                    Msgbox.setIcon(R.drawable.icon);
                    Msgbox.setMessage("Please Enter Height and Weight,");
                    Msgbox1.setMessage("Example, 5'7, 185lbs");
                    Msgbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Msgbox1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            Msgbox1.show();
                        }
                    });


                    Msgbox.show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


            public void getStats(){
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("Height").exists()){
                        String Height = dataSnapshot.child("Height").getValue().toString();
                        MemberActivity.Height.setText(Height);
                    }
                    if (dataSnapshot.child("Weight").exists()){
                        String Weight = dataSnapshot.child("Weight").getValue().toString();
                        MemberActivity.Weight.setText(Weight);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        websitelogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Opensite();
            }

        });

    }







    public void Opensite(){

        String web = Url;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(web));
        startActivity(i);
    }


});

    }
    public void setVisibleGone(){
        FightN.setVisibility(findViewById(R.id.checkBoxFightN).INVISIBLE);
        FightY.setVisibility(findViewById(R.id.checkBoxFightY).INVISIBLE);
        FightOr.setVisibility(findViewById(R.id.textViewFight).INVISIBLE);
    }
}

