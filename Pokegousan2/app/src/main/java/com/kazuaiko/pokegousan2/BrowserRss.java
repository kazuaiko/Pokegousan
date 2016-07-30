package com.kazuaiko.pokegousan2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/**
 * Created by naitou_ka on 2016/07/30.
 */
public class BrowserRss extends ActionBarActivity {
    private WebView myWebView;

    TextView textView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browser_rss);

        Intent intent = getIntent();
        String link = intent.getStringExtra(MainActivity.EXTRA_LINK);
        String title = intent.getStringExtra(MainActivity.EXTRA_TITLE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        textView = (TextView) findViewById(R.id.title);
        textView.setText(title);
        textView.setHeight(130);
        textView.setBackgroundColor(Color.parseColor("#ccffff"));

        myWebView = (WebView) findViewById(R.id.myWebView);

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                setProgressBarIndeterminateVisibility(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                String text = myWebView.getTitle();
                textView.setText(text);
            }
        });

        myWebView.loadUrl(link);

        myWebView.getSettings().setJavaScriptEnabled(true);

    }

    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack();
            return;
        }
        super.onBackPressed();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (myWebView != null) {
            myWebView.stopLoading();
            myWebView.setWebViewClient(null);
            myWebView.destroy();
        }
        myWebView = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        switch(id){
            case  R.id.action_settings:
                break;
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
