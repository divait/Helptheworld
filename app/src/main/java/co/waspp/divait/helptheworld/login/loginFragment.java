package co.waspp.divait.helptheworld.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import co.waspp.divait.helptheworld.R;
import co.waspp.divait.helptheworld.login.interfaces.LoginContract;
import co.waspp.divait.helptheworld.main.MainActivity;

/**
 * Created by divait on 6/10/2016.
 *
 * The View Controller for the login.
 *
 */

public class LoginFragment extends Fragment implements LoginContract.View {

    private LoginFragmentCallback callback;

    private TextInputEditText editEmail;
    private TextInputEditText editPassword;
    private Button btnSignIn;
    private TextView linkSignUp;
    private View viewLoginForm;
    private View viewLoginProgress;
    private TextInputLayout errorEmail;
    private TextInputLayout errorPassword;

    interface LoginFragmentCallback {
        void onInvokeGooglePlayServices(int errorCode);
    }

    private LoginContract.Presenter loginPresenter;

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginFragmentCallback) {
            callback = (LoginFragmentCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "You must implement PSCallback.");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Get Arguments if are some
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        viewLoginForm = root.findViewById(R.id.login_form);
        viewLoginProgress = root.findViewById(R.id.login_progress);

        editEmail = (TextInputEditText) root.findViewById(R.id.edit_email);
        editPassword = (TextInputEditText) root.findViewById(R.id.edit_pass);
        errorEmail = (TextInputLayout) root.findViewById(R.id.input_email);
        errorPassword = (TextInputLayout) root.findViewById(R.id.input_pass);

        btnSignIn = (Button) root.findViewById(R.id.btn_log_in);
        linkSignUp = (TextView) root.findViewById(R.id.link_sign_up);

        // Events
        editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                errorEmail.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                errorPassword.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        linkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignUpActivity();
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        loginPresenter.start();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void showProgress(boolean show) {
        viewLoginForm.setVisibility(show ? View.GONE : View.VISIBLE);
        viewLoginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setEmailError(String error) {
        errorEmail.setError(error);
    }

    @Override
    public void setPasswordError(String error) {
        errorPassword.setError(error);
    }

    @Override
    public void showLoginError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMainActivity() {
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }

    @Override
    public void showGooglePlayServicesDialog(int errorCode) {
        callback.onInvokeGooglePlayServices(errorCode);
    }

    @Override
    public void showGooglePlayServicesError() {
        Toast.makeText(getActivity(),
                getString(R.string.error_need_play_services), Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(getActivity(),
                getString(R.string.error_no_internet), Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        if (presenter != null) {
            loginPresenter = presenter;
        } else {
            throw new RuntimeException("Presenter can't be null.");
        }
    }

    @Override
    public void showSignUpActivity() {
        // startActivityForResult(new Intent(getActivity(), MainActivity.class));
    }

    private void attemptLogin() {
        loginPresenter.attemptLogin(
                editEmail.getText().toString(),
                editPassword.getText().toString());
    }
}
