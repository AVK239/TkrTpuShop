package com.tkr.tkrtpushop.Model;

import android.app.Application;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Инициализация ThreeTenABP


        AndroidThreeTen.init(this);
    }
}
