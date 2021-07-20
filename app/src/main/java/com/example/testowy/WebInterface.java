package com.example.testowy;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class WebInterface {
    Context mContext;

    WebInterface(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public void playSound(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void pauseSound(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }
}
