package co.waspp.divait.helptheworld.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;

import co.waspp.divait.helptheworld.R;
import co.waspp.divait.helptheworld.login.interfaces.LoginContract;
import co.waspp.divait.helptheworld.main.MainActivity;
import co.waspp.divait.helptheworld.register.RegisterActivity;

/**
 * Created by divait on 3/10/2016.
 *
 * The Activity of registration.
 */

public class LoginActivity extends AppCompatActivity implements LoginFragment.LoginFragmentCallback {
    private static final int REQUEST_GOOGLE_PLAY_SERVICES = 0x001;

    private FirebaseAuth fbAuth;
    private FirebaseAuth.AuthStateListener authListener;
    private LoginContract.Presenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

    /*
    // TODO Replace all the text for strings
    private static final int REQUEST_SIGNUP = 0x005;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                onLoginSuccess();
            }
        }
    }

    private void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }

        String email = emailEdit.getText().toString();
        String password = passEdit.getText().toString();

        // TODO Connect to the server
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }

    private void signup() {
        // Start the Signup activity
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailEdit.getText().toString();
        String password = passEdit.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEdit.setError("enter a valid email address");
            valid = false;
        } else {
            emailEdit.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passEdit.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passEdit.setError(null);
        }

        return valid;
    }
    */
}
