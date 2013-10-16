$('#show_createPatient').click(function() {
    $('#createPatient_pid').val('');
    $('#createPatient_phone').val('');
    $('#createPatient span[id^="error_"]').text('');
    $('#createPatient').modal('show');
});
$('#do_createPatient').click(function() {
    postForm({
        form:$('#form_createPatient'),
        done:function(data) {
            $('#createPatient span[id^="error_"]').text('');
            if (data.errors && data.errors.length) {
                data.errors.forEach(function(err) {
                    $('#error_createPatient_' + err).text(data[err]);
                })
            } else {
                $('#createPatient').modal('hide');
            }
        }
    });
});
$('#patient_new_tab').on('shown.bs.tab', function(e) {
    var pane = $('#patientList_new');
    pane.empty();
    $.getJSON('patient/findNew')
        .done(function(data) {
            data.forEach(function(it) {
                var tr = $('<tr></tr>', {'data-pid':it.pid});
                pane.append(tr
                    .append($('<td></td>').text(it.pid))
                    .append($('<td></td>').text(it.phone))
                    .append($('<td></td>').text(it.createdAt))
                )
            })
        });
});
$('#patient_registered_tab').on('shown.bs.tab', function(e) {
    var pane = $('#patientList_registered');
    pane.empty();
    $.getJSON('patient/findRegistered')
        .done(function(data) {
            data.forEach(function(it) {
                var tr = $('<tr></tr>', {'data-pid':it.pid});
                pane.append(tr
                    .append($('<td></td>').text(it.pid))
                    .append($('<td></td>').text(it.name))
                    .append($('<td></td>').text(it.phone))
                    .append($('<td></td>')
                        .append($('<button class="btn btn-default">详细</button>').click(function(pid) {viewPatientDetails(it.pid)}))
                        .append($('<button class="btn btn-default">创建计划</button>').click(function(pid) {createPlan(it.pid)}))
                    )
                )
            })
        });
});
$('#patient_planned_tab').on('shown.bs.tab', function(e) {
    var pane = $('#patientList_planned');
    pane.empty();
    $.getJSON('patient/findPlanned')
        .done(function(data) {
            data.forEach(function(it) {
                var tr = $('<tr></tr>', {'data-pid':it.pid});
                pane.append(tr
                    .append($('<td></td>').text(it.pid))
                    .append($('<td></td>').text(it.name))
                    .append($('<td></td>').text(it.phone))
                    .append($('<td></td>')
                        .append($('<button class="btn btn-default">详细</button>').click(function(pid) {viewPatientDetails(it.pid)}))
                        .append($('<button class="btn btn-default">修改计划</button>').click(function(pid) {updatePlan(it.pid)}))
                    )
                )
            })
        });
});
$('#do_editPatient').click(function() {
    $.ajax($('#editPatient').data('edit-path'),
        {
            type:'post',
            contentType:'application/json',
            data:JSON.stringify({
                pid:$('#ep_pid_text').text(),
                phone:$('#ep_phone').val(),
                password:$('#ep_password').val(),
                name:$('#ep_name').val(),
                height:$('#ep_height').val(),
                weight:$('#ep_weight').val(),
                bmi:$('#ep_bmi_text').text(),
                sex:$('#ep_sex_male').prop('checked') ? 1 : $('#ep_sex_female').prop('checked') ? 0 : null,
                age:$('#ep_age').val(),
                email:$('#ep_email').val(),
                address:$('#ep_address').val(),
                patientType:$('#ep_patientType').val(),

                surgeryDate:$('#ep_surgeryDate').val(),
                dischargeDate:$('#ep_dischargeDate').val(),
                surgeryTypeId:$('#ep_surgeryType').val(),
                surgeonId:$('#ep_surgeryBy').val(),
                surgeryMemo:$('#ep_surgeryMemo').val()
            })
        })
        .done(function(data) {
            if (data.errors && data.errors.length) {
                data.errors.forEach(function(err) {
                    $('#error_editPatient_' + err).text(data[err]);
                })
            } else {
                $('#editPatient').modal('hide');
            }
        });
});
$('#do_createPlan').click(function() {
    var data = [];
    $('#cpl_plan tr').each(function(i, it) {
        var p = {};
        data.push(p);
        p.stage = i + 1;
        p.days = $(it).find('*[name="days"]').val();
        p.times = $(it).find('*[name="times"]').val();
        p.steps = $(it).find('*[name="steps"]').val();
        p.pressure = $(it).find('*[name="pressure"]').val();
    });
    var cp = $('#createPlan');
    $.ajax(cp.data('update-path') + '/' + cp.data('pid'),
        {
            type:'post',
            contentType:'application/json',
            data:JSON.stringify(data)
        })
        .done(function(data) {
            console.log(data);
            if (data && data.length) {
                var errorPane = $('#createPlan_errors');
                errorPane.empty();
                data.forEach(function(err) {
                    errorPane.append($('<li class="text-danger"></li>').text(err));
                });
            } else {
                $('#createPlan').modal('hide');
            }
        });
});
function viewPatientDetails(pid) {
    $.getJSON($('#editPatient').data('details-path'), {pid:pid})
        .done(function(data) {
            $('#ep_pid_text').html(data.pid);
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

            $('#ep_surgeryDate').val(data.surgeryDate);
            $('#ep_dischargeDate').val(data.dischargeDate);
            $('#ep_surgeryMemo').val(data.surgeryMemo);
            $('#ep_surgeryType').val(data.surgeryTypeId).trigger('change');
            $('#ep_surgeryBy').val(data.surgeonId).trigger('change');

            $('#editPatient').modal('show');
        })
}
function createPlan(pid) {
    $('#createPlan').data('pid', pid);
    $.getJSON($('#editPatient').data('details-path'), {pid:pid})
        .done(function(data) {
            $('#cpl_pid').text(data.pid);
            $('#cpl_name').text(data.name);
            $('#cpl_sex').text(data.sex == 1 ? '男' : data.sex == 0 ? '女' : '');
            $('#cpl_age').text(data.age);
            $('#cpl_height').text(data.height + ' 厘米');
            $('#cpl_weight').text(data.weight + ' 斤');
            $('#cpl_bmi').text(data.bmi);
            $('#cpl_surgeryDate').text(data.surgeryDateString);
            $('#cpl_dischargeDate').text(data.dischargeDateString);
            $('#cpl_surgeryMemo').text(data.surgeryMemo);
            $('#cpl_surgeryName').text(data.surgeryName);
            $('#cpl_surgeonName').text(data.surgeonName);
            $('#cpl_plan').empty();
            $('#createPlan_errors').empty();
            $('#createPlan').modal('show');
        })
}
function updatePlan(pid) {
    $('#createPlan').data('pid', pid);
    $.getJSON($('#editPatient').data('details-path'), {pid:pid})
        .done(function(data) {
            $('#cpl_pid').text(data.pid);
            $('#cpl_name').text(data.name);
            $('#cpl_sex').text(data.sex == 1 ? '男' : data.sex == 0 ? '女' : '');
            $('#cpl_age').text(data.age);
            $('#cpl_height').text(data.height + ' 厘米');
            $('#cpl_weight').text(data.weight + ' 斤');
            $('#cpl_bmi').text(data.bmi);
            $('#cpl_surgeryDate').text(data.surgeryDateString);
            $('#cpl_dischargeDate').text(data.dischargeDateString);
            $('#cpl_surgeryMemo').text(data.surgeryMemo);
            $('#cpl_surgeryName').text(data.surgeryName);
            $('#cpl_surgeonName').text(data.surgeonName);
            $('#cpl_plan').empty();
            $('#createPlan_errors').empty();
            $.getJSON('patient/plans/' + pid)
                .done(function(data) {
                    data.forEach(function(it) {
                        var tr = $('<tr></tr>')
                            .append($('<td></td>').append($('<p class="form-control-static" name="index"></p>').text(it.stage)))
                            .append($('<td></td>').append($('<input type="number" class="form-control input-sm" name="days" value="7">').val(it.days)))
                            .append($('<td></td>').append($('<input type="number" class="form-control input-sm" name="times" value="3">').val(it.times)))
                            .append($('<td></td>').append($('<input type="number" class="form-control input-sm" name="steps">').val(it.steps)))
                            .append($('<td></td>').append($('<input type="number" class="form-control input-sm" name="pressure">').val(it.pressure)))
                            .append($('<td></td>').append($('<button class="btn btn-sm btn-danger" onclick="removePlanStep(this)">删除</button>')))
                            .appendTo($('#cpl_plan'));
                    });
                    $('#createPlan').modal('show');
                })
        })
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
    var stage = $('#cpl_plan tr').length + 1;
    var tr = $('<tr></tr>')
        .append($('<td></td>').append($('<p class="form-control-static" name="index"></p>').text(stage)))
        .append($('<td></td>').append($('<input type="number" class="form-control input-sm" name="days" value="7">')))
        .append($('<td></td>').append($('<input type="number" class="form-control input-sm" name="times" value="3">')))
        .append($('<td></td>').append($('<input type="number" class="form-control input-sm" name="steps">')))
        .append($('<td></td>').append($('<input type="number" class="form-control input-sm" name="pressure">')))
        .append($('<td></td>').append($('<button class="btn btn-sm btn-danger" onclick="removePlanStep(this)">删除</button>')))
        .appendTo($('#cpl_plan'));
}
function removePlanStep(btn) {
    $(btn).parent().parent().remove();
    var i = 0;
    $('#cpl_plan p[name="index"]').each(function(index, it) {
        $(it).text(++i);
    })
}