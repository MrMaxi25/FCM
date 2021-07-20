package com.example.testowy;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
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
        Intent intent = getIntent();
        url = intent.getExtras().getString(URL_TAG);
        mimeType = intent.getExtras().getString(MIME_TYPE_TAG);
        viewModel.selectUrl(url);
        viewModel.selectMimeType(mimeType);

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
        switch (mimeType)
        {
            case "text/html": getImage(url);
            break;
            case "video/mp4": getVideo(url);
            break;
            case "audio/mpeg": getAudio(url, mimeType);
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

    private void getAudio(String url, String mimeType)
    {
        WebSettings webSettings = binding.webView.getSettings();
        String html =
                "<html>\n" +
                        "<head>\n" +
                        "<script type=\"text/javascript\">\n" +
                        "    function playSound(toast) {\n" +
                        "        Android.showToast(toast);\n" +
                        "    }\n" +
                        "\n" +
                        "    function pauseSound(toast) {\n" +
                        "        Android.showToast(toast);\n" +
                        "    }\n" +
                        "</script>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<input type=\"button\" value=\"Say hello\" onClick=\"playSound('Sound Played!')\" />\n" +
                        "<input type=\"button\" value=\"Say hello\" onClick=\"pauseSound('Sound Paused!')\" />\n" +
                        "</body>\n" +
                        "</html>";
        webSettings.setJavaScriptEnabled(true);
        binding.webView.addJavascriptInterface(new WebInterface(this), "Android");
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        binding.webView.setFocusable(true);
        webSettings.getLoadWithOverviewMode();
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setDisplayZoomControls(true);
        webSettings.setDatabaseEnabled(true);
        binding.webView.loadData(html, mimeType, "UTF-8");
    }

    private void getText(String url)
    {
        binding.webView.loadUrl(url);
    }
}
