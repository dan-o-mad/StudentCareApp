package com.techsol.danny.studentcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class SecondActivity extends AppCompatActivity {

    private Button signout;
    private FirebaseAuth firebaseAuth;
    private LinearLayout llBooks, llShop;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        llBooks = findViewById(R.id.llBooks);
        llShop = findViewById(R.id.llShop);





        //signout= (Button) findViewById(R.id.btnLogout);
        firebaseAuth= FirebaseAuth.getInstance();




       /* signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logout();





            }
        });*/


        llShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(SecondActivity.this, SplashDk.class));








            }
        });





    }


    private void logout(){


        firebaseAuth.signOut();




        finish();
        Intent intent2 = new Intent(SecondActivity.this, MainActivity.class);

        startActivity(intent2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch(item.getItemId()){

            case R.id.logoutMenu:{

                logout();
                break;


            }
            case R.id.profileMenu: {

                startActivity(new Intent(SecondActivity.this, ProfileActivity.class));
                break;
            }


        }
        return super.onOptionsItemSelected(item);




    }
}
