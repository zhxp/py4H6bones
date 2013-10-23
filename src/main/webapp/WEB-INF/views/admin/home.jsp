<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="cmn">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/select2/select2.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/select2/select2-bootstrap.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/site.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/admin/home.css"/>">
</head>
<body>
<div class="navbar navbar-fixed-top navbar-inverse">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse"
                data-target=".navbar-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <span class="navbar-brand">第六人民医院 骨科</span>
    </div>
    <div class="navbar-collapse collapse">
        <span class="navbar-text">
            <span>
                ${user.displayName}
                <c:if test="${user.id != doctor.id}">
                    <c:out value=" > ${doctor.displayName}"/>
                </c:if>
            </span>
        </span>
        <span class="navbar-text navbar-right">
            <span><button class="btn btn-link" onclick="showChangePasswordDialog()">修改密码
            </button></span>
            <span><a href="<c:url value="/logout"/>" class="btn btn-link">退出</a></span>
        </span>
    </div>
</div>

<div class="container">
    <div class="panel panel-default">
        <div class="panel-body">
            <ul class="nav nav-tabs nav-tabs-justified">
                <li class="active"><a href="#doctorTab" data-toggle="tab" id="doctorTabId">医生管理</a></li>
                <li><a href="#surgeryTab" data-toggle="tab" id="surgeryTabId">医生管理</a></li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane fade active in" id="doctorTab">
                    <div>
                        <button class="btn btn-link" onclick="createDoctor()">+ 添加医生</button>
                        <a href="admin/doctorTree" target="_blank" class="btn btn-link">下属列表</a>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>登录名</th>
                                <th>姓名</th>
                                <th>主管</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody id="doctorList" data-url="<c:url value="/admin/doctors"/>">
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="tab-pane fade active in" id="surgeryTab">
                    <div>
                        <button class="btn btn-link" onclick="createDoctor()">+ 添加手术</button>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>手术名称</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody id="surgeryList" data-url="<c:url value="/admin/surgeries"/>">
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="changePasswordDialog" class="modal fade" tabindex="-1" role="dialog" data-backdrop="static"
     data-url="<c:url value="/changePassword"/>">
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

<div id="editDoctorDialog" class="modal fade" role="dialog" data-backdrop="static"
     data-url="<c:url value="/admin/editDoctor"/>"
     data-supervisors="<c:url value="/admin/supervisors"/>"
     data-details="<c:url value="/admin/doctor/"/> ">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">医生信息</h4>
            </div>
            <div class="modal-body">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <form role="form">
                            <input type="hidden" id="ed_id"/>

                            <div class="form-group">
                                <label class="control-label" for="ed_username">登录名</label>
                                <input type="text" id="ed_username" class="form-control">
                            </div>
                            <div class="form-group">
                                <label class="control-label" for="ed_displayName">姓名</label>
                                <input type="text" id="ed_displayName" class="form-control">
                            </div>
                            <div class="form-group">
                                <label class="control-label" for="ed_password">密码</label>
                                <input type="text" id="ed_password" class="form-control">
                            </div>
                            <div class="form-group">
                                <label class="control-label" for="ed_supervisor">主管</label>
                                <select class="form-control" id="ed_supervisor">
                                    <option value=""></option>
                                </select>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="saveDoctor()">保存</button>
            </div>
        </div>
    </div>
</div>

<div id="editSurgeryDialog" class="modal fade" tabindex="-1" role="dialog" data-backdrop="static"
     data-url="<c:url value="/admin/editSurgery"/>">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">手术类型</h4>
            </div>
            <div class="modal-body">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <form role="form">
                            <input type="hidden" id="es_id">
                            <div class="form-group">
                                <label for="es_name">手术名称</label>
                                <input type="text" id="es_name" class="form-control">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="saveSurgery()">保存</button>
            </div>
        </div>
    </div>
</div>

<script src="<c:url value="/resources/jquery/jquery.min.js"/>"></script>
<script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/bootbox/bootbox.min.js"/>"></script>
<script src="<c:url value="/resources/select2/select2.js"/>"></script>
<script src="<c:url value="/resources/select2/select2_locale_zh-CN.js"/>"></script>
<script src="<c:url value="/resources/admin/home.js"/>"></script>
</body>
<script>
    $('#ed_supervisor').select2({allowClear:true});
</script>
</html>