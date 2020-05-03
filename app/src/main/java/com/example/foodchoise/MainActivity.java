package com.example.foodchoise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.foodchoise.main_fragments.CardFragment;
import com.example.foodchoise.main_fragments.ProfileFragment;
import com.example.foodchoise.main_fragments.ReciepsFragment;
import com.google.android.material.navigation.NavigationView;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    Fragment selectedFragment = null;
    private Toolbar toolbar;
    NavigationView navigationView;
    //TODO: ЧТО ЭТО ДЕЛАЕТ??
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }

        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.text, R.string.text);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        setSupportActionBar(toolbar);

        selectedFragment = new ReciepsFragment();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
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
                toolbar.setTitle(menuItem.getTitle());
                drawerLayout.closeDrawers();
                return true;
            }
        }
        );
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        // Действие home/up action bar'а должно открывать или закрывать drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
