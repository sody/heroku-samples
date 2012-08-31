package com.example.ui.mixins;

import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * This class represents fixin for form component that adds client-side logic to page that prevents double form
 * submission. It will mark form as submitted on {@code Tapestry.FORM_PREPARE_FOR_SUBMIT_EVENT} client event and if it
 * has already marked as submitted - will stop {@code Tapestry.FORM_PROCESS_SUBMIT_EVENT} event. After zone was updated
 * with new content, our form will be marked as clear.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class PreventDoubleSubmit {

	@InjectContainer
	private Form form;

	@Inject
	private JavaScriptSupport javascriptSupport;

	/**
	 * Renders mixin's JavaScript.
	 */
	@AfterRender
	void renderScript() {
		javascriptSupport.addInitializerCall("preventDoubleSubmit", new JSONObject("formId", form.getClientId()));
	}

}
