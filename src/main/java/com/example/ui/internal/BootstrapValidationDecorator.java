package com.example.ui.internal;

import org.apache.tapestry5.BaseValidationDecorator;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationDecorator;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.services.Environment;

/**
 * @author Ivan Khalopik
 */
public class BootstrapValidationDecorator extends BaseValidationDecorator implements ValidationDecorator {
    private final Environment environment;
    private final MarkupWriter markupWriter;

    public BootstrapValidationDecorator(final Environment environment, final MarkupWriter markupWriter) {
        this.environment = environment;
        this.markupWriter = markupWriter;
    }

    @Override
    public void afterField(final Field field) {
        if (inError(field)) {
            markupWriter.getElement().getContainer().addClassName("error");
        }
    }

    private boolean inError(final Field field) {
        final ValidationTracker tracker = environment.peekRequired(ValidationTracker.class);
        return tracker.inError(field);
    }
}
