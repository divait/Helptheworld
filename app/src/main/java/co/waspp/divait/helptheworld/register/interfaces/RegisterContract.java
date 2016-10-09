package co.waspp.divait.helptheworld.register.interfaces;

import co.waspp.divait.helptheworld.login.interfaces.LoginContract;
import co.waspp.divait.helptheworld.models.BasePresenter;
import co.waspp.divait.helptheworld.models.BaseView;

/**
 * Created by divait on 8/10/2016.
 *
 * Contract of methods can be call for the view and the presenter
 */

public class RegisterContract {

    public interface View extends BaseView<RegisterContract.Presenter> {

        void showProgress(boolean show);

        void setNameError(String error);

        void setEmailError(String error);

        void setPasswordError(String error);

        void showRegisterError(String msg);

        void showMainActivity();

        void showGooglePlayServicesDialog(int errorCode);

        void showGooglePlayServicesError();

        void showNetworkError();
    }

    public interface Presenter extends BasePresenter {
        void attemptRegister(String name, String email, String password);
    }
}
