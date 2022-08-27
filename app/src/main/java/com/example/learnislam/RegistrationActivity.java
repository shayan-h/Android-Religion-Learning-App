package com.example.learnislam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;
import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {

    private EditText eRegName, eRegEmail, eUsername, ePassword, eConfPassword;
    private Button eRegBtn;
    private TextView eRror;
    private TextView eRegPrev;

    String regEmail, regName;

    private FirebaseAuth mAuth;

    // public Credentials cred;

    // SharedPreferences sp;
    // SharedPreferences.Editor spE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        eRror.setText("");

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
        }
        */
        // Shared Preferences ----------------------------------------------------------------------
        eRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                regName = eRegName.getText().toString();
                regEmail = eRegEmail.getText().toString().trim();
                String regUsername = eUsername.getText().toString();
                String regPassword = ePassword.getText().toString().trim();
                String regConfPassword = eConfPassword.getText().toString();

                if (validate(regName, regEmail, regUsername, regPassword, regConfPassword)) {

                    mAuth.createUserWithEmailAndPassword(regEmail, regPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // sendEmailVerification();
                                sendUserData();
                                eRror.setText("Registration Successful! User data upload complete.");
                                finish();
                                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                            } else {
                                Log.w("createUserWithEmail:failure", task.getException());
                                eRror.setText("Registration Failed - Password must be at least 6 characters");
                            }
                        }
                    });
                    // Shared Preferences ----------------------------------------------------------
                    /*
                    if (cred.checkUsername(regUsername)) {
                        eRror.setText("Username is already taken!");
                    } else {
                        cred.addCreds(regUsername, regPassword);

                        // Store the credentials
                        spE.putString(regUsername, regPassword);

                        spE.putString("LastSavedUsername", regUsername);
                        spE.putString("LastSavedPassword", regPassword);

                        spE.apply(); // Commits changes and adds them to the file

                        eRror.setText("Welcome to LearnIslam!");
                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                    } */
                    // Shared Preferences ----------------------------------------------------------
                }

            }
        });

        // If already registered
        eRegPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
    }

    private void setupUIViews() {
        eRegName = findViewById(R.id.etRegName);
        eRegEmail = findViewById(R.id.etRegEmail);
        eUsername = findViewById(R.id.etRegUsername);
        ePassword = findViewById(R.id.etRegPassword);
        eConfPassword = findViewById(R.id.etRegConfPassword);
        eRegBtn = findViewById(R.id.btnReg);
        eRror = findViewById(R.id.tvError);
        eRegPrev = findViewById(R.id.tvRegisteredPrev);
    }

    private boolean validate(String name, String email, String username, String password, String confPassword) {
        if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confPassword.isEmpty()) {
            eRror.setText("Missing Information!");
            return false;
        }
        else if (!password.equals(confPassword)) {
            eRror.setText("Passwords do not match!");
            return false;
        }

        return true;
    }

    private void sendEmailVerification() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        sendUserData();
                        eRror.setText("Registration Successful! Verification mail has been sent to your email.");
                        mAuth.signOut();
                        finish();
                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                    } else {
                        eRror.setText("Verification mail failed to send. Try again later.");
                    }
                }
            });
        }
    }

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(Objects.requireNonNull(mAuth.getUid()));
        UserProfile userProfile = new UserProfile(regEmail, regName);
        myRef.setValue(userProfile);
    }
}