package com.example.foodchoise.auth;

import android.app.Activity;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import timber.log.Timber;

public class RegistrationFragment extends Fragment implements Button.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.auth_registration, container, false);
        Button button = view.findViewById(R.id.registrationButton);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registrationButton) {
            Activity activity = getActivity();
            EditText emailEditText, passwordEditText, nameEditText;
            try {
                emailEditText = activity.findViewById(R.id.emailInput);
                passwordEditText = activity.findViewById(R.id.passwordInput);
                nameEditText = activity.findViewById(R.id.nameInput);
            } catch (NullPointerException e) {
                e.printStackTrace();
                return;
            }

            String email = emailEditText.getText().toString().trim();
            //region TODO: ВЫНЕСТИ В ОТДЕЛЬНЫЙ МЕТОД
            if (!emailVerife(email)){
                return;
            }

            String password = passwordEditText.getText().toString().trim();
            if(!passwordVerife(password)){
                return;
            }

            String name = nameEditText.getText().toString().trim();
            if(!nameVerife(name)){
                Timber.w("Name not allowed");
                return;
            }

            //endregion

            registration(email,password,name);
        } else {
            Timber.w("There is no listener at the button - %s .", getResources().getResourceEntryName(v.getId()));
        }
    }

    private boolean emailVerife(String email) {
        Timber.e("Non implement emailVerife");
        return email != null;
    }

    private boolean passwordVerife(String password){
        Timber.e("Non implement passwordVerife");
        return password != null;
    }

    private boolean nameVerife(String name){
        return name != null && name.length() != 0;
    }


    private void registration(String email, String password, final String name) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(),"+",Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name).build();
                    user.updateProfile(profileUpdates);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new SignFragment()).commit();
                }else{
                    Toast.makeText(getContext(),"-",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
