package com.j222102450.login;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ForexViewHolder extends RecyclerView.ViewHolder {
    TextView kodeTextView, kursTextView, namaTextView;
    public ForexViewHolder(@NonNull View itemView) {
        super(itemView);

        kodeTextView = itemView.findViewById(R.id.kodeTextView);
        namaTextView = itemView.findViewById(R.id.namaTextView);
        kursTextView = itemView.findViewById(R.id.kursTextView);
    }
}
