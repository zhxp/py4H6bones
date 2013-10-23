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
$('#patient_registered_tab').on('shown.bs.tab', function (e) {
    loadRegistered()
});
$('#patient_planned_tab').on('shown.bs.tab', function (e) {
    loadPlanned()
});
$('#patient_trained_tab').on('shown.bs.tab', function (e) {
    loadTrainedPatients()
});
loadTrainedPatients();
function loadRegistered() {
    var pane = $('#patientList_registered');
    pane.empty();
    $.getJSON(pane.data('url'))
        .done(function (data) {
            data.forEach(function (it) {
                var tr = $('<tr></tr>', {'data-pid': it.pid});
                pane.append(tr
                    .append($('<td></td>').text(it.pid))
                    .append($('<td></td>').text(it.name))
                    .append($('<td></td>').text(it.phone))
                    .append($('<td></td>')
                        .append($('<button class="btn btn-link">编辑</button>').click(function (pid) {
                            editPatient(it.pid)
                        }))
                    )
                )
            })
        });
}
function loadPlanned() {
    var pane = $('#patientList_planned');
    pane.empty();
    $.getJSON(pane.data('url'))
        .done(function (data) {
            data.forEach(function (it) {
                var tr = $('<tr></tr>', {'data-pid': it.pid});
                pane.append(tr
                    .append($('<td></td>').text(it.pid))
                    .append($('<td></td>').text(it.name))
                    .append($('<td></td>').text(it.phone))
                    .append($('<td></td>')
                        .append($('<button class="btn btn-link">编辑</button>').click(function (pid) {
                            editPatient(it.pid)
                        }))
                    )
                )
            })
        });
}
function loadTrainedPatients() {
    var pane = $('#patientList_trained');
    pane.empty();
    $.getJSON(pane.data('url'))
        .done(function (data) {
            data.forEach(function (it) {
                var tr = $('<tr></tr>', {'data-pid': it.pid});
                pane.append(tr
                    .append($('<td></td>').text(it.pid))
                    .append($('<td></td>').text(it.name))
                    .append($('<td></td>').text(it.surgeryDate))
                    .append($('<td></td>').text(it.surgeryName))
                    .append($('<td></td>').text('如何计算?'))
                    .append($('<td></td>').text(it.feeling))
                    .append($('<td></td>').text(it.effect))
                    .append($('<td></td>').text(it.reaction))
                    .append($('<td></td>')
                        .append($('<button class="btn btn-link">编辑</button>').click(function (pid) {
                            editPatient(it.pid)
                        }))
                        .append($('<button class="btn btn-link">训练明细</button>').click(function (pid) {
                            viewTraining(it.pid)
                        }))
                    )
                )
            })
        });
}
function loadFinished() {

}
function createPatient() {
    $('#ep_id').val('');
    //basic
    $('#ep_pid').val('');
    $('#ep_name').val('');
    $('#ep_password').val('');
    $('#ep_phone').val('');
    $('#ep_email').val('');
    $('#ep_height').val('');
    $('#ep_weight').val('');
    $('#ep_bmi_text').html('');
    $('#ep_address').val('');
    $('#ep_age').val('');
    $('#ep_sex_male').prop('checked', false);
    $('#ep_sex_female').prop('checked', false);
    ep_changeSexColor();

    //surgery
    $('#ep_surgeryDate').val('');
    $('#ep_dischargeDate').val('');
    $('#ep_surgeryMemo').val('');
    $('#ep_surgeryType').val('');
    $('#ep_doctor').val('');

    //plan
    $('#ep_plans').empty();

    $('#editPatient').modal('show');
}
function editPatient(pid) {
    $.getJSON($('#urls').data('patient_bean') + pid)
        .done(function (data) {
            $('#ep_id').val(data.id)
            //basic
            $('#ep_pid').val(data.pid);
            $('#ep_name').val(data.name);
            $('#ep_password').val(data.password);
            $('#ep_phone').val(data.phone);
            $('#ep_email').val(data.email);
            $('#ep_height').val(data.height);
            $('#ep_weight').val(data.weight);
            $('#ep_bmi_text').html(data.bmi);
            $('#ep_address').val(data.address);
            $('#ep_age').val(data.age);
            $('#ep_patientType').val(data.patientType).trigger('change');
            $('#ep_sex_male').prop('checked', data.sex == 1);
            $('#ep_sex_female').prop('checked', data.sex == 0);
            ep_changeSexColor();

            //surgery
            $('#ep_surgeryDate').val(data.surgeryDate);
            $('#ep_dischargeDate').val(data.dischargeDate);
            $('#ep_surgeryMemo').val(data.surgeryMemo);
            $('#ep_surgeryType').val(data.surgeryTypeId).trigger('change');
            $('#ep_doctor').val(data.doctorId).trigger('change');

            //plan
            $('#ep_plans').empty();
            data.plans.forEach(function (it) {
                var tr = $('<tr></tr>', {'data-id': it.id})
                        .append($('<td></td>').append($('<p class="form-control-static" name="index"></p>').text(it.stage)))
                        .append($('<td></td>').append($('<input type="number" class="form-control input-sm" name="days" value="7">').val(it.days).prop('readonly', !!it.started)))
                        .append($('<td></td>').append($('<input type="number" class="form-control input-sm" name="times" value="3">').val(it.times).prop('readonly', !!it.started)))
                        .append($('<td></td>').append($('<input type="number" class="form-control input-sm" name="steps">').val(it.steps).prop('readonly', !!it.started)))
                        .append($('<td></td>').append($('<input type="number" class="form-control input-sm" name="pressure">').val(it.pressure).prop('readonly', !!it.started)))
                        .append($('<td></td>').append($('<textarea class="form-control input-sm" name="memo" rows="1" cols="100">').text(it.memo).prop('readonly', !!it.started)))
                    ;
                if (!it.started) {
                    tr.append($('<td></td>').append($('<button class="btn btn-sm btn-danger" onclick="removePlanStep(this)">删除</button>')))
                }
                tr.appendTo($('#ep_plans'));
            });

            $('#editPatient').modal('show');
        })
}
function viewTraining(pid) {
    $.getJSON($('#urls').data('patient_training') + pid)
        .done(function (data) {
            var list = $('#vt_list');
            list.empty();
            var url = list.data('url')
            data.forEach(function (it) {
                var tr = $('<tr></tr>', {'data-id': it.id})
                        .append($('<td></td>').append($('<p class="form-control-static"></p>').text(it.date)))
                        .append($('<td></td>').append($('<p class="form-control-static"></p>').text(it.stage)))
                        .append($('<td></td>').append($('<p class="form-control-static"></p>').text(it.steps + '/' + it.planSteps)))
                        .append($('<td></td>').append($('<p class="form-control-static"></p>').text(it.averagePressure + '/' + it.PlanPressure)))
                        .append($('<td></td>').append($('<p class="form-control-static"></p>').text(it.feeling)))
                        .append($('<td></td>').append($('<p class="form-control-static"></p>').text(it.effect)))
                        .append($('<td></td>').append($('<p class="form-control-static"></p>').text(it.reaction)))
                        .append($('<td></td>').append($('<p class="form-control-static"></p>').text(it.overrunRate + '%')))
                        .append($('<td></td>').append($('<p class="form-control-static"></p>').text(it.hasMemo ? '有' : '')))
                        .append($('<td></td>')
                            .append($('<a target="_blank" href="' + url + it.id + '" class=" btn btn-link">图表</button>'))
                        )
                    ;
                tr.appendTo(list);
            });

            $('#viewTraining').modal('show');
        })
}
function validatePatient() {
    var message = '';
    if (!$('#ep_pid').val().trim()) message += '病人标识不能为空';
    if (!$('#ep_password').val().trim()) message += '<br>密码不能为空';
    if (!$('#ep_sex_male').prop('checked') && !$('#ep_sex_female').prop('checked')) message += '<br>性别不能为空';
    if (!$('#ep_height').val()) message += '<br>身高不能为空';
    if (!$('#ep_weight').val()) message += '<br>体重不能为空';
    if (message) {
        bootbox.alert(message);
        return false;
    }
    return true;
}
function savePatient() {
    if (!validatePatient()) return false;
    var data = {
        //basic
        id: $('#ep_id').val(),
        pid: $('#ep_pid').val(),
        height: $('#ep_height').val(),
        sex: $('#ep_sex_male').prop('checked') ? 1 : $('#ep_sex_female').prop('checked') ? 0 : null,
        name: $('#ep_name').val(),
        weight: $('#ep_weight').val(),
        age: $('#ep_age').val(),
        password: $('#ep_password').val(),
        bmi: $('#ep_bmi_text').text(),
        address: $('#ep_address').val(),
        phone: $('#ep_phone').val(),
        email: $('#ep_email').val(),

        //surgery
        surgeryDate: $('#ep_surgeryDate').val(),
        dischargeDate: $('#ep_dischargeDate').val(),
        surgeryMemo: $('#ep_surgeryMemo').val(),
        surgeryTypeId: $('#ep_surgeryType').val(),
        doctorId: $('#ep_doctor').val(),

        //plan
        plans: []
    };
    $('#ep_plans > tr').each(function (i, it) {
        var p = {};
        data.plans.push(p);
        var node = $(it);
        p.id = node.data('id')
        p.stage = i + 1;
        p.days = node.find('*[name="days"]').val();
        p.times = node.find('*[name="times"]').val();
        p.steps = node.find('*[name="steps"]').val();
        p.pressure = node.find('*[name="pressure"]').val();
        p.memo = node.find('*[name="memo"]').val();
    });
    $.ajax($('#urls').data('patient_save'),
        {
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(data)
        })
        .done(function (data) {
            if (data.errors && data.errors.length) {
                var message = '';
                data.errors.forEach(function (it) {
                    message += '<br>' + it
                });
                bootbox.alert(message);
            } else {
                var activeId = $('#patientTabs > li.active a').prop('id');
                if (activeId == 'patient_registered_tab') loadRegistered();
                else if (activeId == 'patient_planned_tab') loadPlanned();
                else if (activeId == 'patient_trained_tab') loadTrainedPatients();
                else if (activeId == 'patient_finished_tab') loadFinished();
                $('#editPatient').modal('hide');
            }
        })
        .fail(function (jqXHR) {
            bootbox.alert(jqXHR.statusText);
        });
    return false;
}
function ep_calculateBMI() {
    var height = $('#ep_height').val();
    var weight = $('#ep_weight').val();
    var bmi = 0;
    if (height && height > 0) {
        height = height / 100.0;
        weight = weight / 2;
        bmi = weight / height / height;
    }
    $('#ep_bmi_text').text(bmi);
}
function ep_changeSexColor() {
    var male = $('#ep_sex_male'), maleLabel = $('#ep_sex_male_label');
    var female = $('#ep_sex_female'), femaleLabel = $('#ep_sex_female_label');
    if (male.prop('checked')) {
        maleLabel.removeClass('btn-default').addClass('btn-info active');
    } else {
        maleLabel.removeClass('btn-info active').addClass('btn-default');
    }
    if (female.prop('checked')) {
        femaleLabel.removeClass('btn-default').addClass('btn-info active');
    } else {
        femaleLabel.removeClass('btn-info active').addClass('btn-default');
    }
}
function addPlanStep() {
    var stage = $('#ep_plans > tr').length + 1;
    var tr = $('<tr></tr>')
        .append($('<td></td>').append($('<p class="form-control-static" name="index"></p>').text(stage)))
        .append($('<td></td>').append($('<input type="number" class="form-control input-sm" name="days" value="7">')))
        .append($('<td></td>').append($('<input type="number" class="form-control input-sm" name="times" value="3">')))
        .append($('<td></td>').append($('<input type="number" class="form-control input-sm" name="steps">')))
        .append($('<td></td>').append($('<input type="number" class="form-control input-sm" name="pressure">')))
        .append($('<td></td>').append($('<textarea class="form-control input-sm" name="memo" rows="1" cols="100">')))
        .append($('<td></td>').append($('<button class="btn btn-sm btn-danger" onclick="removePlanStep(this)">删除</button>')))
        .appendTo($('#ep_plans'));
}
function removePlanStep(btn) {
    $(btn).parent().parent().remove();
    var i = 0;
    $('#ep_plans p[name="index"]').each(function (index, it) {
        $(it).text(++i);
    })
}