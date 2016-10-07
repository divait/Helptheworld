package co.waspp.divait.helptheworld.login;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;

import co.waspp.divait.helptheworld.R;
import co.waspp.divait.helptheworld.login.interfaces.LoginContract;

/**
 * Created by divait on 3/10/2016.
 *
 * The Activity of registration.
 */

public class LoginActivity extends AppCompatActivity implements LoginFragment.LoginFragmentCallback {
    public static final int REQUEST_SIGNUP = 0x005;

    private static final int REQUEST_GOOGLE_PLAY_SERVICES = 0x001;

    private FirebaseAuth fbAuth;
    private FirebaseAuth.AuthStateListener authListener;
    private LoginContract.Presenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.login_container);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.login_container, loginFragment)
                    .commit();
        }

        // Get Firebase Instance
        fbAuth = FirebaseAuth.getInstance();

        // Create interactor and presenter
        LoginInteractor loginInteractor = new LoginInteractor(getApplicationContext(), fbAuth);
        loginPresenter = new LoginPresenter(loginFragment, loginInteractor);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // When something refer to the user change
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

    @Override
    public void onInvokeGooglePlayServices(int errorCode) {
        showPlayServicesErrorDialog(errorCode);
    }


    void showPlayServicesErrorDialog(
            final int errorCode) {
        Dialog dialog = GoogleApiAvailability.getInstance()

                .getErrorDialog(
                        LoginActivity.this,
                        errorCode,
                        REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }
}
