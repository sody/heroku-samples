package com.example.ui.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.AfterRenderBody;
import org.apache.tapestry5.annotations.BeforeRenderBody;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.internal.FormSupportAdapter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.util.IdAllocator;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.FormSupport;

/**
 * This class represents fixin that disables dynamic form control name generation by
 * {@link org.apache.tapestry5.ioc.util.IdAllocator} service, so form control names are the same during usual and
 * ajax requests. It fixes <a href="https://issues.apache.org/jira/browse/TAP5-1512">TAP5-1512</a> issue:
 * form controls have changed names after zone update ({@link org.apache.tapestry5.ioc.util.IdAllocator} adds ajax
 * namespace to all generated ids and names), so this controls doesn't have error markup and doesn't restore
 * previous state after validation fail.
 * <p/>
 * Usage:
 * <p/>
 * <pre><code>
 * &lt;t:zone t:id="zoneId" t:mixins="FixedControlName"&gt;
 *     ...
 *     &lt;select t:id="selectId" zone="id:zoneId" .../&gt;
 *     ...
 * &lt;/t:zone&gt;
 * </code></pre>
 * NOTE: use explicit {@code t:id} or {@code id} attributes on form components to prevent form control name conflicts.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FixedControlName {

    /**
     * This parameter defines namespace for control names. It will use {@code fixed} namespace by default.
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL, value = "fixed", allowNull = false)
    private String namespace;

    @Inject
    private Environment environment;

    private boolean insideForm;

    @BeforeRenderBody
    void changeFormSupport() {
        final FormSupport existingFormSupport = environment.peek(FormSupport.class);
        insideForm = existingFormSupport != null;
        if (insideForm) {
            // replace FormSupport with hacked wrapper
            environment.push(FormSupport.class, new FormSupportAdapter(existingFormSupport) {
                private final IdAllocator allocator = new IdAllocator("_" + namespace);

                @Override
                public String allocateControlName(final String id) {
                    return allocator.allocateId(id);
                }
            });
        }
    }

    @AfterRenderBody
    void restoreFormSupport() {
        if (insideForm) {
            // remove hacked FormSupport wrapper
            environment.pop(FormSupport.class);
        }
    }
}
