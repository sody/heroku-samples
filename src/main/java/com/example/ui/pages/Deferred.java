package com.example.ui.pages;

import com.example.ui.base.BasePage;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Deferred extends BasePage {

    @InjectComponent
    private Zone beforeZone;

    @InjectComponent
    private Zone afterZone;

    @Property
    private String beforeContent;

    @Property
    private String afterContent;

    @Persist
    private int beforeTimes;

    @Persist
    private int afterTimes;

    @Property
    private String selectedTab;

    public String getBeforeZoneId() {
        return beforeZone.getClientId();
    }

    public String getAfterZoneId() {
        return afterZone.getClientId();
    }

    public List<String> getTabs() {
        return Arrays.asList("before", "after");
    }

    public Block getContent() {
        return getResources().getBlock(selectedTab + "Block");
    }

    @OnEvent(component = "updateBefore")
    Object updateBeforeContent() {
        beforeContent = format("message.content-text", ++beforeTimes);
        return beforeZone;
    }

    @OnEvent(component = "updateAfter")
    Object updateAfterContent() {
        afterContent = format("message.content-text", ++afterTimes);
        return afterZone;
    }

    @OnEvent(EventConstants.ACTIVATE)
    void activatePage(final String tab) {
        selectedTab = tab;
    }

    @OnEvent(EventConstants.ACTIVATE)
    void activatePage() {
        if (selectedTab == null) {
            selectedTab = "before";
        }
    }

    @OnEvent(EventConstants.PASSIVATE)
    String passivatePage() {
        return selectedTab;
    }

    @OnEvent(component = "tabs")
    void selectTab(final String tab) {
        selectedTab = tab;
    }
}
