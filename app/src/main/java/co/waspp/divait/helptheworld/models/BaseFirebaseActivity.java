package co.waspp.divait.helptheworld.models;

import android.app.Dialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;

import co.waspp.divait.helptheworld.login.LoginActivity;

/**
 * Created by divait on 9/10/2016.
 *
 * The base activity to use firebase auth.
 */

public abstract class BaseFirebaseActivity extends AppCompatActivity {
    private static final int REQUEST_GOOGLE_PLAY_SERVICES = 0x001;

    protected FirebaseAuth fbAuth;
    private FirebaseAuth.AuthStateListener authListener;

    protected abstract void onAuthStateChangedActivity(FirebaseAuth firebaseAuth);

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        // Get Firebase Instance
        fbAuth = FirebaseAuth.getInstance();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                onAuthStateChangedActivity(firebaseAuth);
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        fbAuth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            fbAuth.removeAuthStateListener(authListener);
        }
    }

    protected void showPlayServicesErrorDialog(final int errorCode) {
        Dialog dialog = GoogleApiAvailability.getInstance()
                .getErrorDialog(
                        BaseFirebaseActivity.this,
                        errorCode,
                        REQUEST_GOOGLE_PLAY_SERVICES
                );

        dialog.show();
    }
}
