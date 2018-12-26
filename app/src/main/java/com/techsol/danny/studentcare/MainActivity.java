package com.techsol.danny.studentcare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.String;

public class MainActivity extends AppCompatActivity {


    private EditText name, pass;
    private TextView ti,userRegistration;
    private Button login, signup;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView forgotPassword;



    int counter=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        name = (EditText) findViewById(R.id.etName);
        pass = (EditText) findViewById(R.id.etPass);
        ti = (TextView) findViewById(R.id.tvInfo);
        login = (Button) findViewById(R.id.btnLogin);


        signup = (Button) findViewById(R.id.signup);
        forgotPassword = (TextView) findViewById(R.id.tvForgotPassword);

       //info.setText("No of attempts remaining: 5");
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();


        if(user != null ){

            finish();



            startActivity(new Intent(MainActivity.this, SecondActivity.class));


        }



login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        validate(name.getText().toString(), pass.getText().toString());

    }
});

 signup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {



        startActivity(new Intent(MainActivity.this, Register.class));
        //finish();




    }
});

 forgotPassword.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {



         startActivity(new Intent(MainActivity.this, PasswordActivity.class));


     }
 });




        }


    private void validate(String name1, String pass1){



        if ( name1.isEmpty() || pass1.isEmpty()) {

            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();


        }


        else{

        progressDialog.setMessage("Logging In, Please Wait...");
        progressDialog.show();




              firebaseAuth.signInWithEmailAndPassword(name1, pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {


                      if (task.isSuccessful()) {

                          progressDialog.dismiss();

                          //Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                          checkEmailVerification();


                          //startActivity(new Intent(MainActivity.this, SecondActivity.class));
                          //finish();


                      }else{


                          Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                          counter--;
                          ti.setText("No of attempts remaining:" + counter);
                          progressDialog.dismiss();
                          if(counter == 0){

                              login.setEnabled(false);

                          }

                      }



                  }
              });





    }}




private  void  checkEmailVerification(){

        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailFlag = firebaseUser.isEmailVerified();

     startActivity(new Intent(MainActivity.this, SecondActivity.class));
     finish();


    /*if(emailFlag){
            finish();
            startActivity(new Intent(MainActivity.this, SecondActivity.class));


        }else{


            Toast.makeText(this, "Verify Your Email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();

        }
*/
}

}
