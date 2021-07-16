package com.example.testowy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.testowy.databinding.ActivityMainBinding;
import com.example.testowy.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    public static final String URL_TAG = "URL_TAG";

    public static final String EXTENSION_TAG = "EXTENSION_TAG";

    private ActivitySecondBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        String url = intent.getExtras().getString(URL_TAG);
        String mimeType = intent.getExtras().getString(EXTENSION_TAG);
        switch (mimeType)
        {
            case "text/html": getImage(url);
            break;
            case "video/mp4": getVideo(url);
            break;
            case "audio/mpeg": getAudio(url);
            break;
            case "text/plain": getText(url);
            break;
        }
    }

    private void getImage(String url)
    {
        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        binding.webView.loadUrl(url);
    }
    
    private void getVideo(String url)
    {
        binding.webView.loadUrl(url);
    }

    private void getAudio(String url)
    {
        binding.webView.loadUrl(url);
    }

    private void getText(String url)
    {
        binding.webView.loadUrl(url);
    }
}
