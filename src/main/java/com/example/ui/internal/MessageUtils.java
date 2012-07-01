package com.example.ui.internal;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class MessageUtils {

    public static String label(String key) {
        return "label." + normalize(key);
    }

    public static String normalize(final String key) {
        return key.replaceAll("([A-Z])", "-$1").toLowerCase();
    }
}
