package com.bezirk.candidcamera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.android.BezirkMiddleware;

public class MainActivity extends AppCompatActivity {

    private Bezirk bezirk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BezirkMiddleware.initialize(this);
        bezirk = BezirkMiddleware.registerZirk("Candid Camera Zirk");
    }
}
