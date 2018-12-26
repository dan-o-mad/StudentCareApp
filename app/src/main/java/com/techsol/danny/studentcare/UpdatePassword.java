package com.techsol.danny.studentcare;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePassword extends AppCompatActivity {


    private Button update;
    private EditText newPassword;
    private FirebaseUser firebaseUser;
    private  FirebaseAuth firebaseAuth;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);



        update = findViewById(R.id.btnUpdatePassword);

        newPassword = findViewById(R.id.etNewPassword);

       // getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String userPasswordNew = newPassword.getText().toString();

                if(userPasswordNew.isEmpty()){

                    Toast.makeText(UpdatePassword.this, "Please enter a valid password", Toast.LENGTH_SHORT).show();

                }else {

                    firebaseUser.updatePassword(userPasswordNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {


                            if (task.isSuccessful()) {


                                Toast.makeText(UpdatePassword.this, "Password Changed", Toast.LENGTH_SHORT).show();

                                finish();

                            } else {

                                Toast.makeText(UpdatePassword.this, "Password Change failed", Toast.LENGTH_SHORT).show();

                            }


                        }
                    });
                }



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
