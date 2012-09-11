package com.example.ui.pages.mixin;

import com.example.ui.base.BaseTabbedPage;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class RenderDeferredMixin extends BaseTabbedPage {

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

    public String getBeforeZoneId() {
        return beforeZone.getClientId();
    }

    public String getAfterZoneId() {
        return afterZone.getClientId();
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
}
