package com.example.ui.components;

import com.google.common.io.CharStreams;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Mixin;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.corelib.mixins.DiscardBody;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@SupportsInformalParameters
public class Source {

    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.ASSET)
    private Resource content;

    @Mixin
    private DiscardBody discardBody;

    @Inject
    private ComponentResources resources;

    @BeginRender
    void begin(final MarkupWriter writer) throws IOException {
        writer.element("pre");
        resources.renderInformalParameters(writer);

        InputStream inputStream = null;
        InputStreamReader reader = null;
        try {
            inputStream = content.openStream();
            reader = new InputStreamReader(inputStream);
            writer.write(CharStreams.toString(reader));
        } finally {
            InternalUtils.close(reader);
            InternalUtils.close(inputStream);
        }

        writer.end();
    }
}
