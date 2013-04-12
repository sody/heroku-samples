package com.example.ui.components.form;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Mixin;
import org.apache.tapestry5.corelib.mixins.DiscardBody;

/**
 * @author Ivan Khalopik
 */
public class FormErrors {

    @Mixin
    private DiscardBody discardBody;

    @Environmental
    private ValidationTracker tracker;

    @BeginRender
    void render(final MarkupWriter writer) {
        if (tracker.getHasErrors()) {
            writer.element("div")
                    .addClassName("alert", "alert-block", "alert-error");

            writer.element("a",
                    "href", "#",
                    "data-dismiss", "alert")
                    .addClassName("close");
            writer.writeRaw("&times;");
            writer.end();

            for (String error : tracker.getErrors()) {
                writer.element("p");
                writer.write(error);
                writer.end();
            }

            writer.end();
        }
    }
}
