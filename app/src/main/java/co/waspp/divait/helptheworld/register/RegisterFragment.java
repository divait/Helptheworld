package co.waspp.divait.helptheworld.register;

import android.content.Context;
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

import co.waspp.divait.helptheworld.R;
import co.waspp.divait.helptheworld.register.interfaces.RegisterContract;

/**
 * Created by divait on 8/10/2016.
 *
 * View manager of registration.
 */

public class RegisterFragment extends Fragment {
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

    private RegisterContract.Presenter presenter;

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
        errorEmail = (TextInputLayout) root.findViewById(R.id.error_name);
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

        return root;
    }
}
