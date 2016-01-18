package com.parsebeat.lovish.parse;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Lovish-THINKPAD on 15-01-2016.
 */
public class myapp extends Application {

    public static final String YOUR_APPLICATION_ID = "DWerVIAFySJErPCCm2hzwC8XvirWTNiFsUL0h76N";
    public static final String YOUR_CLIENT_KEY = "vJitQ1K2oM5dL9RqtxBbfmzplt4ocawlrapKAbvW";

    @Override
    public void onCreate() {
        super.onCreate();

        // Add your initialization code here
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);

        // ...
    }
}
