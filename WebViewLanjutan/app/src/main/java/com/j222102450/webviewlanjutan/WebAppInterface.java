package com.j222102450.webviewlanjutan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class WebAppInterface {
    private Activity _activity;
    private Context _context;

    public WebAppInterface(Context context, Activity activity) {
        _context = context;
        _activity = activity;
    }

    @JavascriptInterface
    public void showToast(String message) {
        Toast.makeText(_context, message, Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface
    public void showSms() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
        _context.startActivity(intent);
    }

    // Method untuk membuka aplikasi WhatsApp
    @JavascriptInterface
    public void showWhatsApp(String nomorHp, String pesan) {
        try {
            // Membuat URL untuk membuka WhatsApp dengan nomor dan pesan
            String url = "https://wa.me/" + nomorHp + "?text=" + Uri.encode(pesan);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            _context.startActivity(intent);  // Membuka WhatsApp
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(_context, "Terjadi kesalahan saat membuka WhatsApp", Toast.LENGTH_SHORT).show();
        }
    }
}
