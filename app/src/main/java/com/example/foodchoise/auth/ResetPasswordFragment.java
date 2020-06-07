package com.example.foodchoise.auth;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodchoise.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordFragment extends Fragment {
    EditText emailEditText;
    TextInputLayout inputLayoutEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.auth_reset_password,container,false);
        ImageButton button = view.findViewById(R.id.backButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer,new SignFragment())
                        .commit();
            }
        });
        emailEditText = view.findViewById(R.id.inputEmail);
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                emailVerife(s.toString().trim());
            }
        });

        inputLayoutEmail = view.findViewById(R.id.inputLayoutEmail);

        ImageButton resetPasswordButton = view.findViewById(R.id.resetPasswordButton);
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                if(!emailVerife(email)){return; }
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(),R.string.email_reset_successful,Toast.LENGTH_LONG).show();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainer,new SignFragment())
                                .commit();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),R.string.email_reset_failed,Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                });
            }
        });
        return view;
    }

    private boolean emailVerife(String email){
        if(EmailValidator.getInstance().validate(email)){
            inputLayoutEmail.setError(null);
            return true;
        }else {
            inputLayoutEmail.setError(getContext().getResources().getText(R.string.not_valid_email));
            return false;
        }
    }
}
