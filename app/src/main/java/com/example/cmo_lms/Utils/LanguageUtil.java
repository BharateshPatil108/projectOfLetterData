package com.example.cmo_lms.Utils;


import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.LocaleList;

import java.util.Locale;

public class LanguageUtil {
    private static String lang;

    public static void setAppLocale(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        lang = language;

        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();

        config.setLocale(locale);
        LocaleList localeList = new LocaleList(locale);
        LocaleList.setDefault(localeList);
        config.setLocales(localeList);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    public static String getCurrentLanguage() {
        return lang != null ? lang : Locale.getDefault().getLanguage();
    }

}


