package com.j222102450.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.jetbrains.annotations.Async;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;

public class ForexMainActivity2 extends AppCompatActivity {
    private SwipeRefreshLayout _swipeRefreshLayout3;
    private RecyclerView _recyclerView3;
    private TextView _timestampTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cuaca_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.swipeRefreshLayout3), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initSwipeRefreshLayout();
        _recyclerView3 = findViewById(R.id.recyclerView3);
        _timestampTextView = findViewById(R.id.timestampTextView);

        bindRecyclerView();
    }

    private void bindRecyclerView() {
        String ratesUrl = "https://openexchangerates.org/api/latest.json?app_id=287d75acf4fb4699a4e289d4d6c52336";
        String currencyUrl = "https://openexchangerates.org/api/currencies.json";
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        Log.d("jsp", "accessing " + ratesUrl);

        asyncHttpClient.get(ratesUrl, new AsyncHttpResponseHandler() {
            JSONObject ratesObj;
            long timestamp;
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                Log.d("jsp", new String(responseBody));
                try {
                    JSONObject root = new JSONObject(new String(responseBody));
                    ratesObj = root.getJSONObject("rates");
                    timestamp = root.getLong("timestamp");

                    double idrRate = ratesObj.getDouble("IDR");

                    asyncHttpClient.get(currencyUrl, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try {
                                JSONObject currencies = new JSONObject(new String(responseBody));
                                List<ForexModel> forexList = new ArrayList<>();

                                Iterator<String> keys = ratesObj.keys();
                                while (keys.hasNext()) {
                                    String code = keys.next();
                                    if (code.equals("IDR")) continue;

                                    try {
                                        double rate = idrRate / ratesObj.getDouble(code);
                                        String name = currencies.optString(code, "Unknown");
                                        forexList.add(new ForexModel(code, name, rate));
                                    } catch (JSONException e) {
                                        Log.e("Forex Error", "Gagal Paring untuk " + code, e);
                                    }
                                }

                                setTimestamp(timestamp);

                                ForexAdapter adapter = new ForexAdapter(forexList);
                                _recyclerView3.setLayoutManager(new LinearLayoutManager(ForexMainActivity2.this));
                                _recyclerView3.setAdapter(adapter);
                            } catch (JSONException e) {
                                Toast.makeText(ForexMainActivity2.this, "Gagal parsing data mata uang", Toast.LENGTH_SHORT).show();
                            }

                            _swipeRefreshLayout3.setRefreshing(false);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(ForexMainActivity2.this, "Gagal ambil nama mata uang", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    Toast.makeText(ForexMainActivity2.this, "Gagal parsing kurs", Toast.LENGTH_SHORT).show();
                    _swipeRefreshLayout3.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("jsp", error.getMessage());

                Toast.makeText(ForexMainActivity2.this, "Gagal ambil kurs", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTimestamp(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        format.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        String dateTime = format.format(new Date(timestamp * 1000));

        _timestampTextView.setText("Tanggal dan Waktu (UTC): "+dateTime);
    }

    private void initSwipeRefreshLayout() {
        _swipeRefreshLayout3 = findViewById(R.id.swipeRefreshLayout3);
        _swipeRefreshLayout3.setOnRefreshListener(this::bindRecyclerView);
    }
}