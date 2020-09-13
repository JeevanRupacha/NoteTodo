package com.example.todoandnote.PrefereceUtil;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceData {

    private Context mContext;

    public PreferenceData(Context context)
    {
        mContext = context;
    }

    public void setSchedulerPreference(String key, long value)
    {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("NoteTodo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }


    public long getSchedulerPreference(String key)
    {

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("NoteTodo", Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, System.currentTimeMillis());
    }

}
