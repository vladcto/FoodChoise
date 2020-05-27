package com.example.foodchoise;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.foodchoise.main_fragments.CardFragment;
import com.example.foodchoise.main_fragments.FavoritesFragment;
import com.example.foodchoise.main_fragments.ProfileFragment;
import com.example.foodchoise.main_fragments.ReciepsFragment;
import com.example.foodchoise.themeUtil.ThemeController;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private Toolbar toolbar;
    NavigationView navigationView;
    //TODO: ЧТО ЭТО ДЕЛАЕТ??
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        ThemeController.setNowTheme(this);
        super.onCreate(savedInstanceState);

        //Устанавливаю NavigationView
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.text, R.string.text);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        View navHeader = navigationView.getHeaderView(0);
        TextView textView = navHeader.findViewById(R.id.name_profile);
        textView.setText(getIntent().getStringExtra("name"));
        textView = navHeader.findViewById(R.id.email_profile);
        textView.setText(getIntent().getStringExtra("email"));

        SwitchCompat switchChoiceTheme = (SwitchCompat) navigationView.getMenu().findItem(R.id.switchTheme).getActionView();
        //TODO: Исправить эту фигню
        switchChoiceTheme.setChecked(1 == ThemeController.getIdThemeNow());
        switchChoiceTheme.setOnCheckedChangeListener(this);

        //TODO: Перенести в имплементирования слушателя , а то читать невозможно.
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = getSelectedFragment();
                switch (menuItem.getItemId()) {
                    case R.id.fast_choise:
                        if (!(selectedFragment instanceof CardFragment)) {
                            selectedFragment = new CardFragment();
                        }
                        break;
                        case R.id.recipes_menu:
                            if (!(selectedFragment instanceof ReciepsFragment)) {
                                selectedFragment = new ReciepsFragment();
                            }
                            break;
                            case R.id.profile:
                                if (!(selectedFragment instanceof ProfileFragment)) {
                                    selectedFragment = new ProfileFragment();
                                }
                                break;
                    case R.id.good_recipes:
                        if (!(selectedFragment instanceof FavoritesFragment)) {
                            selectedFragment = new FavoritesFragment();
                        }
                        break;
                }getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                toolbar.setTitle(menuItem.getTitle());
                drawerLayout.closeDrawers();
                return true;
            }
        }
        );

        //Загружаю страницу по умолчанию
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new ReciepsFragment()).commit();
        navigationView.setCheckedItem(R.id.recipes_menu);
        MenuItem menuItem = navigationView.getCheckedItem();
        toolbar.setTitle(menuItem.getTitle());
        setSupportActionBar(toolbar);
        drawerLayout.openDrawer(navigationView);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked) {
            ThemeController.setLightTheme(this);
        } else {
            ThemeController.setDarkTheme(this);
        }
        this.recreate();
        navigationView.setCheckedItem(R.id.recipes_menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Действие home/up action bar'а должно открывать или закрывать drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Fragment getSelectedFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    }


}
