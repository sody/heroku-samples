package com.example.ui.base;

import com.example.ui.components.Tabs;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BaseTabbedPage extends BasePage {

    @Component
    private Tabs tabs;

    private String tab;

    private String selectedTab;

    public List<String> getTabs() {
        return Arrays.asList("before", "after", "source");
    }

    public String getTab() {
        return tab;
    }

    public void setTab(final String tab) {
        this.tab = tab;
    }

    public String getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(final String selectedTab) {
        this.selectedTab = selectedTab;
    }

    @OnEvent(EventConstants.ACTIVATE)
    protected void activatePage(final String tab) {
        selectedTab = tab;
    }

    @OnEvent(EventConstants.ACTIVATE)
    protected void activatePage() {
        if (selectedTab == null) {
            selectedTab = getTabs().get(0);
        }
    }

    @OnEvent(EventConstants.PASSIVATE)
    protected String passivatePage() {
        return selectedTab;
    }
}
