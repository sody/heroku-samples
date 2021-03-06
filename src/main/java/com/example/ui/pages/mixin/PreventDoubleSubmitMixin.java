package com.example.ui.pages.mixin;

import com.example.ui.base.BasePage;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.upload.services.UploadedFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.apache.tapestry5.EventConstants.FAILURE;
import static org.apache.tapestry5.EventConstants.SUCCESS;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class PreventDoubleSubmitMixin extends BasePage {

    @Property
    @Persist(PersistenceConstants.FLASH)
    private String text;

    @InjectComponent
    private Zone formZone;

    @OnEvent(value = FAILURE)
    public Object onFailure() {
        // use zone body to prevent zone duplication effect
        return formZone.getBody();
    }

    @OnEvent(value = SUCCESS)
    public Object onSuccess() throws IOException {
        // use zone body to prevent zone duplication effect
        if (text == null) {
            throw new IllegalStateException("Alarm");
        }

        return formZone.getBody();
    }
}
