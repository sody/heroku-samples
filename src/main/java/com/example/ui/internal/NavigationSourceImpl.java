package com.example.ui.internal;

import com.example.ui.services.NavigationSource;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.MetaDataLocator;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.RequestGlobals;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class NavigationSourceImpl implements NavigationSource {
    private final RequestGlobals globals;
    private final List<String> pages = new ArrayList<String>();

    public NavigationSourceImpl(final List<Class> pageClasses,
                                final RequestGlobals globals,
                                final ComponentClassResolver componentClassResolver) {
        this.globals = globals;
        for (Class pageClass : pageClasses) {
            final String pageName = componentClassResolver.resolvePageClassNameToPageName(pageClass.getName());
            pages.add(pageName);
        }
    }

    @Override
    public List<Item> getNavigationItems() {
        final String activePageName = globals.getActivePageName();

        final List<Item> items = new ArrayList<Item>();
        for (String pageName : pages) {
            final boolean active = activePageName.equals(pageName);
            items.add(new NavigationItem(active, pageName));
        }
        return items;
    }

    class NavigationItem implements Item {
        private final boolean active;
        private final String pageName;

        NavigationItem(boolean active, String pageName) {
            this.active = active;
            this.pageName = pageName;
        }

        public boolean isActive() {
            return active;
        }

        public String getPageName() {
            return pageName;
        }
    }
}
