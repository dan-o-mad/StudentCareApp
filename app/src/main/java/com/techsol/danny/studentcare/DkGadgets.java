package com.techsol.danny.studentcare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.techsol.danny.studentcare.R;

public class DkGadgets extends AppCompatActivity {

    WebView webView;
    String myUrl = "https://www.dkgadgets.pk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dk_gadgets);


        webView = findViewById(R.id.wb);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(myUrl);


        webView.setWebViewClient(new WebViewClient(){


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);

                return true;
            }
        });



    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {

            webView.goBack();
        }else{

            super.onBackPressed();

        }
    }
}
