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
            case /*"audio/mpeg"*/
                   /* "audio/wav"*/
                "audio/ogg"
                    : getAudio(url);
            break;
            case /*"text/plain"*/
                    "application/msword": getText(url);
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
    }

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
        String html =
            "<html>" +
                "<head>" +
                    "<meta name=\"viewport\" content=\"width=device-width\">" +
                "</head>" +
                "<body>" +
                    "<video style=\"width:100%; height:100%;\">" +
                        "<source src='" + url + "'" +
                    "type=\"video/mp4\">" +
                    "</video>" +
                "</body>" +
            "</html>";
        binding.webView.loadData(html, "text/html", null);
    }

    private void getAudio(String url)
    {
        String html =
            /*"<html>" +
                "<head>" +
                    "<meta name=\"viewport\" content=\"width=device-width\">" +
                "</head>" +
                "<body>" +
                    "<audio style=\"width:100%; height:100%;\" controls autoplay name=\"media\">" +
                        "<source src='" + url + "'" +
                    "type=\"audio/mpeg\">" +
                    "</audio>" +
                "</body>" +
            "</html>";*/

        /*"<html>" +
                "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width\">" +
                "</head>" +
                "<body>" +
                "<audio style=\"width:100%; height:100%;\" controls autoplay name=\"media\">" +
                "<source src='" + url + "'" +
                "type=\"audio/x-wav\">" +
                "</audio>" +
                "</body>" +
                "</html>";*/

                "<html>" +
                "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width\">" +
                "</head>" +
                "<body>" +
                "<audio style=\"width:100%; height:100%;\" controls autoplay name=\"media\">" +
                "<source src='" + url + "'" +
                "type=\"audio/ogg\">" +
                "</audio>" +
                "</body>" +
                "</html>";
        binding.webView.loadData(html, "text/html", null);
    }

    private void getText(String url)
    {
        binding.webView.loadUrl(url);
        /*binding.webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });*/
    }
}
