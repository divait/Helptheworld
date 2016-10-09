package co.waspp.divait.helptheworld.login;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;

import co.waspp.divait.helptheworld.R;
import co.waspp.divait.helptheworld.login.interfaces.LoginContract;
import co.waspp.divait.helptheworld.models.BaseFirebaseActivity;

/**
 * Created by divait on 3/10/2016.
 *
 * The Activity of registration.
 */

public class LoginActivity extends BaseFirebaseActivity implements LoginFragment.Callback {
    private LoginContract.Presenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_container);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, loginFragment)
                    .commit();
        }

        // Create interactor and presenter
        LoginInteractor loginInteractor = new LoginInteractor(getApplicationContext(), fbAuth);
        loginPresenter = new LoginPresenter(loginFragment, loginInteractor);
    }

    @Override
    public void onInvokeGooglePlayServices(int errorCode) {
        showPlayServicesErrorDialog(errorCode);
    }

    @Override
    protected void onAuthStateChangedActivity(FirebaseAuth firebaseAuth) {
        // When something refer to the user change
    }
}
