package com.example.ui.internal;

import org.apache.tapestry5.ContentType;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.*;
import org.apache.tapestry5.util.ResponseWrapper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

/**
 * Filter HTTP request and response to make allow AjaxFileUpload works.
 * Basically make iFrame POST looks like XHR POTS, and escape resulting data.
 *
 * @author Eugen Yakovets
 * @version $Revision$ $Date$
 */
public class FileUploadFilter implements RequestFilter {
    private static final String XHR_EMULATION_PARAMETER = "XHR_EMULATION";

    private final ContentType emulatedContentType;

    public FileUploadFilter(@Symbol(SymbolConstants.CHARSET) String outputEncoding) {
        emulatedContentType = new ContentType("text/html", outputEncoding);
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
            final PrintWriter printWriter = super.getPrintWriter(emulatedContentType.toString());
            return new PrintWriter(printWriter) {
                public void write(String s, int off, int len) {
                    s = s.substring(off, off + len);
                    final String s1 = escapeHTML(s);
                    super.write(s1, 0, s1.length());
                }
            };
        }
    }

    public static String escapeHTML(final String input) {
        if (input == null) {
            return null;
        }
        final StringBuilder result = new StringBuilder();
        StringCharacterIterator iterator = new StringCharacterIterator(input);
        char character = iterator.current();
        while (character != CharacterIterator.DONE) {
            if (character == '<') {
                result.append("&lt;");
            } else if (character == '>') {
                result.append("&gt;");
            } else if (character == '\"') {
                result.append("&quot;");
            } else if (character == '\'') {
                result.append("&#039;");
            } else if (character == '&') {
                result.append("&amp;");
            } else {
                result.append(character);
            }
            character = iterator.next();
        }
        return result.toString();
    }
}
