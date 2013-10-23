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
    <style>
        ul.tree, ul.tree ul {
            list-style-type: none;
            background: url(<c:url value="/resources/admin/vline.png"/>) repeat-y;
            margin: 0;
            padding: 0;
        }
        ul.tree ul {
            margin-left: 10px;
        }
        ul.tree li {
            margin: 0;
            padding: 0 12px;
            line-height: 20px;
            background: url(<c:url value="/resources/admin/node.png"/>) no-repeat;
            color: #369;
            font-weight: bold;
        }
        ul.tree li:last-child {
            background: #fff url(<c:url value="/resources/admin/lastnode.png"/>) no-repeat;
        }
    </style>
</head>
<body>
<div class="navbar navbar-fixed-top navbar-inverse">
    <div class="navbar-header">
        <span class="navbar-brand">第六人民医院 骨科</span>
    </div>
</div>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading">下属列表</div>
        <div class="panel-body">
            <ul id="pane" class="tree"></ul>
        </div>
    </div>
</div>

<script src="<c:url value="/resources/jquery/jquery.min.js"/>"></script>
<script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>"></script>
<script>
    $.getJSON('<c:url value="/admin/tree"/>')
            .done(function(data) {
                var pane = $('#pane');
                data.forEach(function(it) { createNode(it, pane)});
            });
    function createNode(doc, parent) {
//        console.log('createNode: ' + doc.n + ' - ' + doc.d + parent);
        var node = $('<li>').text(doc.n + ' - ' + doc.d);
        if (doc.e && doc.e.length) {
            var child = $('<ul>').appendTo(node);
            doc.e.forEach(function(it) { createNode(it, child)});
        }
        node.appendTo(parent);
    }
</script>
</body>
</html>