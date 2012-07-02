package com.example.ui.services;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface NavigationSource {

    List<Item> getNavigationItems();


    interface Item {

        boolean isActive();

        String getPageName();
    }
}
