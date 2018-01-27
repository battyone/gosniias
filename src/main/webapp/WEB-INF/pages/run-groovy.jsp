<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html ng-app="app" ng-controller="ROOT">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Тестирование online</title>

        <%@include file="/WEB-INF/pages/jspf/head.jspf" %>

    </head>
    <body>
        <div class="container" >

            <div class="row">
                <div class="col-sm-3">
                    <%@include file="/WEB-INF/pages/jspf/menu.jspf" %>
                </div>
                <div class="col-sm-9" ng-controller="RunGroovyCtrl">
                    <div class="form-group">
                        <label for="groovyText">Groovy:</label>
                        <textarea rows="10" class="form-control" id="groovyText" ng-model="groovy.body" name="groovyText"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="groovyParams">Параметры:</label>
                        <textarea rows="5" class="form-control" id="groovyParams" ng-model="groovy.params" name="groovyParams"></textarea>
                    </div>
                    <button type="button" class="btn btn-success btn-block" ng-click="executeScript()">Выполнить</button>
                    <hr/>
                    <div class="form-group">
                        <label for="result">Result:</label>
                        <textarea class="form-control" rows="10" id="result" ng-model="result"></textarea>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script>
                $(document).ready(function () {
                    activeMenu('test-online');
                });
    </script>
</html>
