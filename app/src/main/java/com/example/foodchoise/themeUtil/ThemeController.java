package com.example.foodchoise.themeUtil;

import android.content.Context;

import com.example.foodchoise.R;

import timber.log.Timber;

public final class ThemeController {
    private static final String THEME_CODE = "THEME_CODE";
    private static final int LIGHT_THEME = 0;
    private static final int DARK_THEME = 1;
    private static final String SETTINGS = "settings";

    static private int idThemeNow;

    public static void setNowTheme(Context context){
        switch (idThemeNow){
            case LIGHT_THEME:
                context.setTheme(R.style.LightTheme);
                break;
            case DARK_THEME:
                context.setTheme(R.style.DarkTheme);
                break;
        }
    }

    public static void registerThemeController(Context context){
        idThemeNow = context.getSharedPreferences("settings",Context.MODE_PRIVATE).getInt(THEME_CODE, LIGHT_THEME);
    }

    //TODO: Может вывести эти методы , когда приложение закрывается ?
    public static void setLightTheme(Context context){
        Timber.i("Устанавливаем светлую тему");
        idThemeNow = LIGHT_THEME;
        context.getSharedPreferences(SETTINGS,Context.MODE_PRIVATE).edit().putInt(THEME_CODE,LIGHT_THEME).apply();
    }

    public static void setDarkTheme(Context context){
        Timber.i("Устанавливаем темную тему");
        idThemeNow = DARK_THEME;
        context.getSharedPreferences(SETTINGS,Context.MODE_PRIVATE).edit().putInt(THEME_CODE,DARK_THEME).apply();
    }

    public static int getIdThemeNow(){
        return idThemeNow;
    }
}
