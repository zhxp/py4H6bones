<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="cmn">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/datetimepicker/bootstrap-datetimepicker.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/select2/select2.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/select2/select2-bootstrap.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/site.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/user/home.css"/>" rel="stylesheet">
</head>
<body>

<div class="navbar navbar-default navbar-fixed-top">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <span class="navbar-brand">第六人民医院 骨科</span>
    </div>
    <div class="navbar-collapse collapse">
        <p class="navbar-text navbar-right">
            <span>主治医师</span>
            <span><a href="<c:url value="/logout"/>" class="btn btn-info">退出</a></span>
        </p>
    </div>
</div>
<div class="container">
    <p>下属医生</p>
    <c:forEach var="employee" items="${employees}">
        <span>${employee.displayName}</span>
    </c:forEach>
</div>
<div class="container">
    <p>病人情况</p>
    <button type="button" class="btn btn-primary" id="show_createPatient">添加病人</button>
    <ul class="nav nav-tabs nav-tabs-justified">
        <li><a href="#new" data-toggle="tab" id="patient_new_tab">已加入</a></li>
        <li><a href="#registered" data-toggle="tab" id="patient_registered_tab">已注册</a></li>
        <li><a href="#planned" data-toggle="tab" id="patient_planned_tab">已计划</a></li>
        <li class="active"><a href="#trained" data-toggle="tab" id="patient_trained_tab">已训练</a></li>
        <li><a href="#finished" data-toggle="tab" id="patient_finished_tab">已完成</a></li>
    </ul>
    <div class="tab-content">
        <div class="tab-pane fade" id="new">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>病人标识</th>
                            <th>电话号码</th>
                            <th>添加时间</th>
                        </tr>
                    </thead>
                    <tbody id="patientList_new">
                    </tbody>
                </table>
            </div>
        </div>
        <div class="tab-pane fade" id="registered">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>病人标识</th>
                        <th>姓名</th>
                        <th>电话</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody id="patientList_registered">
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
                        <th>电话</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody id="patientList_planned">
                    </tbody>
                </table>
            </div>
        </div>
        <div class="tab-pane fade active in" id="trained">
            trained
        </div>
        <div class="tab-pane fade" id="finished">
            finished
        </div>
    </div>
</div>

<div id="createPatient" class="modal fade" tabindex="-1" role="dialog" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">添加病人</h4>
            </div>
            <div class="modal-body">
                <form role="form" action="patient/create" id="form_createPatient">
                    <div class="form-group">
                        <div class="has-error">
                            <span class="help-block" id="error_createPatient__serverError"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="createPatient_pid">病人标识</label>
                        <input type="text" class="form-control" id="createPatient_pid" name="pid">
                        <div class="has-error">
                            <span class="help-block" id="error_createPatient_pid"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="createPatient_phone">电话号码</label>
                        <input type="text" class="form-control" id="createPatient_phone" name="phone">
                        <span class="has-error">
                            <span class="help-block" id="error_createPatient_phone"></span>
                        </span>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="do_createPatient">保存</button>
            </div>
        </div>
    </div>
</div>

<div id="editPatient" class="modal fade" tabindex="-1" role="dialog" data-backdrop="static"
     data-details-path="<c:url value="/patient/details"/>" data-edit-path="<c:url value="/patient/edit"/>">
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
                            <div class="form-group">
                                <label class="col-md-1 control-label">标识</label>
                                <div class="col-md-3">
                                    <p class="form-control-static" id="ep_pid_text">pid</p>
                                </div>
                                <label for="ep_height" class="col-md-1 control-label">身高</label>
                                <div class="col-md-3">
                                    <div class="input-group input-group-sm">
                                        <input type="number" class="form-control input-sm" id="ep_height" name="height" min="1" onchange="ep_calculateBMI()"/>
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
                                    <input type="text" class="form-control input-sm" id="ep_name" name="name"/>
                                </div>
                                <label for="ep_weight" class="col-md-1 control-label">体重</label>
                                <div class="col-md-3">
                                    <div class="input-group input-group-sm">
                                        <input type="number" class="form-control input-sm" id="ep_weight" name="weight" min="1" onchange="ep_calculateBMI()"/>
                                        <span class="input-group-addon">&nbsp;斤&nbsp;</span>
                                    </div>
                                </div>
                                <label for="ep_age" class="col-md-1 control-label">年龄</label>
                                <div class="col-md-3">
                                    <input type="number" class="form-control input-sm" id="ep_age" name="age" min="0"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="ep_password" class="col-md-1 control-label">密码</label>
                                <div class="col-md-3">
                                    <input type="text" class="form-control input-sm" id="ep_password" name="password"/>
                                </div>
                                <label class="col-md-1 control-label">BMI</label>
                                <div class="col-md-3">
                                    <p class="form-control-static" id="ep_bmi_text"></p>
                                </div>
                                <label for="ep_address" class="col-md-1 control-label">地址</label>
                                <div class="col-md-3">
                                    <input type="text" class="form-control input-sm" id="ep_address" name="address"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="ep_phone" class="col-md-1 control-label">电话</label>
                                <div class="col-md-3">
                                    <input type="text" class="form-control input-sm" id="ep_phone" name="phone"/>
                                </div>
                                <label for="ep_email" class="col-md-1 control-label">邮箱</label>
                                <div class="col-md-3">
                                    <input type="text" class="form-control input-sm" id="ep_email" name="email"/>
                                </div>
                                <label for="ep_patientType" class="col-md-1 control-label">状态</label>
                                <div class="col-md-3">
                                    <div class="input-group input-group-sm select2-bootstrap-prepend">
                                        <span class="input-group-addon"><span class="glyphicon glyphicon-th-list"></span></span>
                                        <select class="form-control input-sm" id="ep_patientType" name="patientType">
                                            <option value="NEW">已加入</option>
                                            <option value="REGISTERED">已登录</option>
                                            <option value="PLANNED">已计划</option>
                                            <option value="TRAINED">已训练</option>
                                            <option value="FINISHED">已完成</option>
                                        </select>
                                    </div>
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
                                            <select class="form-control input-sm" id="ep_surgeryBy">
                                                <option></option>
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
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="do_editPatient">保存</button>
            </div>
        </div>
    </div>
