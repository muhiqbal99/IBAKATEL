package com.ta.iqbal.ibakatel.tes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ta.iqbal.ibakatel.R;
import com.ta.iqbal.ibakatel.identifikasi.QRCodeScanActivity;

public class ActivityTes extends AppCompatActivity {

    Button btnScanQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tes);

        btnScanQRCode = (Button) findViewById(R.id.btnScanQRCode);
        btnScanQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityTes.this, QRCodeScanActivity.class);
                startActivity(intent);
            }
        });
    }
}