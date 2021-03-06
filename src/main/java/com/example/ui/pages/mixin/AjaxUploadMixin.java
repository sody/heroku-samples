package com.example.ui.pages.mixin;

import com.example.ui.base.BaseTabbedPage;
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
public class AjaxUploadMixin extends BaseTabbedPage {
    private static final String IMAGE_EVENT = "image";

    @Property
    private UploadedFile file;

    @Property
    private UploadedFile textFile;

    @Property
    @Persist(PersistenceConstants.SESSION)
    private byte[] image;

    @Property
    @Persist(PersistenceConstants.SESSION)
    private String caption;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private String imageName;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private boolean attachText;

    @Property
    @Persist(PersistenceConstants.SESSION)
    private String text;

    @InjectComponent
    private Zone beforeZone;

    @InjectComponent
    private Zone afterZone;

    public Link getImageLink() {
        return getResources().createEventLink(IMAGE_EVENT, imageName);
    }

    @OnEvent(IMAGE_EVENT)
    StreamResponse renderImage() {
        return new StreamResponse() {
            public String getContentType() {
                return "image/*";
            }

            public InputStream getStream() throws IOException {
                return new ByteArrayInputStream(image);
            }

            public void prepareResponse(Response response) {
            }
        };
    }

    @OnEvent(value = FAILURE, component = "beforeForm")
    Object beforeFailure() {
        return beforeZone;
    }

    @OnEvent(value = SUCCESS, component = "beforeForm")
    Object beforeSuccess() throws IOException {
        if (file != null) {
            image = ByteStreams.toByteArray(file.getStream());
            imageName = file.getFileName();
        }
        if (attachText && textFile != null) {
            text = CharStreams.toString(new InputStreamReader(textFile.getStream()));
        }
        return beforeZone;
    }

    @OnEvent(value = FAILURE, component = "afterForm")
    Object afterFailure() {
        return afterZone;
    }

    @OnEvent(value = SUCCESS, component = "afterForm")
    Object afterSuccess() throws IOException {
        if (file != null) {
            image = ByteStreams.toByteArray(file.getStream());
            imageName = file.getFileName();
        }
        if (attachText && textFile != null) {
            text = CharStreams.toString(new InputStreamReader(textFile.getStream()));
        }
        return afterZone;
    }
}
