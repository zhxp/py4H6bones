<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="cmn">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/datetimepicker/bootstrap-datetimepicker.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/select2/select2.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/select2/select2-bootstrap.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/morris/morris.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/site.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/user/home.css"/>">
</head>
<body>

<div class="navbar navbar-fixed-top navbar-inverse">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <span class="navbar-brand">第六人民医院 骨科</span>
    </div>
    <div class="navbar-collapse collapse">
        <span class="navbar-text">
            <c:forEach var="it" items="${docTree}">
                <c:if test="${it.supervisor != null}">&emsp;&gt;&emsp;</c:if>
                <a href="<c:url value="/home/${it.id}"/>">${it.displayName}</a>
            </c:forEach>
        </span>
        <span class="navbar-text navbar-right">
            <span><button class="btn btn-link" onclick="showChangePasswordDialog()">修改密码</button></span>
            <span><a href="<c:url value="/logout"/>" class="btn btn-link">退出</a></span>
        </span>
    </div>
</div>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading">下属医生</div>
        <div class="panel-body">
            <c:forEach var="it" items="${employees}">
                <a target="_blank" href="<c:url value="/home/${it.id}"/>" class="btn btn-link">${it.displayName}</a>
            </c:forEach>
        </div>
    </div>
</div>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading">
            <span>病人情况</span>
            <button type="button" class="btn btn-link" id="show_createPatient" onclick="createPatient()">+ 添加病人</button>
        </div>
        <div class="panel-body">
            <ul class="nav nav-tabs nav-tabs-justified" id="patientTabs">
                <li><a href="#registered" data-toggle="tab" id="patient_registered_tab">已注册</a></li>
                <li><a href="#planned" data-toggle="tab" id="patient_planned_tab">已计划</a></li>
                <li class="active"><a href="#trained" data-toggle="tab" id="patient_trained_tab">已训练</a></li>
                <li><a href="#finished" data-toggle="tab" id="patient_finished_tab">已完成</a></li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane fade" id="registered">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>病人标识</th>
                                <th>姓名</th>
                                <th>性别</th>
                                <th>年龄</th>
                                <th>身高</th>
                                <th>体重</th>
                                <th>电话</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody id="patientList_registered" data-url="<c:url value="/patient/registered/${doctor.id}"/>">
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="tab-pane fade" id="planned">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>病人标识</th>
                                <th>姓名</th>
                                <th>BMI</th>
                                <th>手术日期</th>
                                <th>手术名称</th>
                                <th>出院日期</th>
                                <th>主管医生</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody id="patientList_planned" data-url="<c:url value="/patient/planned/${doctor.id}"/>">
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="tab-pane fade active in" id="trained">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>病人标识</th>
                                <th>姓名</th>
                                <th>手术日期</th>
                                <th>手术名称</th>
                                <th>完成情况</th>
                                <th>总体感受</th>
                                <th>训练强度</th>
                                <th>不良反应</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody id="patientList_trained" data-url="<c:url value="/patient/trained/${doctor.id}"/>">
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="tab-pane fade" id="finished">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>病人标识</th>
                                <th>姓名</th>
                                <th>手术日期</th>
                                <th>手术名称</th>
                                <th>出院日期</th>
                                <th>主管医生</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody id="patientList_finished" data-url="<c:url value="/patient/finished/${doctor.id}"/>">
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="editPatient" class="modal fade" role="dialog" data-backdrop="static">
    <div class="modal-dialog" style="width: 1100px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">病人信息</h4>
            </div>
            <div class="modal-body">
                <div class="panel panel-default">
                    <div class="panel-heading">基本情况</div>
                    <div class="panel-body">
                        <form class="form-horizontal" role="form">
                            <input type="hidden" id="ep_id"/>
                            <div class="form-group">
                                <label class="col-md-1 control-label">标识</label>
                                <div class="col-md-3">
                                    <input type="text" class="form-control input-sm" id="ep_pid"/>
                                </div>
                                <label for="ep_height" class="col-md-1 control-label">身高</label>
                                <div class="col-md-3">
                                    <div class="input-group input-group-sm">
                                        <input type="number" class="form-control input-sm" id="ep_height" min="1" onchange="ep_calculateBMI()"/>
                                        <span class="input-group-addon">厘米</span>
                                    </div>
                                </div>
                                <label class="col-md-1 control-label">性别</label>
                                <div class="col-md-3">
                                    <div class="btn-group btn-group-sm" data-toggle="buttons">
                                        <label class="btn btn-default" id="ep_sex_male_label">
                                            <input type="radio" name="sex" id="ep_sex_male" onchange="ep_changeSexColor()">&emsp;男&emsp;
                                        </label>
                                        <label class="btn btn-default" id="ep_sex_female_label">
                                            <input type="radio" name="sex" id="ep_sex_female" onchange="ep_changeSexColor()">&emsp;女&emsp;
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="ep_name" class="col-md-1 control-label">姓名</label>
                                <div class="col-md-3">
                                    <input type="text" class="form-control input-sm" id="ep_name"/>
                                </div>
                                <label for="ep_weight" class="col-md-1 control-label">体重</label>
                                <div class="col-md-3">
                                    <div class="input-group input-group-sm">
                                        <input type="number" class="form-control input-sm" id="ep_weight" min="1" onchange="ep_calculateBMI()"/>
                                        <span class="input-group-addon">&nbsp;斤&nbsp;</span>
                                    </div>
                                </div>
                                <label for="ep_age" class="col-md-1 control-label">年龄</label>
                                <div class="col-md-3">
                                    <input type="number" class="form-control input-sm" id="ep_age" min="0"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="ep_password" class="col-md-1 control-label">密码</label>
                                <div class="col-md-3">
                                    <input type="text" class="form-control input-sm" id="ep_password"/>
                                </div>
                                <label class="col-md-1 control-label">BMI</label>
                                <div class="col-md-3">
                                    <p class="form-control-static" id="ep_bmi_text"></p>
                                </div>
                                <label for="ep_address" class="col-md-1 control-label">地址</label>
                                <div class="col-md-3">
                                    <input type="text" class="form-control input-sm" id="ep_address"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="ep_phone" class="col-md-1 control-label">电话</label>
                                <div class="col-md-3">
                                    <input type="text" class="form-control input-sm" id="ep_phone"/>
                                </div>
                                <label for="ep_email" class="col-md-1 control-label">邮箱</label>
                                <div class="col-md-3">
                                    <input type="text" class="form-control input-sm" id="ep_email"/>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">手术情况</div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-8">
                                <form class="form-horizontal" role="form">
                                    <div class="form-group">
                                        <label class="col-md-2 control-label">手术日期</label>
                                        <div class="col-md-4">
                                            <div class="input-group input-group-sm date" id="ep_surgeryDate_picker">
                                                <input type="text" class="form-control input-sm" id="ep_surgeryDate" data-format="yyyy-MM-dd">
                                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                            </div>
                                        </div>
                                        <label class="col-md-2 control-label">出院日期</label>
                                        <div class="col-md-4">
                                            <div class="input-group input-group-sm date" id="ep_dischargeDate_picker">
                                                <input type="text" class="form-control input-sm" id="ep_dischargeDate" data-format="yyyy-MM-dd">
                                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-2 control-label">手术名称</label>
                                        <div class="col-md-4">
                                            <select class="form-control input-sm" id="ep_surgeryType">
                                                <option></option>
                                                <c:forEach var="it" items="${surgeryTypes}">
                                                    <option value="${it.id}">${it.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label class="col-md-2 control-label">主管医生</label>
                                        <div class="col-md-4">
                                            <select class="form-control input-sm" id="ep_doctor">
                                                <option value="${user.id}">${user.displayName}</option>
                                                <c:forEach var="it" items="${employees}">
                                                    <option value="${it.id}">${it.displayName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="col-md-4">
                                <div class="row">
                                    <label class="col-md-3 control-label">特殊事项</label>
                                    <div class="col-md-9">
                                        <textarea class="form-control input-sm" id="ep_surgeryMemo" rows="3"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">训练计划</div>
                    <div class="panel-body">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>阶段</th>
                                <th>天数</th>
                                <th>每日次数</th>
                                <th>每次步数</th>
                                <th>压力-公斤</th>
                                <th>备注</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody id="ep_plans">
                            </tbody>
                            <tfoot>
                            <tr>
                                <td><button class="btn btn-sm btn-info" onclick="addPlanStep()">添加阶段</button></td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="pull-left">
                    <button id="btn_ep_markFinished" type="button" class="btn btn-danger pull-left" onclick="markFinished()">标记为已完成</button>
                </div>
                <button id="btn_ep_cancel" type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button id="btn_ep_save" type="button" class="btn btn-primary" onclick="savePatient()">保存</button>
            </div>
        </div>
    </div>
</div>

<div id="viewTraining" class="modal fade" tabindex="-1" role="dialog" data-backdrop="static">
    <div class="modal-dialog" style="width: 1100px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">训练情况</h4>
            </div>
            <div class="modal-body">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>时间</th>
                                <th>阶段</th>
                                <th>步数</th>
                                <th>压力-公斤</th>
                                <th>总体感受</th>
                                <th>训练强度</th>
                                <th>不良反应</th>
                                <th>超限比例</th>
                                <th>留言</th>
                            </tr>
                            </thead>
                            <tbody id="vt_list" data-url="<c:url value="/patient/chart/"/>">
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<div id="changePasswordDialog" class="modal fade" tabindex="-1" role="dialog" data-backdrop="static" data-url="<c:url value="/changePassword"/>">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">修改密码</h4>
            </div>
            <div class="modal-body">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <form role="form">
                            <div class="form-group">
                                <label for="cpwd_oldPassword">原密码</label>
                                <input type="password" id="cpwd_oldPassword" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="cpwd_newPassword">新密码</label>
                                <input type="password" id="cpwd_newPassword" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="cpwd_newPassword2">确认新密码</label>
                                <input type="password" id="cpwd_newPassword2" class="form-control">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="changePassword()">保存</button>
            </div>
        </div>
    </div>
</div>

<div id="sendMessageDialog" class="modal fade" tabindex="-1" role="dialog" data-backdrop="static" data-url="<c:url value="/patient/push/"/>">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">发送消息</h4>
            </div>
            <div class="modal-body">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <form role="form">
                            <input type="hidden" id="smd_pid">
                            <div class="form-group">
                                <label for="smd_name">病人姓名</label>
                                <p class="form-control-static" id="smd_name"></p>
                            </div>
                            <div class="form-group">
                                <label for="smd_message">消息内容</label>
                                <textarea class="form-control" id="smd_message" cols="30" rows="8" maxlength="200"></textarea>
                            </div>
                            <div class="form-group pull-right">
                                <span class="form-control-static text-primary" id="smd_size"></span>
                                <span class="form-control-static"> / 200</span>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="sendMessage()">发送</button>
            </div>
        </div>
    </div>
</div>

<div id="messageHistoriesDialog" class="modal fade" tabindex="-1" role="dialog" data-backdrop="static" data-url="<c:url value="/patient/messageHistory/"/>">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">发送消息</h4>
            </div>
            <div class="modal-body">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <span class="text-primary" id="mhd_name"></span>
                        <div id="mhd_list" class="list-group"></div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<div id="urls" style="display: none"
        data-patient_bean="<c:url value="/patient/bean/"/>"
        data-patient_training="<c:url value="/patient/training/"/>"
        data-patient_save="<c:url value="/patient/save"/>"
        data-patient_mark_finished="<c:url value="/patient/markFinished/"/>"
        ></div>

<script src="<c:url value="/resources/jquery/jquery.min.js"/>"></script>
<script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/datetimepicker/bootstrap-datetimepicker.min.js"/>"></script>
<script src="<c:url value="/resources/datetimepicker/bootstrap-datetimepicker.zh-CN.js"/>"></script>
<script src="<c:url value="/resources/select2/select2.js"/>"></script>
<script src="<c:url value="/resources/select2/select2_locale_zh-CN.js"/>"></script>
<script src="<c:url value="/resources/bootbox/bootbox.min.js"/>"></script>
<script src="<c:url value="/resources/raphael/raphael-min.js"/>"></script>
<script src="<c:url value="/resources/morris/morris.min.js"/>"></script>
<script src="<c:url value="/resources/site.js"/>"></script>
<script src="<c:url value="/resources/user/home.js"/>"></script>
<script>
    $('#ep_surgeryDate_picker').datetimepicker({language:'zh-CN', pickTime:false});
    $('#ep_dischargeDate_picker').datetimepicker({language:'zh-CN', pickTime:false});
    $('#ep_surgeryType').select2();
    $('#ep_doctor').select2();
</script>
</body>
</html>