</div>

<div id="createPlan" class="modal fade" tabindex="-1" role="dialog" data-backdrop="static"
     data-update-path="<c:url value="/patient/createPlan"/>">
    <div class="modal-dialog" style="width: 1100px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">创建计划</h4>
            </div>
            <div class="modal-body">
                <div class="panel panel-default">
                    <div class="panel-heading">病人情况</div>
                    <div class="panel-body">
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <label class="col-md-1 control-label">标识</label>
                                <p class="col-md-2 form-control-static" id="cpl_pid"></p>
                                <label class="col-md-1 control-label">姓名</label>
                                <p class="col-md-2 form-control-static" id="cpl_name"></p>
                                <label class="col-md-1 control-label">性别</label>
                                <p class="col-md-2 form-control-static" id="cpl_sex"></p>
                                <label class="col-md-1 control-label">年龄</label>
                                <p class="col-md-2 form-control-static" id="cpl_age"></p>
                            </div>
                            <div class="form-group">
                                <label class="col-md-1 control-label">身高</label>
                                <p class="col-md-2 form-control-static" id="cpl_height"></p>
                                <label class="col-md-1 control-label">体重</label>
                                <p class="col-md-2 form-control-static" id="cpl_weight"></p>
                                <label class="col-md-1 control-label">BMI</label>
                                <p class="col-md-2 form-control-static" id="cpl_bmi"></p>
                            </div>
                            <div class="form-group">
                                <label class="col-md-1 control-label">手术日期</label>
                                <p class="col-md-2 form-control-static" id="cpl_surgeryDate"></p>
                                <label class="col-md-1 control-label">出院日期</label>
                                <p class="col-md-2 form-control-static" id="cpl_dischargeDate"></p>
                                <label class="col-md-1 control-label">手术名称</label>
                                <p class="col-md-4 form-control-static" id="cpl_surgeryName"></p>
                            </div>
                            <div class="form-group">
                                <label class="col-md-1 control-label">主管医生</label>
                                <p class="col-md-2 form-control-static" id="cpl_surgeonName"></p>
                                <label class="col-md-1 control-label">特殊事项</label>
                                <p class="col-md-8 form-control-static" id="cpl_surgeryMemo"></p>
                            </div>
                        </form>
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
                                <th>目标压力</th>
                            </tr>
                            </thead>
                            <tbody id="cpl_plan">
                                <%--<tr>--%>
                                    <%--<td><p class="form-control-static">1</p></td>--%>
                                    <%--<td><input type="number" class="form-control input-sm" value="7" name="days" style="width:80px;"></td>--%>
                                    <%--<td><input type="text" class="form-control input-sm" value="7" name="times"></td>--%>
                                    <%--<td><input type="text" class="form-control input-sm" value="7" name="steps"></td>--%>
                                    <%--<td><input type="text" class="form-control input-sm" value="7" name="pressure"></td>--%>
                                    <%--<td><button class="btn btn-danger btn-sm" onclick="removePlanStep(this)">删除</button></td>--%>
                                <%--</tr>--%>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <td><button class="btn btn-sm btn-info" onclick="addPlanStep()">添加阶段</button></td>
                                </tr>
                                <tr>
                                    <td>
                                        <ul id="createPlan_errors"></ul>
                                    </td>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="do_createPlan">保存</button>
            </div>
        </div>
    </div>
</div>

<script src="<c:url value="/resources/jquery/jquery.min.js"/>"></script>
<script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/datetimepicker/bootstrap-datetimepicker.min.js"/>"></script>
<script src="<c:url value="/resources/datetimepicker/bootstrap-datetimepicker.zh-CN.js"/>"></script>
<script src="<c:url value="/resources/select2/select2.js"/>"></script>
<script src="<c:url value="/resources/select2/select2_locale_zh-CN.js"/>"></script>
<script src="<c:url value="/resources/site.js"/>"></script>
<script src="<c:url value="/resources/user/home.js"/>"></script>
<script>
    $('#ep_surgeryDate_picker').datetimepicker({language:'zh-CN', pickTime:false});
    $('#ep_dischargeDate_picker').datetimepicker({language:'zh-CN', pickTime:false});
    $('#ep_patientType').select2({minimumResultsForSearch:-1});
    $('#ep_surgeryType').select2({minimumResultsForSearch:-1});
    $('#ep_surgeryBy').select2({minimumResultsForSearch:-1});
</script>
</body>
</html>