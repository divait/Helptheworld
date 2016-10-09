package co.waspp.divait.helptheworld.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import co.waspp.divait.helptheworld.R;
import co.waspp.divait.helptheworld.login.LoginActivity;
import co.waspp.divait.helptheworld.main.MainActivity;
import co.waspp.divait.helptheworld.register.interfaces.RegisterContract;

/**
 * Created by divait on 8/10/2016.
 *
 * View manager of registration.
 */

public class RegisterFragment extends Fragment implements RegisterContract.View {
    private RegisterFragment.Callback callback;

    private TextInputEditText editName;
    private TextInputEditText editEmail;
    private TextInputEditText editPassword;
    private Button btnSignup;
    private TextView linkLogin;
    private View viewLoginForm;
    private View viewLoginProgress;
    private TextInputLayout errorName;
    private TextInputLayout errorEmail;
    private TextInputLayout errorPassword;

    interface Callback {
        void onInvokeGooglePlayServices(int errorCode);
    }

    private RegisterContract.Presenter registerPresenter;

    public RegisterFragment (){
    }

    public static RegisterFragment newInstance() {
        Bundle args = new Bundle();

        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RegisterFragment.Callback) {
            callback = (RegisterFragment.Callback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "You must implement Callback.");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Get Arguments if are some
            Log.d("Data dev", "Have arguments");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        viewLoginForm = root.findViewById(R.id.login_form);
        viewLoginProgress = root.findViewById(R.id.login_progress);

        editName = (TextInputEditText) root.findViewById(R.id.input_name);
        editEmail = (TextInputEditText) root.findViewById(R.id.input_email);
        editPassword = (TextInputEditText) root.findViewById(R.id.input_password);
        errorName = (TextInputLayout) root.findViewById(R.id.error_name);
        errorEmail = (TextInputLayout) root.findViewById(R.id.error_email);
        errorPassword = (TextInputLayout) root.findViewById(R.id.error_pass);

        btnSignup = (Button) root.findViewById(R.id.btn_signup);
        linkLogin = (TextView) root.findViewById(R.id.link_login);

        // Events
        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                errorName.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
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

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignup();
            }
        });

        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoginActivity();
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        registerPresenter.start();
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
    public void setNameError(String error) {
        errorName.setError(error);
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
    public void showRegisterError(String msg) {
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

    public void setRegisterPresenter(RegisterContract.Presenter presenter) {
        if (presenter != null) {
            registerPresenter = presenter;
        } else {
            throw new RuntimeException("Presenter can't be null.");
        }
    }

    public void showLoginActivity() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    public void  attemptSignup() {
        registerPresenter.attemptRegister(
                editName.getText().toString(),
                editEmail.getText().toString(),
                editPassword.getText().toString()
        );
    }
}
