package com.example.ui.base;

import com.example.ui.components.Tabs;
import org.apache.tapestry5.Block;
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

    private String selectedTab;

    public String getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(final String selectedTab) {
        this.selectedTab = selectedTab;
    }

    public List<String> getTabs() {
        return Arrays.asList("before", "after");
    }

    public Block getTabContent() {
        return getResources().getBlock(getSelectedTab() + "Tab");
    }

    @OnEvent(EventConstants.ACTIVATE)
    protected void activatePage(final String tab) {
        setSelectedTab(tab);
    }

    @OnEvent(EventConstants.ACTIVATE)
    protected void activatePage() {
        if (getSelectedTab() == null) {
            setSelectedTab(getTabs().get(0));
        }
    }

    @OnEvent(EventConstants.PASSIVATE)
    protected String passivatePage() {
        return getSelectedTab();
    }

    @OnEvent(component = "tabs")
    protected void selectTab(final String tab) {
        setSelectedTab(tab);
    }
}
