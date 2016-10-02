package co.waspp.divait.helptheworld.storage;

import android.content.Context;
import android.content.SharedPreferences;

import org.greenrobot.eventbus.EventBus;

import co.waspp.divait.helptheworld.events.UserStateChangeEvent;

/**
 * Created by divait on 1/10/2016.
 */

public class UserPreferences {
    private static final String PREFERENCES = "prefs";
    private static final String PREFERENCES_USER_STATE = "state";

    public static void changeUserState (Context context, boolean state) {
        // Get app preferences
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Add preferences
        editor.putBoolean(PREFERENCES_USER_STATE, state);

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
}
