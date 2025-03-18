package com.j222102450.webview;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button _tampilkanButton;
    private EditText _urlEditText;
    private WebView _webView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        _tampilkanButton = (Button)findViewById(R.id.tampilkanButton);
        _urlEditText = (EditText)findViewById(R.id.urlEditText);
        _webView1 = (WebView)findViewById(R.id.webView1);
    }

    public void tampilkanButton_onClick(View view) {
        String url = _urlEditText.getText().toString();
        _webView1.setWebViewClient(new WebViewClient());
        _webView1.loadUrl(url);
    }
}