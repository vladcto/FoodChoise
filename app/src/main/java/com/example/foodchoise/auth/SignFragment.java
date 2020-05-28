package com.example.foodchoise.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodchoise.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import timber.log.Timber;

public class SignFragment extends Fragment implements Button.OnClickListener {
    EditText emailEditText, passwordEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.auth_sign, container, false);
        emailEditText = view.findViewById(R.id.emailInput);
        passwordEditText = view.findViewById(R.id.passwordInput);
        Button button = view.findViewById(R.id.signIn);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.registration);
        button.setOnClickListener(this);
        Button resetPasswordButton = view.findViewById(R.id.reset_password);
        resetPasswordButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signIn) {
            signIn();
        } else if (v.getId() == R.id.registration) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new RegistrationFragment()).commit();
        } else if (v.getId() == R.id.reset_password) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new ResetPasswordFragment()).commit();
        } else {
            Timber.w("There is no listener at the button - %s .", getResources().getResourceEntryName(v.getId()));
        }
    }

    private void signIn() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        final AuthActivity authActivity = (AuthActivity) getActivity();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    authActivity.startMainActivity();
                } else {
                    Toast.makeText(authActivity, "312312", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
