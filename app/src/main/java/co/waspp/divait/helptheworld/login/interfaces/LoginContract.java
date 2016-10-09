package co.waspp.divait.helptheworld.login.interfaces;

import co.waspp.divait.helptheworld.models.BasePresenter;
import co.waspp.divait.helptheworld.models.BaseView;

/**
 * Created by divait on 6/10/2016.
 *
 * Define all the methods to interact with the presenter for the login View.
 * MVP Interaction in Loggin.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void showProgress(boolean show);

        void setEmailError(String error);

        void setPasswordError(String error);

        void showLoginError(String msg);

        void showMainActivity();

        void showGooglePlayServicesDialog(int errorCode);

        void showGooglePlayServicesError();

        void showNetworkError();
    }

    interface Presenter extends BasePresenter {
        void attemptLogin(String email, String password);
    }
}
