<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html ng-app="app" ng-controller="ROOT">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Groovy скрипты</title>

        <%@include file="/WEB-INF/pages/jspf/head.jspf" %>

    </head>
    <body>
        <div class="container" >

            <div class="row">
                <div class="col-sm-3">
                    <%@include file="/WEB-INF/pages/jspf/menu.jspf" %>
                </div>
                <div class="col-sm-9" ng-controller="RootCtrl">
                    <button type="button" class="btn btn-success data" data-toggle="modal" data-target="#editForm" ng-click="modelFormHeader = 'Создание';newScript()">Добавить</button>

                    <div class="loader"></div>
                    <table class="table table-hover data" >
                        <thead>
                            <tr>
                                <th>Наименование функции</th>
                                <th>Тип функции</th>
                                <th>Действия</th>
                            </tr>
                        </thead>
                        <tbody>

                            <tr ng-repeat="script in scripts.functions">
                                <td>{{script.description}}</td>
                                <td>{{script.typeOf}}</td>
                                <td>
                                    <button type="button" class="btn btn-success" data-toggle="modal" data-target="#editForm" ng-click="setCurrentScript('Редактирование', script)">
                                        <span class="glyphicon glyphicon-pencil"></span>
                                    </button>
                                    <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteForm" ng-click="setCurrentScript('Удаление', script)">
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </button>
                                    <button type="button" class="btn btn-info" title="Выполнение" data-toggle="modal" data-target="#checkForm" ng-click="fillForExecute(script)">
                                        <span class="glyphicon glyphicon-ok-sign"></span>
                                    </button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <ul class="pagination data">
                        <li ng-repeat="i in getPages(scripts) track by $index"><a href="#" ng-click="loadScripts($index + 1)">{{$index + 1}}</a></li>
                    </ul>

                    <div id="checkForm" class="modal fade" role="dialog">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title">Выполнение</h4>
                                </div>
                                <div class="modal-body">
                                    <div class="form-group">
                                        <label for="groovyText">Groovy:</label>
                                        <textarea rows="10" class="form-control" id="groovyText" ng-model="groovy.body" name="groovyText"></textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="groovyParams">Параметры:</label>
                                        <textarea rows="5" class="form-control" id="groovyParams" ng-model="groovy.params" name="groovyParams"></textarea>
                                    </div>
                                    <hr/>
                                    <div class="form-group">
                                        <label for="result">Result:</label>
                                        <textarea class="form-control" rows="5" id="result" ng-model="groovy.result" disabled></textarea>
                                    </div>

                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-success" ng-click="executeScript()">Выполнить</button>
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
                                    <p>Подтвердите удаление скрипта <b>{{currentScript.description}}</b></p>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-info" data-dismiss="modal" ng-click="deleteScript(currentScript)">Удалить</button>
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
                                        <label for="scrName">Наименование функции:</label>
                                        <input type="text" class="form-control" ng-model="currentScript.description" id="scrName" required/>
                                    </div>

                                    <div class="form-group">
                                        <label for="scrName">Тип функции:</label>
                                        <select class="form-control" ng-model="currentScript.typeOf" ng-options="typeOf for typeOf in typeOfs"></select>
                                    </div>

                                    <div class="form-group">
                                        <label for="requestNames">Именованные параметры через запятую:</label>
                                        <input type="text" class="form-control" ng-model="currentScript.requestNames" id="requestNames"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="scrFile">Файл:</label>
                                        <input type="file" class="form-control" file-model="scrFile" id="scrFile"/>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="active btn btn-success" data-dismiss="modal" ng-click="saveScript(currentScript)">Cохранить</button>
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
                    activeMenu('gr-list-menu');
                });
    </script>
</html>
