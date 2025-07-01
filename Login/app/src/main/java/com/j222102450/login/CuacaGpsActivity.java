package com.j222102450.login;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CuacaGpsActivity extends AppCompatActivity {

    private WebView _webView;
    private TextView _koordinatTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cuaca_gps);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle param = getIntent().getBundleExtra("param");
        _koordinatTextView = findViewById(R.id.textView_koordinat);
        _koordinatTextView.setText(param.getDouble("lat") + " x " + param.getDouble("lon"));

        _webView = findViewById(R.id.wvMain);

        String url = "https://www.google.com/maps" +
                "?q=" + param.getDouble("lat") + "," + param.getDouble("lon") +
                "&ll=" + param.getDouble("lat") + "," + param.getDouble("lon") +
                "&z=10";

        WebSettings webSettings = _webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        _webView.setWebViewClient(new WebViewClient());
        _webView.loadUrl(url);
    }
}