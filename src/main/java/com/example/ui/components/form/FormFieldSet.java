package com.example.ui.components.form;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 */
@SupportsInformalParameters
public class FormFieldSet {

    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Inject
    private ComponentResources resources;

    @Environmental
    private ValidationTracker tracker;

    @BeginRender
    void renderTitle(final MarkupWriter writer) {
        writer.element("fieldset");
        resources.renderInformalParameters(writer);

        // render legend
        final Element el = writer.element("legend");
        if (tracker.getHasErrors()) {
            el.addClassName("text-error");
        }
        writer.write(title);
        writer.end();
    }

    @AfterRender
    void end(final MarkupWriter writer) {
        writer.end();
    }
}
