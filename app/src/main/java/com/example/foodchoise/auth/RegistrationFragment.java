package com.example.foodchoise.auth;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodchoise.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import timber.log.Timber;

public class RegistrationFragment extends Fragment implements Button.OnClickListener{
    EditText emailEditText, passwordEditText, nameEditText;
    TextInputLayout inputLayoutEmail,inputLayoutPassword,inputLayoutName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.auth_registration, container, false);

        Button button = view.findViewById(R.id.registrationButton);
        button.setOnClickListener(this);
        ImageButton imageButton = view.findViewById(R.id.backButton);
        imageButton.setOnClickListener(this);

        emailEditText =  view.findViewById(R.id.emailInput);
        passwordEditText =  view.findViewById(R.id.passwordInput);
        nameEditText =  view.findViewById(R.id.nameInput);

        inputLayoutEmail = view.findViewById(R.id.inputLayoutEmail);
        inputLayoutPassword = view.findViewById(R.id.inputLayoutPassword);
        inputLayoutName = view.findViewById(R.id.inputLayoutName);

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                emailVerife(s.toString().trim());
            }
        });
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                passwordVerife(s.toString().trim());
            }
        });
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                nameVerife(s.toString().trim());
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registrationButton) {
            Activity activity = getActivity();

            String email = emailEditText.getText().toString().trim();
            //region TODO: ВЫНЕСТИ В ОТДЕЛЬНЫЙ МЕТОД
            if (!emailVerife(email)) {
                return;
            }

            String password = passwordEditText.getText().toString().trim();
            if (!passwordVerife(password)) {
                return;
            }

            String name = nameEditText.getText().toString().trim();
            if (!nameVerife(name)) {
                Timber.w("Name not allowed");
                return;
            }

            //endregion

            registration(email, password, name);
        } else if (v.getId() == R.id.backButton) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer,new SignFragment())
                    .commit();
        } else {
            Timber.w("There is no listener at the button - %s .", getResources().getResourceEntryName(v.getId()));
        }
    }

    //TODO: Вынести методы в отельный класс
    private boolean passwordVerife(String password) {
        if(password.isEmpty()){
            inputLayoutPassword.setError(getContext().getResources().getText(R.string.empty_password));
            return false;
        }else if(password.length() < 6){
            inputLayoutPassword.setError(getContext().getResources().getText(R.string.weak_password));
            return false;
        }else {
            inputLayoutPassword.setError(null);
            return true;
        }
    }

    //TODO: Вынести методы в отельный класс
    private boolean nameVerife(String name) {
        if(name.isEmpty()){
            inputLayoutName.setError(getContext().getResources().getText(R.string.empty_name));
            return false;
        }else {
            inputLayoutName.setError(null);
            return true;
        }
    }

    //TODO: Вынести методы в отельный класс
    private boolean emailVerife(String email){
        if(EmailValidator.getInstance().validate(email)){
            inputLayoutEmail.setError(null);
            return true;
        }else {
            inputLayoutEmail.setError(getContext().getResources().getText(R.string.not_valid_email));
            return false;
        }
    }

    private void registration(String email, String password, final String name) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "+", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name).build();
                    user.updateProfile(profileUpdates);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new SignFragment()).commit();
                } else {
                    Toast.makeText(getContext(), "-", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
