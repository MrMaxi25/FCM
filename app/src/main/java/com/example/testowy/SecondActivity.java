package com.example.testowy;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.testowy.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    public static final String URL_TAG = "URL_TAG";

    public static final String MIME_TYPE_TAG = "MIME_TYPE_TAG";

    private ActivitySecondBinding binding;

    private MyViewModel viewModel;

   /* private String url;
    private String mimeType;*/
    private String html;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        getData();
        subscribeObserver();
        /*enterMimeType();*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.webView.onPause();
        binding.webView.destroy();
    }

    private void enterMimeType(String url, String mimeType)
    {
        switch (mimeType)
        {
            case "text/html": getImage(url);
                break;
            case "video/mp4": getVideo(url);
                break;
            case "audio/mpeg"
                    /* "audio/wav"*/
                    /*"audio/ogg"*/
                    /*"audio/amr"*/
                    : getAudio(url, mimeType);
                break;
            case "text/plain": getText(url);
                break;
        }
    }

    private void getData()
    {
        final Intent intent = getIntent();
        final String url = intent.getExtras().getString(URL_TAG);
        final String mimeType = intent.getExtras().getString(MIME_TYPE_TAG);
        viewModel.selectUrl(url);
        viewModel.selectMimeType(mimeType);
    }

    private void subscribeObserver()
    {
        /*final Observer<String> urlObserver = new Observer<String>() {
            @Override
            public void onChanged(String urlContent) {
               *//* url = urlContent;*//*
            }
        };
        viewModel.getCurrentUrl().observe(this, urlObserver);*/

        final Observer<String> mimeTypeObserver = new Observer<String>() {
            @Override
            public void onChanged(String mimeTypeContent) {
                //mimeType = mimeTypeContent;
                enterMimeType(viewModel.getCurrentUrl().getValue(), mimeTypeContent);
            }
        };
        viewModel.getCurrentMimeType().observe(this, mimeTypeObserver);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return true;
    }

    private void getImage(String url)
    {
        final WebSettings webSettings = binding.webView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        zoomControls();
        binding.webView.loadUrl(url);
    }

    private void getVideo(String url)
    {
        html =
            "<html>" +
            "<head>" +
            "<meta name=\"viewport\" content=\"width=device-width\">" +
            "</head>" +
            "<style>" +
            "p {padding 0; margin 0;}" +
            "</style>" +
            "<body>" +
            "<video style=\"width:100%; height:100%;\">" +
            "<source src='" + url + "'" +
            "type=\"video/mp4\">" +
            "</video>" +
            "</body>" +
            "</html>";
        zoomControls();
        binding.webView.loadData(html, "text/html", null);
    }

    private void getAudio(String url, String mimeType)
    {
        switch(mimeType)
        {
            case "audio/mpeg":
                html = "<html>" +
                        "<head>" +
                        "<style>" +
                        ".centered {" +
                        "position: fixed;" +
                        "top: 50%;" +
                        "left: 50%;" +
                        "transform: translate(-50%, -50%);}" +
                        "</style>" +
                        "<meta name=\"viewport\" content=\"width=device-width\">" +
                        "</head>" +
                        "<body>" +
                        "<div class=\"centered\">" +
                        "<audio controls autoplay name=\"media\">" +
                        "<source src='" + url + "'" +
                        "type=\"audio/mpeg\">" +
                        "</audio>" +
                        "</div>" +
                        "</body>" +
                        "</html>";
                break;
            case "audio/wav":
                html =  "<html>" +
                        "<head>" +
                        "<style>" +
                        ".centered {" +
                        "position: fixed;" +
                        "top: 50%;" +
                        "left: 50%;" +
                        "transform: translate(-50%, -50%);}" +
                        "</style>" +
                        "<meta name=\"viewport\" content=\"width=device-width\">" +
                        "</head>" +
                        "<body>" +
                        "<div class=\"centered\">" +
                        "<audio controls autoplay name=\"media\">" +
                        "<source src='" + url + "'" +
                        "type=\"audio/x-wav\">" +
                        "</audio>" +
                        "</div>" +
                        "</body>" +
                        "</html>";
                break;
            case "audio/ogg":
                html = "<html>" +
                        "<head>" +
                        "<style>" +
                        ".centered {" +
                        "position: fixed;" +
                        "top: 50%;" +
                        "left: 50%;" +
                        "transform: translate(-50%, -50%);}" +
                        "</style>" +
                        "<meta name=\"viewport\" content=\"width=device-width\">" +
                        "</head>" +
                        "<body>" +
                        "<div class=\"centered\">" +
                        "<audio controls autoplay name=\"media\">" +
                        "<source src='" + url + "'" +
                        "type=\"audio/ogg\">" +
                        "</audio>" +
                        "</div>" +
                        "</body>" +
                        "</html>";
                break;
        }
        binding.webView.loadData(html, "text/html", null);
    }

    private void getText(String url)
    {
        zoomControls();
        binding.webView.loadUrl(url);
    }

    private void zoomControls()
    {
        final WebSettings webSettings = binding.webView.getSettings();
        webSettings.setBuiltInZoomControls(true);
    }
}
