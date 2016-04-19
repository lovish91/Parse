package com.parsebeat.lovish.parse;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.parse.Parse;
import com.parse.ParseObject;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Lovish-THINKPAD on 15-01-2016.
 */
public class myapp extends Application {

    public static final String YOUR_APPLICATION_ID = "DWerVIAFySJErPCCm2hzwC8XvirWTNiFsUL0h76N";
    public static final String YOUR_CLIENT_KEY = "vJitQ1K2oM5dL9RqtxBbfmzplt4ocawlrapKAbvW";

    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());
        Parse.enableLocalDatastore(this);
        // Add your initialization code here
        ParseObject.registerSubclass(tracks.class);

        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);

        // ...
    }
}
