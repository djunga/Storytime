package com.example.storytime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;*/
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }



///////////////////
private void createAccount(String email, String password) {
    Log.d(TAG, "createAccount:" + email);
    if (!validateForm()) {
        return;
    }

    //showProgressBar();

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
                        Elder testElder = new Elder("Derrik", "Lamb", 77, "Urdu", "Pakistan");
                        //favArr.add(testElder);
//                        Map<String, Object> favData = new HashMap<>();
//                        favData.put("favorites", favArr);

                        db.collection("users").document(newUserUID)
                                .update(
                                        "favorites", favArr
                                );
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }

                    // [START_EXCLUDE]
                    //hideProgressBar();
                    // [END_EXCLUDE]
                }
            });
    // [END create_user_with_email]
}


    private boolean validateForm() {
        boolean valid = true;

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
        } else {
            registerPassword.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null) {
            registerEmail.setText("Register was successful.");
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
