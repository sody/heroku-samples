package com.example.ui.components;

import com.example.ui.base.BaseComponent;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@SupportsInformalParameters
public class Tabs extends BaseComponent implements ClientElement {
    public static final String TAB_PARAMETER_MARKER = "Tab";
    public static final String TAB_HEADER_PARAMETER_MARKER = TAB_PARAMETER_MARKER + "Header";

    @Parameter(name = "id", value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
    private String clientId;

    @Parameter
    private List<String> tabs;

    @Property
    @Parameter
    private String tab;

    @Parameter
    private String selected;

    @Property
    @Parameter
    private boolean offline;

    private String assignedClientId;

    @Inject
    private Block defaultTabHeaderBody;

    @Inject
    private Block defaultOfflineTabHeaderBody;

    @Inject
    private Block defaultTabBody;

    @Inject
    private JavaScriptSupport jsSupport;

    @Override
    public String getClientId() {
        return assignedClientId;
    }

    @Cached
    public List<String> getTabs() {
        if (tabs != null) {
            return tabs;
        }

        // generate tabs from
        final List<String> tabs = new ArrayList<String>();
        for (String informalParameter : getResources().getInformalParameterNames()) {
            if (informalParameter.endsWith(TAB_PARAMETER_MARKER)) {
                tabs.add(informalParameter.substring(0, informalParameter.length() - TAB_PARAMETER_MARKER.length()));
            } else if (informalParameter.endsWith(TAB_PARAMETER_MARKER)) {
                tabs.add(informalParameter.substring(0, informalParameter.length() - TAB_HEADER_PARAMETER_MARKER.length()));
            }
        }
        return tabs;
    }

    public String getActiveClass() {
        return tab.equals(selected) ? " active" : "";
    }

    public String getTabId() {
        return getClientId() + "_" + tab;
    }

    public String getTabHeaderLabel() {
        return tab(tab);
    }

    public Block getTabHeaderBody() {
        final Block header = getResources().getBlockParameter(tab + TAB_HEADER_PARAMETER_MARKER);
        return header != null ? header : offline ? defaultOfflineTabHeaderBody : defaultTabHeaderBody;
    }

    public Block getTabBody() {
        final String currentTab = offline ? tab : selected;
        final Block header = getResources().getBlockParameter(currentTab + TAB_PARAMETER_MARKER);
        return header != null ? header : defaultTabBody;
    }

    @SetupRender
    void setup() {
        // setup client id
        assignedClientId = jsSupport.allocateClientId(clientId);

        // setup selected tab
        if (selected == null) {
            Iterator<String> iterator = getTabs().iterator();
            selected = iterator.hasNext() ? iterator.next() : null;
        }
    }

    @OnEvent(EventConstants.ACTION)
    void selectTab(final String selectedTab) {
        // change tab
        selected = selectedTab;
    }
}
