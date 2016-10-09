package co.waspp.divait.helptheworld.login;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
 * Created by divait on 6/10/2016.
 *
 * The interactor that manege the behavior of the login View.
 */

class LoginInteractor {

    private final Context context;
    private FirebaseAuth fbAuth;

    interface Callback {

        void onEmailError(String msg);

        void onPasswordError(String msg);

        void onNetworkConnectFailed();

        void onBeUserResolvableError(int errorCode);

        void onGooglePlayServicesFailed();

        void onAuthFailed(String msg);

        void onAuthSuccess();
    }

    LoginInteractor(Context context, FirebaseAuth firebaseAuth) {
        this.context = context;
        if (firebaseAuth != null) {
            this.fbAuth = firebaseAuth;
        } else {
            throw new RuntimeException("La instancia de FirebaseAuth no puede ser null");
        }
    }

    void login(String email, String password, final Callback callback) {
        // Check Logic
        boolean cEmail = isValidEmail(email, callback);
        boolean cPass = isValidPassword(password, callback);
        if (!(cEmail && cPass)) {
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
        signInUser(email, password, callback);

    }

    private boolean isValidPassword(String password, Callback callback) {
        boolean isValid = true;
        if (TextUtils.isEmpty(password)) {
            callback.onPasswordError(context.getString(R.string.write_password));
            isValid = false;
        }

        return isValid;
    }

    private boolean isValidEmail(String email, Callback callback) {
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

    private boolean isGooglePlayServicesAvailable(Callback callback) {
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

    private void signInUser(String email, String password, final Callback callback) {
        fbAuth.signInWithEmailAndPassword(email, password)
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
