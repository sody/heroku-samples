package com.example.ui.services;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.ioc.Messages;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface NavigationSource {

    List<Item> getNavigationItems(Messages messages);

    interface Item {

        boolean isActive();

        Link getLink();

        String getLabel();

        List<Item> getChildren();
    }
}
