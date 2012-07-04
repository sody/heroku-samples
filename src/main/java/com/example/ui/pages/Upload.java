package com.example.ui.pages;

import com.example.ui.base.BasePage;
import com.google.common.io.ByteStreams;
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

import static org.apache.tapestry5.EventConstants.FAILURE;
import static org.apache.tapestry5.EventConstants.SUCCESS;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Upload extends BasePage {

    @Property
    private UploadedFile file;

    @Property
    @Persist(PersistenceConstants.SESSION)
    private byte[] image;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private boolean success;

    @InjectComponent
    private Zone formZone;

    public Link getImageLink() {
        return getResources().createEventLink("image");
    }

    public StreamResponse onImage() {
        return new StreamResponse() {
            public String getContentType() {
                return "application/octet-stream";
            }

            public InputStream getStream() throws IOException {
                return new ByteArrayInputStream(image);
            }

            public void prepareResponse(Response response) {
            }
        };
    }

    @OnEvent(value = FAILURE)
    public Object onFailure() {
        return formZone;
    }

    @OnEvent(value = SUCCESS)
    public Object onSuccess() throws IOException {
        image = ByteStreams.toByteArray(file.getStream());
        success = true;

        return formZone;
    }
}
