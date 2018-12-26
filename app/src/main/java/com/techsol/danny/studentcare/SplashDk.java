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


public class SplashDk extends AppCompatActivity {


    private ProgressBar progressBar;
    LinearLayout l11,l22;
   // Button btnSub;
    Animation uptodown1,downtoup1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_dk);


       // btnSub = (Button)findViewById(R.id.btnSub);
        l11 = (LinearLayout) findViewById(R.id.l11);
        l22 = (LinearLayout) findViewById(R.id.l22);
        uptodown1 = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup1 = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        l11.setAnimation(uptodown1);
        l22.setAnimation(downtoup1);



        Thread td = new Thread(){


            public void run(){

                try{


                    sleep(6500);
                    progressBar.getProgress();



                }

                catch(Exception ex) {

                    ex.printStackTrace();

                } finally {

                    Intent it = new Intent(SplashDk.this,DkGadgets.class);
                    startActivity(it);
                    finish();
                }
            }


        };td.start();


       /* btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Uri uri = Uri.parse("http://www.daniyalkhan.tk");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
*/



    }
}
