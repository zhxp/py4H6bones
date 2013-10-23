function showChangePasswordDialog() {
    $('#cpwd_oldPassword').val('');
    $('#cpwd_newPassword').val('');
    $('#cpwd_newPassword2').val('');
    $('#changePasswordDialog').modal('show');
}
function changePassword() {
    var p = $('#cpwd_newPassword').val();
    var p2 = $('#cpwd_newPassword2').val();
    if (p != p2) {
        bootbox.alert('两次输入新密码不一致');
    } else if (p == '') {
        bootbox.alert('密码不能为空');
    } else {
        $.ajax($('#changePasswordDialog').data('url'),
            {
                type:'post',
                data:{oldPassword:$('#cpwd_oldPassword').val(), newPassword:p}
            }
        ).done(function(data) {
                if (data.errors && data.errors.length) {
                    var msg = '';
                    data.errors.forEach(function(it) {
                        msg += '<br>' + it;
                    })
                    bootbox.alert(msg);
                } else {
                    $('#changePasswordDialog').modal('hide');
                }
            })
    }
    return false;
}
$('#doctorTabId').on('shown.bs.tab', function (e) {
    loadDoctors();
});
function loadDoctors() {
    var pane = $('#doctorList');
    pane.empty();
    $.getJSON(pane.data('url'))
        .done(function (data) {
            data.forEach(function (it) {
                var tr = $('<tr></tr>', {'data-id': it.id});
                pane.append(tr
                    .append($('<td></td>').text(it.username))
                    .append($('<td></td>').text(it.displayName))
                    .append($('<td></td>').text(it.supervisorName + ' - ' + it.supervisorDisplayName))
                    .append($('<td></td>')
                        .append($('<button class="btn btn-link">编辑</button>').click(function () {
                            editDoctor(it.id, it.name)
                        }))
                    )
                )
            })
        });
}
loadDoctors();
$('#surgeryTabId').on('shown.bs.tab', function() {
    loadSurgery();
});
function loadSurgery() {
    var pane = $('#surgeryList');
    pane.empty();
    $.getJSON(pane.data('url'))
        .done(function(data) {
            data.forEach(function(it) {
                var tr = $('<tr></tr>', {'data-id': it.id});
                pane.append(tr
                    .append($('<td></td>').text(it.name))
                    .append($('<td></td>')
                        .append($('<button class="btn btn-link">编辑</button>').click(function() {
                            editSurgery(it.id, it.name)
                        }))
                    )
                )
            })
        })
}
function createDoctor() {
    $('#ed_id').val('');
    $('#ed_username').val('');
    $('#ed_displayName').val('');
    $('#ed_password').val('').prop('placeholder', '创建密码');
    $('#ed_supervisor').val('');
    var url = $('#editDoctorDialog').data('supervisors');
    $.getJSON(url, {userId: null})
        .done(function(data) {
            var pane = $('#ed_supervisor');
            pane.empty();
            pane.append($('<option></option>'));
            data.forEach(function(it) {
                pane.append($('<option></option>').prop('value', it.id).text(it.username));
            });
            pane.trigger('change');
            $('#editDoctorDialog').modal('show');
        })
}
function editDoctor(id) {
    $.getJSON($('#editDoctorDialog').data('details') + id)
        .done(function(data) {
            $.getJSON($('#editDoctorDialog').data('supervisors'), {userId: id})
                .done(function(docs) {
                    var pane = $('#ed_supervisor');
                    pane.empty();
                    pane.append($('<option></option>'));
                    docs.forEach(function(it) {
                        pane.append($('<option></option>').prop('value', it.id).text(it.username + ' - ' + it.displayName));
                    });
                    $('#ed_id').val(data.id);
                    $('#ed_username').val(data.username);
                    $('#ed_displayName').val(data.displayName);
                    $('#ed_password').val('').prop('placeholder', '修改密码');
                    $('#ed_supervisor').val(data.supervisorId);
                    pane.trigger('change');
                    $('#editDoctorDialog').modal('show');
                })
        })
}
function saveDoctor() {
    if (validateDoctor()) {
        var doc = {
            id: $('#ed_id').val(),
            password: $('#ed_password').val(),
            username: $('#ed_username').val(),
            displayName: $('#ed_displayName').val(),
            supervisorId: $('#ed_supervisor').val()
        }
        $.ajax($('#editDoctorDialog').data('url'),
            {
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify(doc)
            })
            .done(function (data) {
                if (data.errors && data.errors.length) {
                    var message = '';
                    data.errors.forEach(function (it) {
                        message += '<br>' + it
                    });
                    bootbox.alert(message);
                } else {
                    $('#editDoctorDialog').modal('hide');
                    loadDoctors();
                }
            })
            .fail(function (jqXHR) {
                bootbox.alert(jqXHR.statusText);
            });
    }
    return false;
}
function validateDoctor() {
    var message = '';
    if (!$('#ed_username').val().trim()) message += '登录名不能为空';
    if (!$('#ed_displayName').val().trim()) message += '<br>姓名不能为空';
    if (!$('#ed_id').val() && !$('#ed_password').val()) message += '<br>密码不能为空';
    if (message) {
        bootbox.alert(message);
        return false;
    }
    return true;
}
function createSurgery() {
    $('#es_id').val('');
    $('#es_name').val('');
    $('#editSurgeryDialog').modal('show');
}
function editSurgery(id, name) {
    $('#es_id').val(id);
    $('#es_name').val(name);
    $('#editSurgeryDialog').modal('show');
}
function saveSurgery() {
    if (validateSurgery()) {
        var data = {id: $('#es_id').val(), name: $('#es_name').val()}
        var dialog = $('#editSurgeryDialog');
        $.ajax(dialog.data('url'),
            {
                type:'post',
                contentType: 'application/json',
                data:JSON.stringify(data)
            })
            .done(function(data) {
                loadSurgery();
                dialog.modal('hide');
            })
            .fail(function(jqXHR) {
                bootbox.alert(jqXHR.statusText)
            })
    }
    return false;
}
function validateSurgery() {
    if (!$('#es_name').val().trim()) {
        bootbox.alert('手术名称不能为空');
        return false;
    }
    return true;
}