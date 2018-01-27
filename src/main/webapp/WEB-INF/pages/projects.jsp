<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html ng-app="app" ng-controller="ROOT">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Проекты</title>

        <%@include file="/WEB-INF/pages/jspf/head.jspf" %>

    </head>
    <body>
        <div class="container" >

            <div class="row">
                <div class="col-sm-3">
                    <%@include file="/WEB-INF/pages/jspf/menu.jspf" %>
                </div>
                <div class="col-sm-9" ng-controller="ProjectsCtrl">
                    <button type="button" class="btn btn-success data" data-toggle="modal" data-target="#editForm" ng-click="modelFormHeader = 'Создание';newProject()">Добавить</button>

                    <div class="loader"></div>
                    <table class="table table-hover data" >
                        <thead>
                            <tr>
                                <th>Наименование проекта</th>
                                <th>Действия</th>
                            </tr>
                        </thead>
                        <tbody>

                            <tr ng-repeat="project in projects.projects">
                                <td>{{project.description}}</td>
                                <td>
                                    <button type="button" class="btn btn-success" data-toggle="modal" data-target="#editForm" ng-click="setCurrentProject('Редактирование', project)">
                                        <span class="glyphicon glyphicon-pencil"></span>
                                    </button>
                                    <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteForm" ng-click="setCurrentProject('Удаление', project)">
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <ul class="pagination data">
                        <li ng-repeat="i in getPages(projects) track by $index"><a href="#" ng-click="loadProjects($index + 1)">{{$index + 1}}</a></li>
                    </ul>

                    <div id="deleteForm" class="modal fade" role="dialog">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title">Удаление</h4>
                                </div>
                                <div class="modal-body">
                                    <p>Подтвердите удаление проекта <b>{{currentProject.description}}</b></p>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-info" data-dismiss="modal" ng-click="deleteProject(currentProject)">Удалить</button>
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
                                        <label for="scrName">Наименование проекта:</label>
                                        <input type="text" class="form-control" ng-model="currentProject.description" id="scrName" required/>
                                    </div>

                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="active btn btn-success" data-dismiss="modal" ng-click="saveProject(currentProject)">Cохранить</button>
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
                    activeMenu('projects');
                });
    </script>
</html>
