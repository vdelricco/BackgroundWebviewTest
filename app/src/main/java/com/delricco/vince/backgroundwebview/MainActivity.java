package com.delricco.vince.backgroundwebview;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, BackgroundWebView.class));
        finish();
    }

    public static class BackgroundWebView extends Service {
        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            final HandlerThread handlerThread = new HandlerThread("TestHandlerThread");
            handlerThread.start();
            final Handler handler = new Handler(handlerThread.getLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    WebView webview = new WebView(getApplicationContext());//findViewById(R.id.webview);
                    webview.getSettings().setJavaScriptEnabled(true);
                    webview.setWebViewClient(new WebViewClient() {
                        @Override
                        public void onLoadResource(WebView view, String url) {
                            System.out.println("URL: " + url);
                            super.onLoadResource(view, url);
                        }
                    });
                    webview.loadUrl("http://www.google.com");
                }
            }, 8000);
            return super.onStartCommand(intent, flags, startId);
        }
    }
}
