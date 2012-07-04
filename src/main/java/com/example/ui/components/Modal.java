package com.example.ui.components;

import com.example.ui.base.BaseComponent;
import com.example.ui.internal.CSSConstants;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Any;
import org.apache.tapestry5.corelib.mixins.RenderInformals;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@SupportsInformalParameters
public class Modal extends BaseComponent implements ClientElement {

    @Parameter(name = "id", value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
    private String clientId;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Parameter
    private boolean active;

    @Parameter(name = "class", defaultPrefix = BindingConstants.LITERAL)
    private String className;

    @Parameter
    private Block footer;

    @Inject
    private Block defaultFooter;

    @Component(inheritInformalParameters = true)
    @MixinClasses(RenderInformals.class)
    private Any container;

    @Inject
    private JavaScriptSupport support;

    public String getClientId() {
        return clientId;
    }

    public String getContainerClass() {
        return className != null ?
                CSSConstants.MODAL + " " + className :
                CSSConstants.MODAL;
    }

    public Block getFooter() {
        return footer != null ? footer : defaultFooter;
    }

    @AfterRender
    void renderScript() {
        if (active) {
            support.addInitializerCall("modal", new JSONObject("id", clientId));
        }
    }
}