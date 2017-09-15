package com.example.kraputis.meginam;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
/**
 * Created by Justineso on 2016.12.07.
 */

public class MultidexApplication extends Application{

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
