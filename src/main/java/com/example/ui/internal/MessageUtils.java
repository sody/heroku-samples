package com.example.ui.internal;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class MessageUtils {

    public static String label(final String key) {
        return "label." + normalize(key);
    }

    public static String navigation(final String key) {
        return "nav." + normalize(key);
    }

    public static String title(final String key) {
        return "page." + normalize(key) + ".title";
    }

    public static String normalize(final String key) {
        return key.replaceAll("/", ".").replaceAll("([^.])([A-Z])", "$1-$2").toLowerCase();
    }
}
