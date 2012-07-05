package com.example.ui.internal;

import org.apache.tapestry5.ContentType;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.*;
import org.apache.tapestry5.util.ResponseWrapper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FileUploadFilter implements RequestFilter {
    private static final String XHR_EMULATION_PARAMETER = "XHR_EMULATION";

    private final ContentType emulatedContentType;

    public FileUploadFilter(@Symbol(SymbolConstants.CHARSET) String outputEncoding) {
        emulatedContentType = new ContentType("text/plain", outputEncoding);
    }

    public boolean service(final Request request, final Response response, final RequestHandler handler) throws IOException {
        //separate handler for FileUpload requests only
        if (!request.isXHR() && request.getParameter(XHR_EMULATION_PARAMETER) != null) {
            return handler.service(new FileUploadRequest(request), new FileUploadResponse(response));
        }
        return handler.service(request, response);
    }

    public class FileUploadRequest extends DelegatingRequest {
        public FileUploadRequest(final Request request) {
            super(request);
        }

        @Override
        public boolean isXHR() {
            return true;
        }
    }

    public class FileUploadResponse extends ResponseWrapper {

        public FileUploadResponse(final Response response) {
            super(response);
        }

        @Override
        public OutputStream getOutputStream(final String contentType) throws IOException {
            return super.getOutputStream(emulatedContentType.toString());
        }

        @Override
        public PrintWriter getPrintWriter(final String contentType) throws IOException {
            return super.getPrintWriter(emulatedContentType.toString());
        }
    }
}
