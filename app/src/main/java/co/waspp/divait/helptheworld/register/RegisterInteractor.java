package co.waspp.divait.helptheworld.register;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import co.waspp.divait.helptheworld.R;
import co.waspp.divait.helptheworld.utils.Utils;

/**
 * Created by divait on 9/10/2016.
 *
 * Control all the behaviors in the registration.
 *
 */

class RegisterInteractor {

    private final Context context;
    private FirebaseAuth fbAuth;

    interface Callback {
        void onNameError(String msg);

        void onEmailError(String msg);

        void onPasswordError(String msg);

        void onNetworkConnectFailed();

        void onBeUserResolvableError(int errorCode);

        void onGooglePlayServicesFailed();

        void onAuthFailed(String msg);

        void onAuthSuccess();
    }

    RegisterInteractor(Context context, FirebaseAuth firebaseAuth) {
        this.context = context;
        if (firebaseAuth != null) {
            this.fbAuth = firebaseAuth;
        } else {
            throw new RuntimeException("La instancia de FirebaseAuth no puede ser null");
        }
    }

    void signup(String name, String email, String password, final RegisterInteractor.Callback callback) {
        // Check Logic
        boolean cName = isValidName(name, callback);
        boolean cEmail = isValidEmail(email, callback);
        boolean cPass = isValidPassword(password, callback);
        if (!(cEmail && cPass && cName)) {
            return;
        }

        // Check Network
        if (!Utils.isNetworkAvailable(context)) {
            callback.onNetworkConnectFailed();
            return;
        }

        // Check Google Play Service
        if (!isGooglePlayServicesAvailable(callback)) {
            return;
        }

        // Send Firebase Authentication
        signupUser(name, email, password, callback);

    }

    private boolean isValidPassword(String password, RegisterInteractor.Callback callback) {
        boolean isValid = true;
        if (TextUtils.isEmpty(password)) {
            callback.onPasswordError(context.getString(R.string.write_password));
            isValid = false;
        }

        return isValid;
    }

    private boolean isValidEmail(String email, RegisterInteractor.Callback callback) {
        boolean isValid = true;
        if (TextUtils.isEmpty(email)) {
            callback.onEmailError(context.getString(R.string.write_email));
            isValid = false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            callback.onEmailError(context.getString(R.string.no_valid_email));
            isValid = false;
        }

        return isValid;
    }

    private boolean isValidName(String name, RegisterInteractor.Callback callback) {
        boolean isValid = true;
        if (TextUtils.isEmpty(name)) {
            callback.onNameError(context.getString(R.string.write_name));
            isValid = false;
        }

        return isValid;
    }

    private boolean isGooglePlayServicesAvailable(RegisterInteractor.Callback callback) {
        int statusCode = GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(context);

        if (GoogleApiAvailability.getInstance().isUserResolvableError(statusCode)) {
            callback.onBeUserResolvableError(statusCode);
            return false;
        } else if (statusCode != ConnectionResult.SUCCESS) {
            callback.onGooglePlayServicesFailed();
            return false;
        }

        return true;
    }

    private void signupUser (String name, String email, String password, final RegisterInteractor.Callback callback) {
        fbAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            callback.onAuthFailed(task.getException().getMessage());
                        } else {
                            callback.onAuthSuccess();
                        }
                    }
                });
    }
}
