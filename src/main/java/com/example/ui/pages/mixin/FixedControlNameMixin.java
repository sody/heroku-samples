package com.example.ui.pages.mixin;

import com.example.ui.base.BaseTabbedPage;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.internal.TapestryInternalUtils;

import java.util.Collections;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FixedControlNameMixin extends BaseTabbedPage {

    @Property
    private String name;

    @Property
    private String description;

    @Persist(PersistenceConstants.FLASH)
    @Property
    private String country;

    @Property
    private String city;

    @InjectComponent
    private Zone beforeZone;

    @InjectComponent
    private Zone afterZone;

    public SelectModel getCountryModel() {
        return TapestryInternalUtils.toSelectModel("Country-1,Country-2,Country-3");
    }

    public SelectModel getCityModel() {
        if ("Country-1".equals(country)) {
            return TapestryInternalUtils.toSelectModel("City-1-1,City-1-2");
        } else if ("Country-2".equals(country)) {
            return TapestryInternalUtils.toSelectModel("City-2-1");
        } else {
            return TapestryInternalUtils.toSelectModel(Collections.emptyList());
        }
    }

    public String getBeforeZoneId() {
        return beforeZone.getClientId();
    }

    public String getAfterZoneId() {
        return afterZone.getClientId();
    }

    @OnEvent(value = EventConstants.VALUE_CHANGED, component = "beforeCountrySelect")
    Object updateBeforeZone() {
        return beforeZone;
    }

    @OnEvent(value = EventConstants.VALUE_CHANGED, component = "afterCountrySelect")
    Object updateAfterZone() {
        return afterZone;
    }

    @OnEvent(EventConstants.SUCCESS)
    void clearForm() {
         country = null;
    }
}
