package com.example.ui.pages;

import com.example.ui.base.BasePage;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;

/**
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Deferred extends BasePage {

    @InjectComponent
    private Zone leftZone;

    @InjectComponent
    private Zone rightZone;

    @Property
    private String leftContent;

    @Property
    private String rightContent;

    @Persist
    private int leftTimes;

    @Persist
    private int rightTimes;

    public String getLeftZoneId() {
        return leftZone.getClientId();
    }

    public String getRightZoneId() {
        return rightZone.getClientId();
    }

    @OnEvent(component = "updateLeft")
    Object updateLeftContent() {
        leftContent = format("message.content-text", ++leftTimes);
        return leftZone;
    }

    @OnEvent(component = "updateRight")
    Object updateRightContent() {
        rightContent = format("message.content-text", ++rightTimes);
        return rightZone;
    }
}
