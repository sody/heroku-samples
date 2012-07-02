package com.example.ui.components;

import com.example.ui.base.BaseComponent;
import com.example.ui.internal.MessageUtils;
import com.example.ui.services.NavigationSource;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Navigation extends BaseComponent {

    @Inject
    private NavigationSource navigationSource;

    @Property
    private List<NavigationSource.Item> navigationItems;

    @Property
    private NavigationSource.Item navigationItem;

    public String getPageLabel() {
        return message(MessageUtils.navigation(navigationItem.getPageName()));
    }

    @SetupRender
    void setupCurrentPage() {
        navigationItems = navigationSource.getNavigationItems();
    }
}
