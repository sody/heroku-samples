(function ($) {
    var uuid = 0;
    var supportsFormData = typeof(FormData) == "function" && typeof(FormData.prototype) == "object";

    if (supportsFormData) {
        // for html5 we can use FormData approach
        $.fn.upload = function (options) {
            var self = this,
                data = new FormData(self.get(0)),
                deferred = $.Deferred();

            options = options || {};
            self.promise = deferred.promise;

            // add additional parameters to form data
            $.each(options.data || {}, function (name) {
                data.append(name, this);
            });

            // ajax post
            $.ajax({
                url:self.attr("action"),
                type:"post",
                data:new FormData(self.get(0)),
                //Options to tell JQuery not to process data or worry about content-type
                cache:false,
                contentType:false,
                processData:false
            }).then(deferred.resolve, deferred.reject, deferred.notify);

            return this;
        };
    } else {
        // for no html5 we can emulate ajax request via iframe
        $.fn.upload = function (options) {
            var self = this,
                data = self.serializeArray(),
                deferred = $.Deferred(),
                $file = self.find("input:file"),
                $target = $("<iframe>", {
                    name:"upload_" + ++uuid
                }).hide().appendTo("body"),
                $form = $("<form>", {
                    target:$target.attr("name"),
                    method:"post",
                    enctype:"multipart/form-data",
                    encoding:"multipart/form-data",
                    action:self.attr("action")
                }).hide().appendTo("body");

            options = options || {};
            self.promise = deferred.promise;

            // add additional parameters to form data
            $.each(options.data || {}, function (name) {
                data.push({ name:name, value:this });
            });

            // clone file inputs and attach original to newly created form
            $file.after(function() {
                return this.clone();
            }).appendTo($form);

            // add flag to simulate ajax request
            data.push({ name:"XHR_EMULATION", value:true });
            // create hidden inputs for all data entries and attach them to form
            $.each(data, function () {
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
                    $form.remove();
                    $target.remove();
                    deferred.resolve(response);
                });
            }).submit();

            return this;
        };
    }
})(jQuery);
