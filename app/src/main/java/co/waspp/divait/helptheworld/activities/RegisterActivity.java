package co.waspp.divait.helptheworld.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import co.waspp.divait.helptheworld.R;

/**
 * Created by divait on 3/10/2016.
 *
 * The Activity of registration.
 */

public class RegisterActivity extends AppCompatActivity {
    private Button signupButton;
    private TextView loginText;
    private EditText nameEdit;
    private EditText emailEdit;
    private EditText passEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signupButton = (Button) findViewById(R.id.btn_signup);
        loginText = (TextView) findViewById(R.id.link_login);
        nameEdit = (EditText) findViewById(R.id.input_name);
        emailEdit = (EditText) findViewById(R.id.input_email);
        passEdit = (EditText) findViewById(R.id.input_password);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    private void signup() {

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                android.R.style.Theme_DeviceDefault_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = nameEdit.getText().toString();
        String email = emailEdit.getText().toString();
        String password = passEdit.getText().toString();

        // TODO Connect to the server
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }

    public boolean validate() {
        boolean valid = true;

        String name = nameEdit.getText().toString();
        String email = emailEdit.getText().toString();
        String password = passEdit.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameEdit.setError("at least 3 characters");
            valid = false;
        } else {
            nameEdit.setError(null);
        }

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
