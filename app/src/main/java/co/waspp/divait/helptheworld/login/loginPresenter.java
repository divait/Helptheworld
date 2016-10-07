package co.waspp.divait.helptheworld.login;

import android.support.annotation.NonNull;

import co.waspp.divait.helptheworld.login.interfaces.LoginContract;

/**
 * Created by divait on 6/10/2016.
 */

public class LoginPresenter implements LoginContract.Presenter, LoginInteractor.LoginInteractorCallback {

    private final LoginContract.View loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenter (@NonNull LoginContract.View loginView, @NonNull LoginInteractor loginInteractor) {
        this.loginView = loginView;
        loginView.setPresenter(this);
        this.loginInteractor = loginInteractor;
    }

    @Override
    public void start() {
        // Check if the user is already logged
    }

    @Override
    public void attemptLogin(String email, String password) {
        loginView.showProgress(true);
        loginInteractor.login(email, password, this);
    }

    @Override
    public void onEmailError(String msg) {
        loginView.showProgress(false);
        loginView.setEmailError(msg);
    }

    @Override
    public void onPasswordError(String msg) {
        loginView.showProgress(false);
        loginView.setPasswordError(msg);
    }

    @Override
    public void onNetworkConnectFailed() {
        loginView.showProgress(false);
        loginView.showNetworkError();
    }

    @Override
    public void onBeUserResolvableError(int errorCode) {
        loginView.showProgress(false);
        loginView.showGooglePlayServicesDialog(errorCode);
    }

    @Override
    public void onGooglePlayServicesFailed() {
        loginView.showGooglePlayServicesError();
    }

    @Override
    public void onAuthFailed(String msg) {
        loginView.showProgress(false);
        loginView.showLoginError(msg);
    }

    @Override
    public void onAuthSuccess() {
        loginView.showMainActivity();
    }
}
