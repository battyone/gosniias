<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html ng-app="app" ng-controller="ROOT">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Исполнение функций</title>

        <%@include file="/WEB-INF/pages/jspf/head.jspf" %>

    </head>
    <body>
        <div class="container" >

            <div class="row">
                <div class="col-sm-3">
                    <%@include file="/WEB-INF/pages/jspf/menu.jspf" %>
                </div>
                <div class="col-sm-9" ng-controller="RulesCtrl" ng-init="loadProjects(1, loadRules)">

                    <div class="loader"></div>

                    <div class="data form-inline" style="margin-bottom: 20px;">
                        <div class="form-group ">
                            <label for="project">Проект:</label>
                            <select class="form-control" id="project" >
                                <option value="-1">Выберите проект</option>
                                <option ng-repeat="x in projects.projects" value="{{x.id}}">{{x.description}}</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <button type="button" class="btn btn-primary" ng-click="loadRules()">Обновить</button>
                        </div>
                    </div>

                    <div class="panel panel-default">
                        <div class="panel-body">

                            <button type="button" class="btn btn-success data" data-toggle="modal" data-target="#editForm" ng-click="modelFormHeader = 'Создание'; newRule()">Добавить</button>

                            <table class="table table-hover data" >
                                <thead>
                                    <tr>
                                        <th>Наименование правила</th>
                                        <th>Действия</th>
                                    </tr>
                                </thead>
                                <tbody>

                                    <tr ng-repeat="rule in rules.rules">
                                        <td>{{rule.description}}</td>
                                        <td>
                                            <button type="button" class="btn btn-success" data-toggle="modal" data-target="#editForm" ng-click="setCurrentRule('Редактирование', rule)">
                                                <span class="glyphicon glyphicon-pencil"></span>
                                            </button>
                                            <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteForm" ng-click="setCurrentRule('Удаление', rule)">
                                                <span class="glyphicon glyphicon-remove"></span>
                                            </button>
                                            <button type="button" class="btn btn-info" title="Выполнение" data-toggle="modal" data-target="#checkForm" ng-click="fillForExecute(rule)">
                                                <span class="glyphicon glyphicon-ok-sign"></span>
                                            </button>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <ul class="pagination data">
                                <li ng-repeat="i in getPages(rules) track by $index"><a href="#" ng-click="loadRules($index + 1)">{{$index + 1}}</a></li>
                            </ul>
                        </div>
                    </div>
                    <div id="checkForm" class="modal fade" role="dialog">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title">Исполнение правила</h4>
                                </div>
                                <div class="modal-body">
                                    <div class="form-group">
                                        <label for="groovyText">Содержательная часть:</label>
                                        <textarea rows="10" class="form-control" id="groovyText" ng-model="rule.body" name="reqText"></textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="result">Result:</label>
                                        <textarea class="form-control" rows="15" id="result" ng-model="rule.result" disabled></textarea>
                                    </div>

                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-success" ng-click="executeRule()">Выполнить</button>
                                    <button type="button" class="active btn btn-default" data-dismiss="modal">Закрыть</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div id="deleteForm" class="modal fade" role="dialog">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title">Удаление</h4>
                                </div>
                                <div class="modal-body">
                                    <p>Подтвердите удаление правила <b>{{currentRule.description}}</b></p>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-info" data-dismiss="modal" ng-click="deleteRule(currentRule)">Удалить</button>
                                    <button type="button" class="active btn btn-default" data-dismiss="modal">Отмена</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div id="editForm" class="modal fade " role="dialog">
                        <div class="modal-dialog">

                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title">{{modelFormHeader}}</h4>
                                </div>
                                <div class="modal-body">
                                    <div class="form-group">
                                        <label for="scrName">Наименование правила:</label>
                                        <input type="text" class="form-control" ng-model="currentRule.description" id="scrName" required/>
                                    </div>
                                    <div class="form-group">
                                        <label for="scrName">Тип правила</label>
                                        <select class="form-control" ng-model="currentRule.typeOf" ng-options="typeOf for typeOf in typeOfs"></select>
                                    </div>

                                    <div class="form-group">
                                        <label for="scrFile">Файл правила:</label>
                                        <input type="file" class="form-control" file-model="scrFile" id="scrFile"/>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="active btn btn-success" data-dismiss="modal" ng-click="saveRule(currentRule)">Cохранить</button>
                                    <button type="button" class="btn btn-warningdefault" data-dismiss="modal">Закрыть</button>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>

        </div>
    </body>
    <script>
        $(document).ready(function () {
        activeMenu('rules');
        <%--/*if (!$.isNumeric(parseInt($('#project option')[0].value))) {
                $('#project option')[0].remove();
            }*/ --%>
        });
    </script>
</html>

