package com.example.testowy;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.testowy.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    public static final String URL_TAG = "URL_TAG";

    public static final String MIME_TYPE_TAG = "MIME_TYPE_TAG";

    private ActivitySecondBinding binding;

    private MyViewModel viewModel;

    private String url;
    private String mimeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        getData();
        subscribeObserver();
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

    private void getData()
    {
        Intent intent = getIntent();
        url = intent.getExtras().getString(URL_TAG);
        mimeType = intent.getExtras().getString(MIME_TYPE_TAG);
        viewModel.selectUrl(url);
        viewModel.selectMimeType(mimeType);
    };

    private void subscribeObserver()
    {
        final Observer<String> urlObserver = new Observer<String>() {
            @Override
            public void onChanged(String urlContent) {
                url = urlContent;
            }
        };
        viewModel.getCurrentUrl().observe(this, urlObserver);

        final Observer<String> mimeTypeObserver = new Observer<String>() {
            @Override
            public void onChanged(String mimeTypeContent) {
                mimeType = mimeTypeContent;
            }
        };
        viewModel.getCurrentMimeType().observe(this, mimeTypeObserver);
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
        String html =
            "<html>" +
                "<head>" +
                    "<meta name=\"viewport\" content=\"width=device-width\">" +
                "</head>" +
                "<body>" +
                    "<div align=\"center\">" +
                    "<audio controls autoplay name=\"media\">" +
                        "<source src='" + url + "'" +
                    "type=\"audio/mpeg\">" +
                    "</audio>" +
                    "</div>" +
                "</body>" +
            "</html>";
        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setDomStorageEnabled(true);
        binding.webView.loadData(html, "text/html", null);

        /*
         binding.webView.setWebViewClient(new WebViewClient());
         WebSettings webSettings = binding.webView.getSettings();
         webSettings.setJavaScriptEnabled(true);
         webSettings.setAllowContentAccess(true);
         webSettings.setDomStorageEnabled(true);
         binding.webView.loadUrl(url);

         */
    }

    private void getText(String url)
    {
        binding.webView.loadUrl(url);
    }
}
