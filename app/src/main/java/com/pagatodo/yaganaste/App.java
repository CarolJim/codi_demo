package com.pagatodo.yaganaste;

import android.content.Context;

import com.facebook.stetho.Stetho;
import com.google.firebase.FirebaseApp;
import com.pagatodo.network_manager.utils.RequestHeaders;
import com.pagatodo.yaganaste.commons.Preferences;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class App extends MultiDexApplication {

    private static App application;
    private static Preferences preferences;
    private final static String nameDBUsers = "https://odin-mx-users.firebaseio.com/";
    private final static String nameDBDefault = "https://odin-dd5ba.firebaseio.com/";

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        // Multicompatibilidad de la App
        MultiDex.install(this);
        // Preferences
        preferences = new Preferences(this);
        RequestHeaders.initHeaders(this);
        // Configuraciones de la App
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }

    public static Context getContext() {
        return application.getApplicationContext();
    }

    public static Preferences getPreferences() {
        return preferences;
    }
}
