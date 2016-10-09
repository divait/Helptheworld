package co.waspp.divait.helptheworld.register;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;

import co.waspp.divait.helptheworld.R;
import co.waspp.divait.helptheworld.login.LoginActivity;
import co.waspp.divait.helptheworld.login.LoginFragment;
import co.waspp.divait.helptheworld.login.interfaces.LoginContract;
import co.waspp.divait.helptheworld.models.BaseFirebaseActivity;
import co.waspp.divait.helptheworld.register.interfaces.RegisterContract;
import co.waspp.divait.helptheworld.storage.UserPreferences;

/**
 * Created by divait on 3/10/2016.
 *
 * The Activity of registration.
 */

public class RegisterActivity extends BaseFirebaseActivity implements RegisterFragment.Callback {
    private RegisterContract.Presenter registerPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        RegisterFragment registerFragment = (RegisterFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_container);
        if (registerFragment == null) {
            registerFragment = RegisterFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, registerFragment)
                    .commit();
        }

        // Create interactor and presenter
        RegisterInteractor registerInteractor = new RegisterInteractor(getApplicationContext(), fbAuth);
        registerPresenter = new RegisterPresenter(registerFragment, registerInteractor);
    }

    @Override
    public void onInvokeGooglePlayServices(int errorCode) {
        showPlayServicesErrorDialog(errorCode);
    }

    @Override
    protected void onAuthStateChangedActivity(FirebaseAuth firebaseAuth) {
        // When something refer to the user change
        if (firebaseAuth.getCurrentUser() != null) {
            UserPreferences.login(getApplicationContext(), null, firebaseAuth.getCurrentUser().getEmail());
        } else {
            UserPreferences.logout(getApplicationContext());
        }
    }
}
