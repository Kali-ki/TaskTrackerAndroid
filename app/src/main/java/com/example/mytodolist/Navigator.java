package com.example.mytodolist;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class Navigator extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigator);

        // Called by default by this application to display in a WebView the url

        String url = getIntent().getData().toString();

        WebView webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        WebSettings params = webView.getSettings();
        params.setJavaScriptEnabled(true);
        params.setBuiltInZoomControls(true);

        webView.loadUrl(url);
    }

}
