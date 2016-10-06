package co.waspp.divait.helptheworld.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import co.waspp.divait.helptheworld.R;
import co.waspp.divait.helptheworld.login.LoginActivity;
import co.waspp.divait.helptheworld.register.RegisterActivity;

/**
 * Created by divait on 3/10/2016.
 *
 * The main fragment when your  guest.
 */

public class MainGuestFragment extends Fragment {
    private static final int REQUEST_SIGNUP = 0x005;
    private static final int REQUEST_LOGIN = 0x006;

    private Button signupButton;
    private Button loginButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_guest_main, container, false);
        signupButton = (Button) rootView.findViewById(R.id.button_register);
        loginButton = (Button) rootView.findViewById(R.id.button_log_in);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    private void signup() {
        // Start the Signup activity
        Intent intent = new Intent(getActivity().getApplicationContext(), RegisterActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
    }

    private void login() {
        // Start the Signup activity
        Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
        startActivityForResult(intent, REQUEST_LOGIN);
    }
}
