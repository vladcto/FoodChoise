package com.example.foodchoise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Debug;
import android.view.MenuItem;

import com.example.foodchoise.main_fragments.CardFragment;
import com.example.foodchoise.main_fragments.ProfileFragment;
import com.example.foodchoise.main_fragments.ReciepsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    Fragment selectedFragment = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }

        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_bar);
        selectedFragment = new ReciepsFragment();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.fast_choise:
                        if(!(selectedFragment instanceof CardFragment)) {
                            selectedFragment = new CardFragment();
                        }
                        break;
                    case R.id.recipes_menu:
                        if(!(selectedFragment instanceof ReciepsFragment)) {
                            selectedFragment = new ReciepsFragment();
                        }
                        break;
                    case R.id.profile:
                        if(!(selectedFragment instanceof ProfileFragment)) {
                            selectedFragment = new ProfileFragment();
                        }
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            }
        }
        );


        bottomNavigationView.setSelectedItemId(R.id.recipes_menu);
    }
}
