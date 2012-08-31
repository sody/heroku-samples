package com.example.ui.mixins;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.HeartbeatDeferred;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.dom.Node;
import org.apache.tapestry5.internal.services.MarkupWriterImpl;
import org.apache.tapestry5.internal.services.RenderQueueImpl;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.runtime.RenderCommand;
import org.slf4j.LoggerFactory;

/**
 * This class represents mixin that can be placed on any component that needs to be rendered deferred after all other
 * components will populate their properties.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class RenderDeferred {

    @InjectContainer
    private Component container;

    private Element placeholder;

    /**
     * Prevents container from being rendered in usual render phase. It will render placeholder instead. Later in
     * deferred phase it will render eal container component markup.
     *
     * @param writer markup writer, not {@code null}
     * @return {@code false} in usual render phase to prevent container component from being rendered,
     *         {@code true} in deferred phase
     */
    @SetupRender
    boolean setup(final MarkupWriter writer) {
        if (placeholder != null) {
            // render container markup in deferred phase
            return true;
        }
        // render placeholder element
        // it will mark where the original markup should be placed
        placeholder = writer.element("deferred");
        writer.end();
        // store heartbeat deferred action
        // it will render real container markup
        render();

        // stop rendering container
        return false;
    }

    /**
     * Renders real container component markup in remembered by placeholder location. It is a heartbeat deferred action
     * that will be executed after calling {@link org.apache.tapestry5.services.Heartbeat#end()} method.
     */
    @HeartbeatDeferred
    void render() {
        // create fake render queue and markup writer
        final RenderQueueImpl queue = new RenderQueueImpl(LoggerFactory.getLogger(getClass()));
        final MarkupWriter writer = new MarkupWriterImpl();
        // create root element
        final Element root = writer.element("root");
        // render container component with fake markup writer
        queue.push((RenderCommand) container.getComponentResources());
        queue.run(writer);
        // move rendered elements to real DOM
        for (Node node : root.getChildren()) {
            node.moveBefore(placeholder);
        }
        // remove placeholder
        placeholder.remove();
    }
}
