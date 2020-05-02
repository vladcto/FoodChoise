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
import com.google.android.material.navigation.NavigationView;

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
        NavigationView bottomNavigationView = findViewById(R.id.nav_view);
        selectedFragment = new ReciepsFragment();

        bottomNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //TODO: Профиль может быть статичным, быть может, не надо каждый раз создавать новый.
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

    }
}
