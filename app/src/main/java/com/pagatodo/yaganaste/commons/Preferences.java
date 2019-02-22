package com.pagatodo.yaganaste.commons;

import android.content.Context;
import android.content.SharedPreferences;

import com.pagatodo.yaganaste.R;

public class Preferences {
    private SharedPreferences preferences;
    private Context context;

    public Preferences(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(context.getString(R.string.prf_richard_parameters),
                Context.MODE_PRIVATE);
    }

    public void saveData(int key, String data) {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putString(context.getString(key), data);
        editor.commit();
    }

    public void saveData(String key, String data) {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putString(key, data);
        editor.commit();
    }

    public void saveDataBool(String key, boolean data) {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putBoolean(key, data);
        editor.commit();
    }

    public boolean containsData(String key) {
        return this.preferences.getBoolean(key, false);
    }

    public boolean loadDataBoolean(String key, boolean defValue) {
        return this.preferences.getBoolean(key, defValue);
    }

    public String loadData(String key) {
        return this.preferences.getString(key, "");
    }

    public String loadData(String key, String def) {
        return this.preferences.getString(key, def);
    }

    public String getDataString(int key) {
        return this.preferences.getString(context.getString(key), "");
    }

    public void clearPreferences() {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.clear();
        editor.commit();
        return;
    }

    public void clearPreference(String key) {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.remove(key);
        editor.commit();
        return;
    }

    public void saveDataInt(String key, int data) {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putInt(key, data);
        editor.commit();
    }

    public int loadDataInt(String key) {
        String pref = this.preferences.getString(key, "");
        return Integer.parseInt(pref);
        //return this.preferences.getInt(key, -1);
    }
}
