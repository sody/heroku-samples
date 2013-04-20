package com.example.ui.base;

import com.example.ui.internal.MessageUtils;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BaseComponent {

    @Inject
    private ComponentResources resources;

    @Inject
    private Locale locale;

    public ComponentResources getResources() {
        return resources;
    }

    public Messages getMessages() {
        return resources.getContainerMessages();
    }

    public Locale getLocale() {
        return locale;
    }

    protected String format(final String key, final Object... parameters) {
        return getMessages().format(key, parameters);
    }

    protected String message(final String key) {
        return getMessages().get(key);
    }

    protected String label(final String key) {
        return getMessages().get(MessageUtils.label(key));
    }

    protected String tab(final String key) {
        return getMessages().get(MessageUtils.tab(key));
    }
}
