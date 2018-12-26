package com.techsol.danny.studentcare;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;


public class Splash extends AppCompatActivity {


    private ProgressBar progressBar;
    LinearLayout l1,l2;
    Button btnSub;
    Animation uptodown,downtoup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        btnSub = (Button)findViewById(R.id.btnSub);
        l1 = (LinearLayout) findViewById(R.id.l1);
        l2 = (LinearLayout) findViewById(R.id.l2);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);



        Thread td = new Thread(){


             public void run(){

try{


    sleep(4500);
    progressBar.getProgress();



}

catch(Exception ex) {

    ex.printStackTrace();

} finally {

        Intent it = new Intent(Splash.this,MainActivity.class);
        startActivity(it);
        finish();
}
}


        };td.start();


        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Uri uri = Uri.parse("http://www.daniyalkhan.tk");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });




    }
}
