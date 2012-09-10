package com.example.ui.pages;

import com.example.ui.base.BasePage;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;

import java.util.Collections;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FixedControlName extends BasePage {

    @Property
    private String leftName;

    @Property
    private String leftDescription;

    @Persist(PersistenceConstants.FLASH)
    @Property
    private String leftCountry;

    @Property
    private String leftCity;

    @Property
    private String rightName;

    @Property
    private String rightDescription;

    @Persist(PersistenceConstants.FLASH)
    @Property
    private String rightCountry;

    @Property
    private String rightCity;

    @InjectComponent
    private Zone leftZone;

    @InjectComponent
    private Zone rightZone;

    public String getLeftZoneId() {
        return leftZone.getClientId();
    }

    public SelectModel getLeftCountryModel() {
        return getCountryModel();
    }

    public SelectModel getLeftCityModel() {
        return getCityModel(leftCountry);
    }

    public String getRightZoneId() {
        return rightZone.getClientId();
    }

    public SelectModel getRightCountryModel() {
        return getCountryModel();
    }

    public SelectModel getRightCityModel() {
        return getCityModel(rightCountry);
    }

    @OnEvent(value = EventConstants.VALUE_CHANGED, component = "leftCountrySelect")
    Object updateLeftZone() {
        return leftZone;
    }

    @OnEvent(value = EventConstants.VALUE_CHANGED, component = "rightCountrySelect")
    Object updateRightZone() {
        return rightZone;
    }

    private SelectModel getCountryModel() {
        return TapestryInternalUtils.toSelectModel("Country-1,Country-2,Country-3");
    }

    private SelectModel getCityModel(final String country) {
        if ("Country-1".equals(country)) {
            return TapestryInternalUtils.toSelectModel("City-1-1,City-1-2");
        } else if ("Country-2".equals(country)) {
            return TapestryInternalUtils.toSelectModel("City-2-1");
        } else {
            return TapestryInternalUtils.toSelectModel(Collections.emptyList());
        }
    }
}
