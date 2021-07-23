package com.example.testowy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.testowy.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String URL_TAG = "URL_TAG";

    public static final String MIME_TYPE_TAG = "MIME_TYPE_TAG";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnImage.setOnClickListener(this);
        binding.btnVideo.setOnClickListener(this);
        binding.btnAudio.setOnClickListener(this);
        binding.btnTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.btnImage.getId())
        {
            //final String url = "https://4rooms.com.pl/userdata/public/gfx/1279cded36c5a39ffac5a8b86ffa654c.jpg";
            //final String url = "https://people.sc.fsu.edu/~jburkardt/data/png/lizard.png";
            final String url = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/Rotating_earth_%28large%29.gif/240px-Rotating_earth_%28large%29.gif";
            final String mimeType = "text/html";
            startSecondActivity(url, mimeType);
        }
        else if (view.getId() == binding.btnVideo.getId())
        {
            final String url = "http://vstatic.teledyski.info/www/s7/hd/vid17144.mp4";
            final String mimeType = "video/mp4";
            startSecondActivity(url, mimeType);
        }
        else if (view.getId() == binding.btnAudio.getId())
        {
            final String url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";
            //final String url = "https://www2.cs.uic.edu/~i101/SoundFiles/CantinaBand60.wav";
            //final String url = "https://upload.wikimedia.org/wikipedia/commons/c/c8/Example.ogg";
            //final String url = "http://chomikuj.pl/DarX1024/Do+kom*c3*b3rki/*c5*9amieszne+odg*c5*82osy+(format+AMR)/Cool+music,20835134.amr";
            final String mimeType =
                    "audio/mpeg";
                    /*"audio/wav";*/
                    /*"audio/ogg";*/
                    /*"audio/amr";*/
            startSecondActivity(url, mimeType);
        }
        else if (view.getId() == binding.btnTxt.getId())
        {
            final String url = "http://example.com/the-text-document.txt";
            final String mimeType =
                    "text/plain";
            startSecondActivity(url, mimeType);
        }
    }

    private void startSecondActivity(String url, String mimeType)
    {
        final Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(URL_TAG, url);
        intent.putExtra(MIME_TYPE_TAG, mimeType);
        startActivity(intent);
    }


}