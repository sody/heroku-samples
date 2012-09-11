package com.example.ui.components;

import com.example.ui.base.BaseComponent;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Tabs extends BaseComponent {

    @Property
    @Parameter(required = true, allowNull = false)
    private List<String> items;

    @Property
    @Parameter
    private String item;

    @Parameter
    private String selected;

    @Inject
    private Block defaultItemBody;

    public String getItemClass() {
        return item.equals(selected) ? "active" : null;
    }

    public String getItemLabel() {
        return label(item);
    }

    public Block getItemBody() {
        final Block body = getResources().getBlockParameter(item + "Tab");
        return body != null ? body : defaultItemBody;
    }
}
