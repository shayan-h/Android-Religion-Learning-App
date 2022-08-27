package com.example.learnislam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText eEmail;
    private EditText ePassword;
    private Button eLogin;
    private TextView eAttempts;
    private TextView eRegister;
    private TextView forgotPassword;

    private FirebaseAuth mAuth;

    private int counter = 5;

    // boolean isValid = false;

    // public Credentials cred;

    // SharedPreferences sp;
    // SharedPreferences.Editor spE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eEmail = findViewById(R.id.etName);
        ePassword = findViewById(R.id.etPassword);
        eLogin = findViewById(R.id.btnLogin);
        eAttempts = findViewById(R.id.tvAttempts);
        eRegister = findViewById(R.id.tvRegister);
        forgotPassword = findViewById(R.id.tvForgot);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        /*
        if (user != null) {
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
        */


        // Shared Preferences ----------------------------------------------------------------------
        /*
        cred = new Credentials();

        sp = getApplicationContext().getSharedPreferences("CredentialsDB", MODE_PRIVATE);
        spE = sp.edit();

        if (sp != null) {
            Map<String, ?> preferencesMap = sp.getAll();
            if (preferencesMap.size() != 0) {
                cred.loadCreds(preferencesMap);
            }


            String savedUsername = sp.getString("LastSavedUsername", "");
            String savedPassword = sp.getString("LastSavedPassword", "");


            if (sp.getBoolean("RememberMeCheckBox", false)) {
                eUsername.setText(savedUsername);
                ePassword.setText(savedPassword);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                eRememberMe.setChecked(true);
            }

        }

        eRememberMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spE.putBoolean("RememberMeCheckBox", eRememberMe.isChecked());
                spE.apply();
            }
        });
        */
        // Shared Preferences ----------------------------------------------------------------------

        eRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

        eAttempts.setText("");

        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = eEmail.getText().toString();
                String inputPassword = ePassword.getText().toString();

                if (inputEmail.isEmpty() || inputPassword.isEmpty()) {

                    Toast.makeText(LoginActivity.this, "Missing Username or Password!", Toast.LENGTH_SHORT).show();
                    eAttempts.setText("Missing Username or Password!");

                } else {

                    mAuth.signInWithEmailAndPassword(inputEmail, inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // eAttempts.setText("Welcome to LearnIslam!");
                                checkEmailVerified();
                            } else {
                                counter--;
                                eAttempts.setText("Attempts Left:" + counter);
                                if (counter == 0) {
                                    eLogin.setEnabled(false);
                                    eAttempts.setText("Login Failed Too Many Times, Try Again Later");
                                }
                            }
                        }
                    });

                    /*
                    if (!isValid) {
                        counter--;

                        Toast.makeText(LoginActivity.this, "Incorrect Username or Password!", Toast.LENGTH_SHORT).show();

                        eAttempts.setText("Attempts Left:" + counter);

                        if (counter == 0) {
                            eLogin.setEnabled(false);
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Welcome to LearnIslam!", Toast.LENGTH_SHORT).show();

                        eAttempts.setText("Welcome to LearnIslam!");

                        spE.putString("LastSavedUsername", inputUsername);
                        spE.putString("LastSavedPassword", inputPassword);

                        // Go to new activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                     */
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, PasswordActivity.class));
            }
        });
    }

    private void checkEmailVerified() {
        FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();
        boolean emailCheck = firebaseUser.isEmailVerified();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));

        /*
        if (emailCheck) {
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } else {
            eAttempts.setText("Verify your email!");
            mAuth.signOut();
        }
         */
    }
}