package com.example.ui.pages;

import com.example.ui.base.BasePage;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Button extends BasePage {

    @Inject
    private PageRenderLinkSource pageRenderLinkSource;

    public Link getHiddenPageLink() {
        return pageRenderLinkSource.createPageRenderLink(ButtonHidden.class);
    }
}
