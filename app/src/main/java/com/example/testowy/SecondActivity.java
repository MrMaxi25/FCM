package com.example.testowy;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.content.CursorLoader;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.testowy.databinding.ActivitySecondBinding;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class SecondActivity extends AppCompatActivity {

    public static final String URL_TAG = "URL_TAG";

    public static final String MIME_TYPE_TAG = "MIME_TYPE_TAG";

    private ActivitySecondBinding binding;

    private MyViewModel viewModel;

    /*private String url;*/
    private String mimeType;
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

    private void enterMimeType(String url, String mimeType) throws URISyntaxException {
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
        final Observer<String> urlObserver = new Observer<String>() {
            @Override
            public void onChanged(String urlContent) {
               /* url = urlContent;*/
            }
        };
        viewModel.getCurrentUrl().observe(this, urlObserver);

        final Observer<String> mimeTypeObserver = new Observer<String>() {
            @Override
            public void onChanged(String mimeTypeContent) {
                /*mimeType = mimeTypeContent;*/
                try {
                    enterMimeType(viewModel.getCurrentUrl().getValue(), mimeTypeContent);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
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

    private void getImage(String url) throws URISyntaxException
    {
        binding.webView.getSettings().setAllowFileAccess(true);
        String base = Environment.getExternalStorageDirectory().getAbsolutePath();
        String imagePath = "file://"+ base + "/zakon.jpg";
        String html = "<html><head></head><body> <img src=\""+ imagePath + "\"> </body></html>";
        binding.webView.loadUrl(imagePath);
       /* binding.webView.loadDataWithBaseURL("", html, "text/html","utf-8", "");*/

        /*final String imagePath = "file://" + "/storage/emulated/0/Android/data/com.google.android.videos/files/Movies/zakon.jpg";
        String html = "<img src=\"" + imagePath + "\">";
        binding.webView.post(new Runnable() {
            @Override
            public void run() {
                binding.webView.evaluateJavascript("postImage('" + imagePath + "')", null);
            }
        });
        binding.webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);*/

        /*String imagePath = "file://" + file.getAbsolutePath();
        String html = "<html><body><img src=\"" + imagePath + "\"></body></html>";
        binding.webView.loadDataWithBaseURL(null, html, "text/html","utf-8", null);*/
        /*final WebSettings webSettings = binding.webView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        zoomControls();
        binding.webView.loadUrl(url);*/
    }

    @Nullable
    public static String getPathFromUri(Context context, Uri uri) {
        final String DATA_COLUMN = "_data";
        final CursorLoader loader = new CursorLoader(context, uri, new String[]{DATA_COLUMN},
                null, null, null);
        try (Cursor cursor = loader.loadInBackground()) {
            final int columnIndex = cursor.getColumnIndex(DATA_COLUMN);
            cursor.moveToFirst();
            return cursor.getString(columnIndex);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
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
