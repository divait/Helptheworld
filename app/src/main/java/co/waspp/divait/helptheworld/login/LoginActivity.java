package co.waspp.divait.helptheworld.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import co.waspp.divait.helptheworld.R;
import co.waspp.divait.helptheworld.activities.MainActivity;
import co.waspp.divait.helptheworld.activities.RegisterActivity;

/**
 * Created by divait on 3/10/2016.
 *
 * The Activity of registration.
 */

public class LoginActivity extends AppCompatActivity {
    // TODO Replace all the text for strings
    private static final int REQUEST_SIGNUP = 0x005;

    private Button loginButton;
    private TextView signUpText;
    private EditText emailEdit;
    private EditText passEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getAllViews();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                onLoginSuccess();
            }
        }
    }

    private void getAllViews() {
        loginButton = (Button) findViewById(R.id.btn_login);
        signUpText = (TextView) findViewById(R.id.link_signup);
        emailEdit = (EditText) findViewById(R.id.input_email);
        passEdit = (EditText) findViewById(R.id.input_password);
    }

    private void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                android.R.style.Theme_DeviceDefault_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = emailEdit.getText().toString();
        String password = passEdit.getText().toString();

        // TODO Connect to the server
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }

    private void signup() {
        // Start the Signup activity
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailEdit.getText().toString();
        String password = passEdit.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEdit.setError("enter a valid email address");
            valid = false;
        } else {
            emailEdit.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passEdit.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passEdit.setError(null);
        }

        return valid;
    }
}
