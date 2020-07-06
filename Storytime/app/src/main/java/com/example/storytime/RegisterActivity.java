/*
 * NAME: Tora Mullings
 * SB ID: 111407756
 * */
package com.example.storytime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class registers a new user. It sets defaults like style and font size.
 * It verifies the form.
 * Firestore is used to store the new user's information.
 */
public class RegisterActivity extends AppCompatActivity {
    private EditText registerName;
    private EditText registerEmail;
    private EditText registerPassword;
    private EditText registerConfirmPassword;

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_fields);

        registerName = findViewById(R.id.editTextRegisterName);
        registerEmail = findViewById(R.id.editTextRegisterEmail);
        registerPassword = findViewById(R.id.editTextRegisterPassword);
        registerConfirmPassword = findViewById(R.id.editTextRegisterConfirmPassword);

        initRegisterScreenButton();

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        // [END initialize_auth]
    }

    private void initRegisterScreenButton() {
        final Button buttonRegister = findViewById(R.id.buttonRegisterScreen);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();
                createAccount(email, password);
            }
        });
    }

    /**
     * The user is shown the home screen after registering.
     */
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    /**
     * This method validates the registration. If valid, it goes into
     * the firestore and stores the new user's info. Else, it displays alerts
     * to let the user know what they did wrong.
     * @param email The email used for registering
     * @param password The password used for registering
     */
private void createAccount(String email, String password) {
    Log.d(TAG, "createAccount:" + email);
    if (!validateForm()) {
        return;
    }

    String pass = registerPassword.getText().toString();
    String confirmPass = registerConfirmPassword.getText().toString();
    if(!pass.equals(confirmPass)) {
        Toast.makeText(RegisterActivity.this, "Password does not match Confirm Password.",
                Toast.LENGTH_SHORT).show();
        return;
    }

    // [START create_user_with_email]
    mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        String newUserUID = user.getUid();

                        Map<String, String> data = new HashMap<>();
                        data.put("name", registerName.getText().toString());
                        data.put("email", registerEmail.getText().toString());
                        data.put("password", registerPassword.getText().toString());
                        db.collection("users").document(newUserUID).set(data);

                        ArrayList<Elder> favArr = new ArrayList<>();
                        db.collection("users").document(newUserUID)
                                .update(
                                        "favorites", favArr
                                );
                        db.collection("users").document(newUserUID)
                                .update(
                                        "fontSize", 48
                                );
                        db.collection("users").document(newUserUID)
                                .update(
                                        "style", "indigo"
                                );
                        updateUI(user);
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }

                }
            });
}


    /**
     * This method checks if all fields on the registration form
     * are filled out correctly.
     * @return Returns true if the form is valid
     */
    private boolean validateForm() {
        boolean valid = true;

        String name = registerName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            registerName.setError("Required.");
            valid = false;
        } else {
            registerName.setError(null);
        }

        String confirmPassword = registerConfirmPassword.getText().toString();
        if (TextUtils.isEmpty(confirmPassword)) {
            registerConfirmPassword.setError("Required.");
            valid = false;
        } else {
            registerConfirmPassword.setError(null);
        }

        String email = registerEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            registerEmail.setError("Required.");
            valid = false;
        } else {
            registerEmail.setError(null);
        }

        String password = registerPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            registerPassword.setError("Required.");
            valid = false;
        }
        else if(password.length() < 6) {
            registerPassword.setError("Password must be at least 6 characters.");
            Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters..",
                    Toast.LENGTH_SHORT).show();
            valid = false;
        }
        else {
            registerPassword.setError(null);
        }

        return valid;
    }

    /**
     * This method is called after the user has been successfully registered.
     * It starts the Main Activity, which takes them to the Home Screen.
     * @param currentUser The current user that was registered.
     */
    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null) {
            registerEmail.setText("Registration was successful.");
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
