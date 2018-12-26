package com.techsol.danny.studentcare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Register extends AppCompatActivity {


    private EditText userName, userPassword, userEmail,userAge;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
    private ImageView userProfilePic;
    String name,password,email, age;
    private FirebaseStorage firebaseStorage;
    private  static int PICK_IMAGE = 123;
    Uri imagePath;
    private StorageReference storageReference;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){

            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);

                userProfilePic.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();


        storageReference = firebaseStorage.getReference();
       // StorageReference myRef1 = storageReference.child(firebaseAuth.getUid());


        userProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);


            }
        });






        regButton.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {


                                             if(validate()) {


                                                 //upload data to frebase



                                                 String user_email = userEmail.getText().toString().trim();

                                                 String user_password = userPassword.getText().toString().trim();


                                                 firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                     @Override
                                                     public void onComplete(@NonNull Task<AuthResult> task) {

                                                         if(task.isSuccessful()){
                                                            // Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                             //startActivity(new Intent(Register.this, MainActivity.class));
                                                             //finish();
                                                             //sendEmailVerification();
                                                             sendUserData();
                                                             firebaseAuth.signOut();


                                                             Toast.makeText(Register.this, "Successfully Registered, Upload Complete!", Toast.LENGTH_SHORT).show();
                                                             //firebaseAuth.signOut();
                                                             finish();


                                                             startActivity(new Intent(Register.this, MainActivity.class));


                                                         }

                                                         else{


                                                             Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();



                                                         }

                                                     }
                                                 });

                                             }

                                         }
                                     }
        );


        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(Register.this, MainActivity.class));
               finish();


            }
        });

    }










    private void setupUIViews(){



        userName = (EditText) findViewById(R.id.etUserNameReg);
        userPassword = (EditText) findViewById(R.id.etPasswordReg);
        userEmail = (EditText) findViewById(R.id.etEmailReg);
        userAge = (EditText) findViewById(R.id.etAgeReg);

        regButton = (Button) findViewById(R.id.btnRegister);
        userLogin = (TextView) findViewById(R.id.tvUserLogin);

        userProfilePic = (ImageView) findViewById(R.id.ivProfile);







    }


    private Boolean validate() {

        Boolean result = false;

        name = userName.getText().toString();



        password = userPassword.getText().toString();

         email = userEmail.getText().toString();

         age = userAge.getText().toString();


        if ( password.isEmpty() || email.isEmpty() || name.isEmpty() || age.isEmpty() || imagePath == null ) {

            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();


        } else {


            result = true;
        }

        return result;

    }

     private  void sendEmailVerification(){


             final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                  if(firebaseUser != null){


                      firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {


                              if(task.isSuccessful()){

                                  sendUserData();


                                  Toast.makeText(Register.this, "Successfully Registered, Verification Email Sent!", Toast.LENGTH_SHORT).show();
                                  firebaseAuth.signOut();
                                  finish();
                                  startActivity(new Intent(Register.this, MainActivity.class));


                              }else{


                                  Toast.makeText(Register.this, "Verification mail hasn't been sent!", Toast.LENGTH_SHORT).show();


                              }



                          }
                      });



    }


}


private void sendUserData(){


    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
    StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic");
    UploadTask uploadTask = imageReference.putFile(imagePath);
    uploadTask.addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {


            Toast.makeText(Register.this, "Upload Failed", Toast.LENGTH_SHORT).show();



        }
    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


            Toast.makeText(Register.this, "Upload Successful", Toast.LENGTH_SHORT).show();









        }
    });


    UserProfile userProfile = new UserProfile(age, email, name);
    myRef.setValue(userProfile);



}

}

