<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="cmn">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/site.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/morris/morris.css"/>">
</head>
<body>
<div class="navbar navbar-fixed-top navbar-inverse">
    <div class="navbar-header">
        <span class="navbar-brand">第六人民医院 骨科</span>
    </div>
</div>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-body">
            <div id="chart"></div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">病人留言</div>
        <div class="panel-body">
            <p>${bean.memo}</p>
        </div>
    </div>
</div>

<script src="<c:url value="/resources/jquery/jquery.min.js"/>"></script>
<script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/raphael/raphael-min.js"/>"></script>
<script src="<c:url value="/resources/morris/morris.min.js"/>"></script>
<script>
    var i = 0, values = [];
    <c:forEach var="it" items="${bean.points}">
    values.push({x: ++i, y:${it}});
    </c:forEach>
    var planPressure = '${bean.plan.pressure}';
    var maxPressure = planPressure * 1.5;
    Morris.Line({
        element: 'chart',
        smooth: false,
        parseTime: false,
        data: values,
        xkey: 'x',
        ykeys: ['y'],
        labels: ['压力'],
        goals: [planPressure, maxPressure],
        goalLineColors: ['#0D0', '#D00']
    });
</script>
</body>
</html>