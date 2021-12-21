package com.example.planner;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefManager {
    public static final String SP_JBALATULIS_APP = "praktikum_progmob_1";

    public static final String SP_ID = "id";

    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    SharedPreferences sp;

    public static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void saveSPString(Context context, String keySP, String value){
        SharedPreferences.Editor spEditor = getSharedPreference(context).edit();
        spEditor.putString(keySP, value);
        spEditor.apply();
    }

    public static void saveSPInt(Context context, String keySP, int value){
        SharedPreferences.Editor spEditor = getSharedPreference(context).edit();
        spEditor.putInt(keySP, value);
        spEditor.apply();
    }

    public static void saveSPBoolean(Context context, String keySP, boolean value){
        SharedPreferences.Editor spEditor = getSharedPreference(context).edit();
        spEditor.putBoolean(keySP, value);
        spEditor.apply();
    }

    public static int getSPId(Context context){
        return getSharedPreference(context).getInt(SP_ID,0);
    }
    public static void clearLoggedInUser (Context context){
        SharedPreferences.Editor spEditor = getSharedPreference(context).edit();
        spEditor.remove(SP_ID);
        spEditor.apply();
    }
}
