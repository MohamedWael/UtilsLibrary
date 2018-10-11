package com.blogspot.mowael.utilslibrary.storage;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;
import static com.blogspot.mowael.utilslibrary.Config.SHARED_PREFERENCES_NAME;

/**
 * Created by moham on 2/1/2017.
 */
public class SharedPreferencesManager {
    private static SharedPreferencesManager ourInstance;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    private SharedPreferencesManager() {
    }

    public static SharedPreferencesManager getInstance() {
        if (ourInstance == null) ourInstance = new SharedPreferencesManager();
        return ourInstance;
    }

    /**
     * initialize shared preferences with custom name
     *
     * @param mContext              application context
     * @param sharedPreferencesName shared preferences name
     * @return @return SharedPreferences instance
     */
    public SharedPreferences initSharedPreferences(Context mContext, String sharedPreferencesName) {
        initEditor(mContext);
        return prefs = mContext.getSharedPreferences(sharedPreferencesName, MODE_PRIVATE);
    }

    /**
     * initialize shared preferences with default name {@link SHARED_PREFERENCES_NAME}
     *
     * @param mContext application context
     * @return SharedPreferences instance
     */
    public SharedPreferences initSharedPreferences(Context mContext) {
        return initSharedPreferences(mContext, SHARED_PREFERENCES_NAME);
    }

    /**
     * initialize shared preferences editor
     *
     * @param mContext application context
     * @return
     */
    public SharedPreferences.Editor initEditor(Context mContext) {
        if (prefs == null) {
            initSharedPreferences(mContext);
        }
        return editor = prefs.edit();
    }

    public SharedPreferences.Editor getEditor() {
        if (editor == null) {
            throw new NullPointerException("you have to initialize SharedPreferencesManager.initEditor(Context mContext)");
        }
        return editor;
    }

    public SharedPreferences getPrefs() {
        if (prefs == null) {
            throw new NullPointerException("you have to initialize SharedPreferencesManager.initSharedPreferences(Context mContext)");
        }
        return prefs;
    }

    /**
     * Returns true if the new values were successfully written to persistent storage
     * or returns false if editor is null or the values weren't written to persistent storage
     */
    public boolean closeEditor() {
        if (editor != null) return editor.commit();
        return false;
    }

}
