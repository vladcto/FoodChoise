package com.example.foodchoise.auth;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodchoise.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.BuildConfig;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import timber.log.Timber;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        email = (EditText) findViewById(R.id.emailInput);
        password = (EditText) findViewById(R.id.passwordInput);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        //TODO: Зачем это?
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (currentUser != null) {
                    if (BuildConfig.DEBUG) {
                        Log.i("auth","Signed in: " + currentUser.getUid());
                    }
                } else {
                    if (BuildConfig.DEBUG) {
                        Log.i("auth","Signed out.");
                    }
                }
            }
        };

        Button button ;
        button = findViewById(R.id.registration);
        button.setOnClickListener(this);
        button = findViewById(R.id.signIn);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();
        Log.i("auth","Email: "+ email);
        Log.i("auth","Password: "+ password);
        if (v.getId() == R.id.signIn) {
            signIn(email,password);

        } else if (v.getId() == R.id.registration) {
            registration(email,password);

        } else {
            throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AuthActivity.this, "+", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AuthActivity.this, "-", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void registration(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AuthActivity.this, "+", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AuthActivity.this, "-", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}