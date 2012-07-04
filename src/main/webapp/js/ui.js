T5.extendInitializers({
    buttons:function () {
        (function ($) {
            // create buttons from every element of 'ui-button' class
            $(".ui-button").each(function () {
                // get element
                var element = $(this);
                // find element representing primary icon ang parse it's class attribute
                var primary = element.find(".ui-button-icon-primary")
                    .removeClass("ui-button-icon-primary ui-button-icon-secondary ui-icon")
                    .attr("class");
                // find element representing secondary icon ang parse it's class attribute
                var secondary = element.find(".ui-button-icon-secondary")
                    .removeClass("ui-button-icon-primary ui-button-icon-secondary ui-icon")
                    .attr("class");
                // find element representing button text and get it's content
                var label = element.find(".ui-button-text").html();
                // check if element should have text
                var text = !element.hasClass("ui-button-icon-only") && !element.hasClass("ui-button-icons-only");
                // if element is label we should create button from input it references for
                if (element.is("label")) {
                    element = $("#" + element.attr("for"));
                }
                // create button
                element.button({
                    text:text,
                    label:label,
                    icons:{
                        primary:primary,
                        secondary:secondary
                    }
                });
            });

            // for demo only - create usual buttons from every element of 'button' class
            $(".button").button();
        })(jQuery);
    },

    modal:function (spec) {
        (function ($) {
            $("#" + spec.id).modal("show");
        })(jQuery);
    },

    ajaxUpload:function (spec) {
        // find tapestry form
        var tapestryForm = $(spec.formId);
        // bind on submit form event
        tapestryForm.observe(Tapestry.FORM_PREPARE_FOR_SUBMIT_EVENT, function () {
            // find jquery form
            var $form = j$("#" + spec.formId);
            // find all input files
            var $files = $form.find("input:file");
            // if not empty files upload it in separate iframe
            if ($files.val()) {
                var url = $form.attr("action");
                // copy form submit values to form inside the iframe
                // all " characters should be converted to ' because of upload plugin uses "
                var data = $form.serializeArray()
                    .map(function (entry) {
                        return {
                            name:entry.name,
                            value:entry.value.replace(/"/g, '\'')
                        };
                    });
                // add flag to simulate ajax request
                data.push({ name:"XHR_EMULATION", value:true });
                // stop observing submit event to prevent usual tapestry form submission
                tapestryForm.stopObserving(Tapestry.FORM_PROCESS_SUBMIT_EVENT);
                // connect to tapestry form submission event
                tapestryForm.observe(Tapestry.FORM_PROCESS_SUBMIT_EVENT, function () {
                    // clear submit event listener to prevent infinite loop
                    tapestryForm.onsubmit = null;
                    // upload files in iframe using jquery plugin
                    $files.upload(url, data, function (transport) {
                        // parse json response
                        var response = j$.parseJSON(j$(transport).text());
                        // update zone with POST result
                        var zone = Tapestry.findZoneManager(spec.formId);
                        zone.processReply(response);
                    });
                });
            }
        });
    }
});