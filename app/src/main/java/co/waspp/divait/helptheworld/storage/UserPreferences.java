package co.waspp.divait.helptheworld.storage;

import android.content.Context;
import android.content.SharedPreferences;

import org.greenrobot.eventbus.EventBus;

import co.waspp.divait.helptheworld.events.UserStateChangeEvent;

/**
 * Created by divait on 1/10/2016.
 *
 * A class to manage user preferences that are store in the memory.
 */

public class UserPreferences {
    private static final String PREFERENCES = "prefs";
    private static final String PREFERENCES_USER_STATE = "state";
    private static final String PREFERENCES_USER_EMAIL = "email";
    private static final String PREFERENCES_USER_NAME = "name";

    private static void changeUserState (Context context, String name, String email, boolean state) {
        // Get app preferences
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Add preferences
        editor.putBoolean(PREFERENCES_USER_STATE, state);

        if(name != null){
            editor.putString(PREFERENCES_USER_NAME, name);
        }

        if(email != null){
            editor.putString(PREFERENCES_USER_EMAIL, email);
        }

        // Commit the edits!
        editor.apply();

        // Notify the event
        EventBus.getDefault().post(new UserStateChangeEvent(state));
    }

    public static boolean getUserState (Context context) {
        // Get app preferences
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        return preferences.getBoolean(PREFERENCES_USER_STATE, false);
    }

    public static void login(Context context, String name, String email) {
        changeUserState(context, name, email, true);
    }

    public static void logout (Context context) {
        changeUserState(context, null, null, false);
    }
}
