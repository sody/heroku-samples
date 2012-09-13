package com.example.ui.components;

import com.example.ui.base.BaseComponent;
import com.example.ui.services.NavigationSource;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Mixin;
import org.apache.tapestry5.corelib.mixins.DiscardBody;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Navigation extends BaseComponent {

    @Inject
    private NavigationSource navigationSource;

    @Mixin
    private DiscardBody discardBody;

    @BeginRender
    void render(final MarkupWriter writer) {
        final List<NavigationSource.Item> items = navigationSource.getNavigationItems(getMessages());
        writer.element("ul",
                "class", "nav");

        for (NavigationSource.Item item : items) {
            renderItem(writer, item);
        }

        writer.end();
    }

    private void renderItem(final MarkupWriter writer,
                            final NavigationSource.Item item) {
        final boolean hasChildren = !item.getChildren().isEmpty();

        final Element li = writer.element("li");
        if (item.isActive()) {
            li.addClassName("active");
        }
        if (hasChildren) {
            li.addClassName("dropdown");
        }

        writer.element("a", "href", toHref(item.getLink()));
        writer.write(item.getLabel());
        if (hasChildren) {
            writer.attributes(
                    "class", "dropdown-toggle",
                    "data-toggle", "dropdown");
            writer.element("span",
                    "class", "caret");
            writer.end();
        }
        writer.end();

        if (hasChildren) {
            writer.element("ul",
                    "class", "dropdown-menu");
            for (NavigationSource.Item childItem : item.getChildren()) {
                writer.element("li");
                writer.element("a", "href", toHref(childItem.getLink()));
                writer.write(childItem.getLabel());
                writer.end();
                writer.end();
            }
            writer.end();
        }

        writer.end();
    }

    private String toHref(final Link link) {
        return link != null ? link.toURI() : "#";
    }
}
