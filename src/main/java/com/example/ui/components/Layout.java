package com.example.ui.components;

import com.example.ui.base.BaseComponent;
import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.BindingSource;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@SupportsInformalParameters
@Import(
		stylesheet = "context:css/bootstrap.css",
		library = {"context:js/jquery-1.7.2.js", "context:js/bootstrap.js"}
)
public class Layout extends BaseComponent {
	private static final String PAGE_TITLE_PROPERTY = "title";
	private static final String PAGE_SUBTITLE_PROPERTY = "subtitle";

	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String title;

	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String subtitle;

	@Property
	@Inject
	@Symbol(SymbolConstants.APPLICATION_VERSION)
	private String applicationVersion;

	@Property
	@Inject
	@Symbol(SymbolConstants.TAPESTRY_VERSION)
	private String tapestryVersion;

	@Inject
	private BindingSource bindingSource;

	Binding defaultTitle() {
		return bindingSource.newBinding("Page title", getResources().getContainerResources(), BindingConstants.PROP, PAGE_TITLE_PROPERTY);
	}

	Binding defaultSubtitle() {
		return bindingSource.newBinding("Page subtitle", getResources().getContainerResources(), BindingConstants.PROP, PAGE_SUBTITLE_PROPERTY);
	}

}
