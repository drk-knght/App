package com.example.androidapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.androidapplication.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivitydel extends AppCompatActivity {

    int MY_PERMISSIONS_REQUEST_CAMERA=0;

    ZXingScannerView scannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code_activitydel);
        setContentView(scannerView);
    }
    /*@Override
    public void handleResult(Result result) {

        deleteItemsActivity.resultdeleteview.setText(result.getText());

        onBackPressed();
    }*/

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        scannerView.setResultHandler(this);
//        scannerView.startCamera();
//    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        }
        scannerView.setResultHandler((ZXingScannerView.ResultHandler) this);
        scannerView.startCamera();
    }
}