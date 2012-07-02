package com.example.ui.base;

import com.example.ui.internal.MessageUtils;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BasePage extends BaseComponent {

    public String getTitle() {
        //todo: make more complex title obtaining(with prefix and suffix)
        if (getMessages().contains("title")) {
            return message("title");
        }
        return message(MessageUtils.title(getResources().getPageName()));
    }

    public String getSubtitle() {
        return null;
    }
}