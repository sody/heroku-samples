package com.example.ui.mixins;

import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BindParameter;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * This class represents fixin for form component that adds file uploading possibility when upload component
 * are placed inside tapestry zone. It will submit form in multipart request within separate iframe
 * and mark request with {@code 'XHR_EMULATION'} flag. Then client-side zone will be updated with json response
 * from server. If no file was selected the form will be submitted in a standard way.
 * <p/>
 * This fixin is based on <a href="http://lagoscript.org/jquery/upload?locale=en">jquery upload plugin</a>.
 * <p/>
 * NOTE: {@link com.example.ui.internal.FileUploadFilter} request filter should be configured before.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
@Import(
		library = {
				"context:js/jquery.ajax.upload.js"
		})
public class AjaxUpload {

	@BindParameter
	private String zone;

	@InjectContainer
	private Form form;

	@Inject
	private JavaScriptSupport javascriptSupport;

	/**
	 * Renders fixin's JavaScript if form's zone parameter is bound.
	 */
	@AfterRender
	void renderScript() {
		// render script only if form's zone parameter is bound
		if (zone != null) {
			javascriptSupport.addInitializerCall("ajaxUpload", new JSONObject(
					"formId", form.getClientId(),
					"zoneId", zone
			));
		}
	}
}
