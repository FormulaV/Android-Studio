package com.j222102450.webviewangular;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class AndroidBridge {
    private Context context;
    private Activity activity;

    public AndroidBridge(Activity activity) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
    }

    @JavascriptInterface
    public void showNotification(String title, String message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "webview_channel",
                    "WebView Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "webview_channel")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(1, builder.build());
    }

    @JavascriptInterface
    public void showCall() {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:08123456789"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "Gagal membuka aplikasi Telepon", Toast.LENGTH_SHORT).show();
        }
    }

    @JavascriptInterface
    public void showWhatsApp() {
        try {
            String number = "628123456789";
            String url = "https://wa.me/" + number;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } catch (Exception e) {
            Toast.makeText(context, "WhatsApp tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
    }

    @JavascriptInterface
    public void showCamera() {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{android.Manifest.permission.CAMERA}, 102);
            Toast.makeText(context, "Meminta izin kamera...", Toast.LENGTH_SHORT).show();
        } else {
            try {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(context, "Gagal membuka kamera", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
