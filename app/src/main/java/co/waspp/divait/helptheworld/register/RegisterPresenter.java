package co.waspp.divait.helptheworld.register;

import android.support.annotation.NonNull;

import co.waspp.divait.helptheworld.register.interfaces.RegisterContract;

/**
 * Created by divait on 9/10/2016.
 *
 * The presenter for the Register View.
 */

class RegisterPresenter implements RegisterContract.Presenter, RegisterInteractor.Callback {

    private final RegisterContract.View registerView;
    private RegisterInteractor registerInteractor;

    RegisterPresenter(@NonNull RegisterContract.View registerView, @NonNull RegisterInteractor registerInteractor) {
        this.registerView = registerView;
        registerView.setRegisterPresenter(this);
        this.registerInteractor = registerInteractor;
    }

    @Override
    public void start() {
        // Check if the user is already logged
    }

    @Override
    public void attemptRegister(String name, String email, String password) {
        registerView.showProgress(true);
        registerInteractor.signup(name, email, password, this);
    }

    @Override
    public void onNameError(String msg) {
        registerView.showProgress(false);
        registerView.setNameError(msg);
    }

    @Override
    public void onEmailError(String msg) {
        registerView.showProgress(false);
        registerView.setEmailError(msg);
    }

    @Override
    public void onPasswordError(String msg) {
        registerView.showProgress(false);
        registerView.setPasswordError(msg);
    }

    @Override
    public void onNetworkConnectFailed() {
        registerView.showProgress(false);
        registerView.showNetworkError();
    }

    @Override
    public void onBeUserResolvableError(int errorCode) {
        registerView.showProgress(false);
        registerView.showGooglePlayServicesDialog(errorCode);
    }

    @Override
    public void onGooglePlayServicesFailed() {
        registerView.showGooglePlayServicesError();
    }

    @Override
    public void onAuthFailed(String msg) {
        registerView.showProgress(false);
        registerView.showRegisterError(msg);
    }

    @Override
    public void onAuthSuccess() {
        registerView.showMainActivity();
    }
}
