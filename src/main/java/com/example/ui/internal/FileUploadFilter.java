package com.example.ui.internal;

import org.apache.tapestry5.services.*;

import java.io.IOException;

/**
 * Filter HTTP request and response to make allow AjaxFileUpload works.
 * Basically make iFrame POST looks like XHR POTS, and escape resulting data.
 *
 * @author Eugen Yakovets
 * @version $Revision$ $Date$
 */
public class FileUploadFilter implements RequestFilter {
    private static final String XHR_EMULATION_PARAMETER = "XHR_EMULATION";

    public boolean service(Request request, Response response, RequestHandler handler) throws IOException {
        //separate handler for FileUpload requests only
        if (!request.isXHR() && request.getParameter(XHR_EMULATION_PARAMETER) != null) {
            return handler.service(new FileUploadRequest(request), response);
        }
        return handler.service(request, response);
    }

    public static class FileUploadRequest extends DelegatingRequest {
        public FileUploadRequest(final Request request) {
            super(request);
        }

        @Override
        public boolean isXHR() {
            return true;
        }
    }
}
