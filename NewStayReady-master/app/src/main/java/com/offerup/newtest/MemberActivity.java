package com.offerup.newtest;


import com.ceylonlabs.imageviewpopup.ImagePopup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SectionIndexer;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class MemberActivity extends AppCompatActivity {


    private FirebaseStorage ImageData2;
    private StorageReference ImageData;
    private Button BoxingLessons, FitLessons, AddXP, PicButton;

    private ToggleButton Membership;
    private String NUID;
    private CheckBox FightY, FightN;
    private static EditText Height, Weight;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mFireDatabase;
    private DatabaseReference myRef, myRef2;
    private TextView User, FightOr, EXP;
    private ImageView websitelogo, ProfilePic;
    private ProgressBar xpProgressBar;
    private TextView xpTextView;
    private int currentProgress = 1;
    private ImageView imageViewLogo;
    private FirebaseInstanceIdService Token;
    private FirebaseInstanceId t1;

    String Url = "https://www.stayreadysport.com";
    String Url2 = "http://cisweb.bristolcc.edu/~kjacobs12/CIS272/MemberJoin.html";
    Uri FilePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        PicButton = (Button) findViewById(R.id.btnPic);
        ProfilePic = (ImageView) findViewById(R.id.imageView4);
        AddXP = (Button) findViewById(R.id.btnAddXP);
        FitLessons = (Button) findViewById(R.id.btnWorkouts);
        BoxingLessons = (Button) findViewById(R.id.btnBoxingLessons);
        firebaseAuth = FirebaseAuth.getInstance();
        Membership = findViewById(R.id.toggleButton);
        NUID = firebaseAuth.getCurrentUser().getUid();
        EXP = (TextView) findViewById(R.id.textView5);
        mFireDatabase = FirebaseDatabase.getInstance();
        myRef = mFireDatabase.getReference(NUID);


        FightY = findViewById(R.id.checkBoxFightY);
        FightN = findViewById(R.id.checkBoxFightN);
        FightOr = findViewById(R.id.textViewFight);


        websitelogo = (ImageView) findViewById(R.id.imageView7);

        Weight = findViewById(R.id.editTextWeight);
        Weight.setError("Make sure to enter your weight, i.e: 187lbs");

        Height = findViewById(R.id.editTextHeight);
        Height.setError("Make sure to enter your height, i.e: 5'7");

        xpProgressBar = findViewById(R.id.xPProgressBar);
        xpProgressBar.setMax(100);
        xpProgressBar.setProgress(currentProgress);

        xpTextView = findViewById(R.id.xPTextView);
        xpTextView.setText(currentProgress + "/100");


        AddXP.setVisibility(findViewById(R.id.xPProgressBar).INVISIBLE);
        xpProgressBar.setVisibility(findViewById(R.id.xPProgressBar).INVISIBLE);
        xpTextView.setVisibility(findViewById(R.id.xPProgressBar).INVISIBLE);
        BoxingLessons.setVisibility(findViewById(R.id.btnBoxingLessons).INVISIBLE);
        FitLessons.setVisibility(findViewById(R.id.btnBoxingLessons).INVISIBLE);


        final ImagePopup imagePopup = new ImagePopup(this);




        FitLessons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkoutList();
            }
        });

        BoxingLessons.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                BoxingLessons();
            }
        });


        imageViewLogo = findViewById(R.id.imageViewLogo);
        imagePopup.initiatePopup(imageViewLogo.getDrawable());

        imageViewLogo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                imagePopup.viewPopup();
                return true;
            }
        });


        PicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }


        });


        t1.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    //Log.w(TAG, "getInstanceId failed", task.getException());
                    Toast.makeText(MemberActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                    return;
                }
                String UserToken = task.getResult().getToken();
                //Toast.makeText(MemberActivity.this, UserToken, Toast.LENGTH_SHORT).show();
            }


        });


        setVisibleGone();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                xpProgressBar.setVisibility(findViewById(R.id.xPProgressBar).INVISIBLE);
                xpTextView.setVisibility(findViewById(R.id.xPProgressBar).INVISIBLE);
                EXP.setVisibility(findViewById(R.id.xPProgressBar).INVISIBLE);


                FightY.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (dataSnapshot.child("Fighter").exists()) {

                        } else {


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
                                        xpProgressBar.setVisibility(findViewById(R.id.xPProgressBar).VISIBLE);
                                        xpTextView.setVisibility(findViewById(R.id.xPProgressBar).VISIBLE);
                                        EXP.setVisibility(findViewById(R.id.xPProgressBar).VISIBLE);
                                        setVisibleGone();


                                    }


                                });
                                Sure.show();
                            }
                            FightN.setChecked(false);
                        }
                        if (FightN.isChecked()) {
                            FightY.setChecked(false);

                        }
                    }
                });
                FightN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (dataSnapshot.child("Fighter").exists()) {

                        } else {
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
                                        myRef.child("Fighter").setValue("NO/FITNESS ONLY");
                                        xpProgressBar.setVisibility(findViewById(R.id.xPProgressBar).VISIBLE);
                                        xpTextView.setVisibility(findViewById(R.id.xPProgressBar).VISIBLE);
                                        EXP.setVisibility(findViewById(R.id.xPProgressBar).VISIBLE);
                                        setVisibleGone();


                                    }


                                });
                                Nope.show();
                            }


                            FightY.setChecked(false);

                        }
                        if (FightY.isChecked()) {
                            FightN.setChecked(false);
                        }
                    }

                });

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
                if (dataSnapshot.child("Height").exists()) {
                    String Height = dataSnapshot.child("Height").getValue().toString();
                    MemberActivity.Height.setText(Height);
                }
                if (dataSnapshot.child("Weight").exists()) {
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


            public void getStats() {
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("Height").exists()) {
                            String Height = dataSnapshot.child("Height").getValue().toString();
                            MemberActivity.Height.setText(Height);
                        }
                        if (dataSnapshot.child("Weight").exists()) {
                            String Weight = dataSnapshot.child("Weight").getValue().toString();
                            MemberActivity.Weight.setText(Weight);

                        }

                        if (dataSnapshot.child("Fighter").exists()) {
                            if (dataSnapshot.child("Fighter").getValue() == "NO") {
                                FightN.setChecked(true);
                                // Toast.makeText(getBaseContext(),"fighter found", Toast.LENGTH_LONG).show();
                                setVisibleGone();
                                xpProgressBar.setVisibility(findViewById(R.id.xPProgressBar).VISIBLE);
                                xpTextView.setVisibility(findViewById(R.id.xPProgressBar).VISIBLE);
                                EXP.setVisibility(findViewById(R.id.xPProgressBar).VISIBLE);
                                AddXP.setVisibility(findViewById(R.id.xPProgressBar).VISIBLE);
                                FightDisplay();

                            } else {
                                FightY.setChecked(true);
                                setVisibleGone();
                                xpProgressBar.setVisibility(findViewById(R.id.xPProgressBar).VISIBLE);
                                xpTextView.setVisibility(findViewById(R.id.xPProgressBar).VISIBLE);
                                EXP.setVisibility(findViewById(R.id.xPProgressBar).VISIBLE);
                                AddXP.setVisibility(findViewById(R.id.xPProgressBar).VISIBLE);
                                FightDisplay();
                            }
                        }


                        if (hasImage(ProfilePic) == true) {
                            setProfile();

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                Membership.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Membership.isChecked()) {
                            Membership.setTextOff("NotReady");
                            String web = Url2;
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(web));
                            startActivity(i);

                        } else {
                            Membership.setTextOff("Join StayReady");
                        }


                    }
                });


                websitelogo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Opensite();
                    }

                });


            }


            public void Opensite() {

                String web = Url;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(web));
                startActivity(i);
            }


        });

    }

    public void setVisibleGone() {
        FightN.setVisibility(findViewById(R.id.checkBoxFightN).INVISIBLE);
        FightY.setVisibility(findViewById(R.id.checkBoxFightY).INVISIBLE);
        FightOr.setVisibility(findViewById(R.id.textViewFight).INVISIBLE);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        ProfilePic.setImageBitmap(bitmap);
        PicButton.setVisibility(findViewById(R.id.btnPic).INVISIBLE);


        ImageData2 = FirebaseStorage.getInstance();
        ImageData = ImageData2.getReference();
        StorageReference uPic = ImageData.child(NUID);
        StorageReference uuPic = uPic.child("Images/" + ProfilePic.toString());

        uPic.getName().equals(uuPic.getName());
        uPic.getPath().equals(uuPic.getPath());

        ProfilePic.setDrawingCacheEnabled(true);
        ProfilePic.buildDrawingCache();
        Bitmap bitpic = ((BitmapDrawable) ProfilePic.getDrawable()).getBitmap();
        ByteArrayOutputStream OutPic = new ByteArrayOutputStream();
        bitpic.compress(CompressFormat.JPEG, 100, OutPic);
        byte[] dataPic = OutPic.toByteArray();

        UploadTask uploadTask = uPic.putBytes(dataPic);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getMetadata();
                Toast.makeText(MemberActivity.this, "Uploading Profile Photo", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MemberActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void FightDisplay() {

        BoxingLessons.setVisibility(findViewById(R.id.btnBoxingLessons).VISIBLE);
        FitLessons.setVisibility(findViewById(R.id.btnBoxingLessons).VISIBLE);

    }


    public void setProfile() {

        final long ONE_MEGABYTE = 1024 * 1024;
        ImageData2 = FirebaseStorage.getInstance();
        ImageData = ImageData2.getReference();
        StorageReference uPic = ImageData.child(NUID);
        uPic.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                ProfilePic.setMinimumHeight(dm.heightPixels);
                ProfilePic.setMinimumHeight(dm.widthPixels);
                ProfilePic.setImageBitmap(bm);
                PicButton.setVisibility(findViewById(R.id.btnPic).INVISIBLE);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                PicButton.setVisibility(findViewById(R.id.btnPic).VISIBLE);
                Toast.makeText(MemberActivity.this, "Please Take a Profile Picture", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);


        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable) drawable).getBitmap() != null;

        }

        return hasImage;
    }



    public void WorkoutList(){
           Intent intent1 = new Intent(this, WorkoutActivity.class );
           startActivity(intent1);


       }


       public void BoxingLessons(){
           Intent intent1 = new Intent(this, BoxingLessonsActivity.class );
           startActivity(intent1);
       }




}

