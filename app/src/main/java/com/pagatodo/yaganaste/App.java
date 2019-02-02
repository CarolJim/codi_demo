package com.pagatodo.yaganaste;

import android.content.Context;

import com.facebook.stetho.Stetho;
import com.pagatodo.network_manager.utils.RequestHeaders;
import com.pagatodo.yaganaste.commons.Preferences;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import static com.pagatodo.yaganaste.commons.ConstantsKt.CODI_SER;
import static com.pagatodo.yaganaste.commons.ConstantsKt.CODI_SER_LAST_UPDATE;

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
        // Increment Serialize CoDi
        long lastUpdateSer = Long.valueOf(preferences.loadData(CODI_SER_LAST_UPDATE, "0"));
        if (System.currentTimeMillis() - lastUpdateSer > (1000 * 60 * 60 * 24) || lastUpdateSer == 0) {
            long ser = Long.valueOf(preferences.loadData(CODI_SER, "0"));
            preferences.saveData(CODI_SER, (ser++) + "");
            preferences.saveData(CODI_SER_LAST_UPDATE, System.currentTimeMillis() + "");
        }
    }

    public static Context getContext() {
        return application.getApplicationContext();
    }

    public static Preferences getPreferences() {
        return preferences;
    }
}
