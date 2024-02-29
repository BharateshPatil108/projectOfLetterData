package com.example.cmo_lms.Utils;


import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.LocaleList;

import java.util.Locale;

public class LanguageUtil {

    public static void setAppLocale(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();

        config.setLocale(locale);
        LocaleList localeList = new LocaleList(locale);
        LocaleList.setDefault(localeList);
        config.setLocales(localeList);
        context = context.createConfigurationContext(config);
    }

    public static String getCurrentLanguage() {
        return Locale.getDefault().getLanguage();
    }

}


