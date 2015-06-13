package com.fzv.glucowatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class confirmation_activity extends ActionBarActivity {

    NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_activity);

        notificationManager = NotificationManagerCompat.from(this);

        Intent intent = getIntent();
        TextView textView = (TextView) findViewById(R.id.tvContentText);
        textView.setText(intent.getStringExtra("notificationContent"));

        Button btnCofnirm = (Button) findViewById(R.id.btnConfirm);
        btnCofnirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationManager.cancelAll();
                finish();
            }
        });

        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



}
