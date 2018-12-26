package com.techsol.danny.studentcare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profilePic;
    private TextView profileName, profileAge,profileEmail;
    private LinearLayout changePassword, llRefresh, llBack;
    private Button profileUpdate;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private SwipeRefreshLayout mSwipe;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profilePic = findViewById(R.id.ivProfilePic);
        profileName = findViewById(R.id.tvProfileName);
        profileAge = findViewById(R.id.tvProfileAge);
        profileEmail = findViewById(R.id.tvProfileEmail);
        profileUpdate = findViewById(R.id.btnProfileUpdate);
        changePassword = findViewById(R.id.btnChangePassword);
        llRefresh = findViewById(R.id.llRefresh);
        llBack = findViewById(R.id.llBack);
        mSwipe = findViewById(R.id.mSwipe);

       // getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseStorage = FirebaseStorage.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(firebaseAuth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {


                Picasso.get().load(uri).fit().centerCrop().into(profilePic);









            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


           UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
           profileName.setText( userProfile.getUserName());
           profileAge.setText("Age " + userProfile.getUserAge());
           profileEmail.setText(userProfile.getUserEmail());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


                Toast.makeText(ProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });

        profileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(ProfileActivity.this, UpdateProfile.class));
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(ProfileActivity.this, UpdatePassword.class));

            }
        });


        llRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
                finish();


            }
        });


        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(ProfileActivity.this, SecondActivity.class));
                finish();


            }
        });

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
                mSwipe.setRefreshing(false);
                finish();


            }
        });






    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){

            case android.R.id.home:
                onBackPressed();

        }
        return super.onOptionsItemSelected(item);
    }
}
