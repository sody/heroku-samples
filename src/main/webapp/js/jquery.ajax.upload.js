(function ($) {
    var uuid = 0;
    var html5 = false;

    $.fn.upload = function (options) {
        var self = this,
            deferred = $.Deferred(),
            $file = self.find("input:file");

        options = options || {};
        self.promise = deferred.promise;

        var values = self.serializeArray();
        if (options.data) {
            $.each(options.data, function (name) {
                values.push({
                    name:name,
                    value:this
                })
            });
        }

        if (!html5) {
            var $target = $("<iframe>", {
                    name:"upload_" + ++uuid
                }).hide().appendTo("body"),
                $form = $("<form>", {
                    target:$target.attr("name"),
                    method:"post",
                    enctype:"multipart/form-data",
                    encoding:"multipart/form-data",
                    action:self.attr("action")
                }).hide().appendTo("body");

            // clone file inputs and attach them to original form
            $file.clone().before($file);
            // attach original file inputs to newly created form
            $file.appendTo($form);
            // add flag to simulate ajax request
            values.push({ name:"XHR_EMULATION", value:true });
            // create hidden inputs for all data entries and attach them to form
            $.each(values, function () {
                $("<input>", {
                    type:"hidden",
                    name:this.name,
                    value:this.value
                }).appendTo($form);
            });

            // submit form
            $form.on("submit",function () {
                $target.on("load", function () {
                    // when iframe is loaded cache its contents and remove it
                    var content = $target.contents().text(),
                        response = $.parseJSON(content);
                    $target.remove();
                    $form.remove();
                    deferred.resolve(response);
                });
            }).submit();
        } else {
            $.ajax({
                url:self.attr("action"),
                type:"post",
                data:new FormData(self.get(0)),
                //Options to tell JQuery not to process data or worry about content-type
                cache:false,
                contentType:false,
                processData:false
            }).then(deferred.resolve, deferred.reject);
        }

        return this;
    };
})(jQuery);
