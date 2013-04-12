package com.example.ui.internal;

import com.example.ui.services.NavigationSource;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.RequestGlobals;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class NavigationSourceBuilder implements NavigationSource {
    private final RequestGlobals globals;
    private final PageRenderLinkSource linkSource;
    private final ComponentClassResolver componentClassResolver;
    private final ItemBuilder rootBuilder = new ItemBuilder(null, null, null);

    public NavigationSourceBuilder(final RequestGlobals globals,
                                   final PageRenderLinkSource linkSource,
                                   final ComponentClassResolver componentClassResolver) {
        this.globals = globals;
        this.linkSource = linkSource;
        this.componentClassResolver = componentClassResolver;
    }

    public List<Item> getNavigationItems(final Messages messages) {
        return rootBuilder.build(messages).getChildren();
    }

    public ItemBuilder add(final Class pageClass) {
        return rootBuilder.add(pageClass);
    }

    public ItemBuilder child(final String itemLabel) {
        return rootBuilder.child(itemLabel);
    }

    public class ItemBuilder {
        private final Class pageClass;
        private final String labelKey;
        private final ItemBuilder parentBuilder;
        private final List<ItemBuilder> childBuilders = new ArrayList<ItemBuilder>();

        private ItemBuilder(final ItemBuilder parentBuilder, final Class pageClass, final String labelKey) {
            this.parentBuilder = parentBuilder;
            this.pageClass = pageClass;
            this.labelKey = labelKey;
        }

        public NavigationSource.Item build(final Messages messages) {
            final String pageName = pageClass != null ?
                    componentClassResolver.resolvePageClassNameToPageName(pageClass.getName()) :
                    null;
            final Link link = pageClass != null ?
                    linkSource.createPageRenderLink(pageClass) :
                    null;
            final String label = labelKey != null ?
                    messages.get(MessageUtils.navigation(labelKey)) :
                    pageName != null ?
                            messages.get(MessageUtils.navigation(pageName)) :
                            null;
            boolean active = globals.getActivePageName().equals(pageName);

            final List<NavigationSource.Item> children = new ArrayList<NavigationSource.Item>();
            for (ItemBuilder childBuilder : childBuilders) {
                final NavigationSource.Item child = childBuilder.build(messages);
                children.add(child);
                active = active || child.isActive();
            }

            return new NavigationItem(active, link, label, children);
        }

        public ItemBuilder add(final Class pageClass) {
            childBuilders.add(new ItemBuilder(this, pageClass, null));
            return this;
        }

        public ItemBuilder child(final String labelKey) {
            final ItemBuilder child = new ItemBuilder(this, null, labelKey);
            childBuilders.add(child);
            return child;
        }

        public ItemBuilder end() {
            return parentBuilder;
        }
    }
}

class NavigationItem implements NavigationSource.Item {
    private final boolean active;
    private final Link link;
    private final String label;
    private final List<NavigationSource.Item> children;

    NavigationItem(final boolean active,
                   final Link link,
                   final String label,
                   final List<NavigationSource.Item> children) {
        this.active = active;
        this.link = link;
        this.label = label;
        this.children = children;
    }

    public boolean isActive() {
        return active;
    }

    public Link getLink() {
        return link;
    }

    public String getLabel() {
        return label;
    }

    public List<NavigationSource.Item> getChildren() {
        return children;
    }
}
