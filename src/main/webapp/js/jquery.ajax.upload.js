(function ($) {
    var uuid = 0;

    $.fn.upload = function (data, callback) {
        var $originalForm = this.parents("form"),
            $originalFile = this.clone().insertBefore(this),
            $target = $("<iframe>", {
                name:"upload_" + ++uuid
            }).hide().appendTo("body"),
            $form = $("<form>", {
                target:$target.attr("name"),
                method:"post",
                enctype:"multipart/form-data",
                action:$originalForm.attr("action")
            }).hide().appendTo("body"),
            $file = this.appendTo($form);

        if ($.isFunction(data)) {
            callback = data;
            data = $originalForm.serializeArray();
        }

        $.each(data, function () {
            $("<input>", {
                type:"hidden",
                name:this.name,
                value:this.value
            }).appendTo($form);
        });

        $form.on("submit",function () {
            $target.on("load", function () {
                var content = $target.contents().get(0),
                    response = $(content).find("body").text();
                $target.remove();
                callback.call($file, response);
            });
        }).submit();

        return this;
    };
})(jQuery);
