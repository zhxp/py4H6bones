function postForm(config) {
    var form = config.form;
    $.ajax(
        form.prop('action'),
        {
            type:'post',
            data:form.serialize()
        }).done(config.done)
}