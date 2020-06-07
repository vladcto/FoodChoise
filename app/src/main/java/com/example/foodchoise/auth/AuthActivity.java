package com.example.foodchoise.auth;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.foodchoise.MainActivity;
import com.example.foodchoise.R;
import com.example.foodchoise.themeUtil.ThemeController;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        ThemeController.setNowTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        email = (EditText) findViewById(R.id.emailInput);
        password = (EditText) findViewById(R.id.passwordInput);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            Log.d("auth", "debug+" + currentUser.getEmail());
            final boolean[] success = {true};
            currentUser.reload().addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    success[0] = false;
                    String exc = e.getStackTrace().toString();
                    Log.e("FireBaseUser", exc);
                    mAuth.signOut();
                }
            });

            if (success[0]) {
                startMainActivity();
            }
        }

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.i("auth", "Signed in: " + user.getEmail());
                } else {
                    Log.i("auth", "Signed out.");
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new SignFragment()).commit();
    }

    void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("name",mAuth.getCurrentUser().getDisplayName());
        intent.putExtra("email",mAuth.getCurrentUser().getEmail());
        startActivity(intent);
        //TODO:
        mAuth.removeAuthStateListener(mAuthListener);
        finish();
    }

    @Override
    public void onBackPressed() {
        //Можно использовать паттерн стратегия.
        Fragment fragment = getSupportFragmentManager().getFragments().get(0);
        if(fragment instanceof RegistrationFragment || fragment instanceof ResetPasswordFragment){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new SignFragment()).commit();
        }else {
            finish();
        }
    }
}
