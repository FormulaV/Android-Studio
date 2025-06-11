package com.j222102450.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class CuacaMainActivity extends AppCompatActivity {
    private RecyclerView _recyclerView2;
    private SwipeRefreshLayout _swipeRefreshLayout2;
    private TextView _totalTextView;
    @SuppressLint("MissingInflateId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cuaca_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.swipeRefreshLayout2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        _recyclerView2 = findViewById(R.id.recyclerView2);
        _swipeRefreshLayout2 = findViewById(R.id.swipeRefreshLayout2);

        initSwipeRefreshLayout();
        bindRecyclerView2();
    }


    private void initSwipeRefreshLayout()
    {
        _swipeRefreshLayout2 = findViewById(R.id.swipeRefreshLayout2);

        _swipeRefreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bindRecyclerView2();
                _swipeRefreshLayout2.setRefreshing(false);
            }
        });
    }

    private void bindRecyclerView2() {
        _swipeRefreshLayout2.setRefreshing(true);
        String url = "https://api.openweathermap.org/data/2.5/forecast?id=1630789&appid=c066400094976a3781669abd1338c074";
        AsyncHttpClient ahc = new AsyncHttpClient();

        ahc.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Gson gson = new Gson();
                CuacaRootModel rm = gson.fromJson(new String(responseBody), CuacaRootModel.class);


                RecyclerView.LayoutManager lm = new LinearLayoutManager(CuacaMainActivity.this);
                _recyclerView2.setLayoutManager(lm);

                CuacaAdapter ca = new CuacaAdapter(CuacaMainActivity.this, rm);
                _recyclerView2.setAdapter(ca);

                _swipeRefreshLayout2.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                _swipeRefreshLayout2.setRefreshing(false);
            }
        });
    }
}