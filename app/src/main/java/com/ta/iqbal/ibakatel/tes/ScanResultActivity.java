package com.ta.iqbal.ibakatel.tes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ta.iqbal.ibakatel.R;

public class ScanResultActivity extends AppCompatActivity {

    TextView txtScanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tes_result);

        txtScanResult = (TextView) findViewById(R.id.txtScanResult); /* Find TextView and initialize it to object */

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String scanResult = extras.getString("ResultText"); /* Retrieving text of QR Code */
            txtScanResult.setText(scanResult);
        }
    }
}