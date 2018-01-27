<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html ng-app="app" ng-controller="ROOT" >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Понятия, термины</title>

        <%@include file="/WEB-INF/pages/jspf/head.jspf" %>

    </head>
    <body>
        <div class="container" >

            <div class="row">
                <div class="col-sm-3">
                    <%@include file="/WEB-INF/pages/jspf/menu.jspf" %>
                </div>
                <div class="col-sm-9" ng-controller="TaxonCtrl" ng-init="loadProjects(1, loadTaxons)">
                    <div class="loader"></div>
                    <div class="data form-inline" style="margin-bottom: 20px;">
                        <div class="form-group ">
                            <label for="project">Проект:</label>
                            <select class="form-control" id="project">
                                <option value="-1">Выберите проект</option>
                                <option ng-repeat="x in projects.projects" value="{{x.id}}">{{x.description}}</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <button type="button" class="btn btn-primary" ng-click=" loadTaxons()">Обновить</button>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <table class="table table-hover data" >
                                <thead>
                                    <tr>
                                        <th>Наименование термина/понятия</th>
                                        <th>Действия</th>
                                    </tr>
                                </thead>
                                <tbody>

                                    <tr ng-repeat="taxon in taxons.taxons">
                                        <td>{{taxon.description}}</td>
                                        <td>
                                            <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteForm" ng-click="setCurrentTaxon('Удаление', taxon)">
                                                <span class="glyphicon glyphicon-remove"></span>
                                            </button>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <ul class="pagination data">
                                <li ng-repeat="i in getPages(taxons) track by $index"><a href="#" ng-click="loadTaxons($index + 1)">{{$index + 1}}</a></li>
                            </ul>
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
                                    <p>Удаление термина <b>{{currentTaxon.description}}</b> повлечет за собой удаление всех отношений связности термина.</p>
                                    <p>Подтвердите удаление  <b>{{currentTaxon.description}}</b></p>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-info" data-dismiss="modal" ng-click="deleteTaxon(currentTaxon)">Удалить</button>
                                    <button type="button" class="active btn btn-default" data-dismiss="modal">Отмена</button>
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
                    activeMenu('taxons');
                });
    </script>
</html>
