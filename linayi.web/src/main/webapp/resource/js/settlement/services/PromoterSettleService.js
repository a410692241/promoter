'use strict';
app.service('promoterService', [function () {
    function ajax(options) {
        $.ajax({
            url: options.url,
            data: options.data,
            dataType: options.dataType ? options.dataType : "json",
            type: options.type ? options.type : "post",
            success: options.success,
            contentType: options.contentType ? options.contentType : "application/x-www-form-urlencoded",
            error: options.error ? options.error : function () {
            }
        }).then(function (data) {
            if (options.then) {
                options.then.call(this, data);
            }
        });
    }
}]);