package com.example.ui.base;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BasePage extends BaseComponent {
    public String getTitle() {
        //todo: make more complex title obtaining(with prefix and suffix)
        if (getMessages().contains("title")) {
            return getMessages().get("title");
        }
        final String key = "page." + getResources().getPageName().toLowerCase().replaceAll("/", "-") + ".title";
        return getMessages().get(key);
    }

    public String getSubtitle() {
        return null;
    }
}