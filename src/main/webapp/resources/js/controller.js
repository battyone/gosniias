var pageSize = 20;

var app = angular.module('app', []);

app.directive('fileModel', ['$parse', function ($parse) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var model = $parse(attrs.fileModel);
                var modelSetter = model.assign;

                element.bind('change', function () {
                    scope.$apply(function () {
                        modelSetter(scope, element[0].files[0]);
                    });
                });
            }
        };
    }]);

app.controller('ROOT', function ($scope, $http) {

    $scope.modelFormHeader = '';
    $scope.currentPage = 1;

    $scope.projects = {};

    $scope.currentProjectId=-1;

    $scope.getPages = function (entities) {
        if (entities && entities.size) {
            var pages = Math.floor(entities.size / pageSize);
            pages = pages + (entities.size % pageSize == 0 ? 0 : 1);
            pages = pages <= 1 ? 0 : pages;
            return new Array(pages);
        } else {
            return new Array(0);
        }
    };

    $scope.loadProjects = function (page, funct) {
        if (page) {
            $scope.currentPage = page;
        } else {
            page = 1;
        }
        $http({
            method: "GET",
            url: "/api/project/list/" + page + "?pageSize=" + pageSize
        }).then(function (response) {
//            if ($scope.currentProjectId==-1 && response.data.size != 0) {
//                $scope.currentProjectId = response.data.projects[0].id;
//            }
            $scope.projects = response.data;
            $('.loader').hide();
            $('.data').show();
            if (funct) {
                funct();
            }
        }), function (error) {
            var a = 1;
        };
    };
});
app.controller('RelationsCtrl', function ($scope, $http) {
    $scope.currentRelation = {
        id: '',
        description: ''
    };
    $scope.relations = {};

    $scope.setCurrentRelation = function (title, relation) {
        $scope.currentRelation = angular.copy(relation);
        $scope.modelFormHeader = title;
    };

    $scope.deleteRelation = function (relation) {
        $http({
            method: "DELETE",
            url: "/api/relation/" + relation.id
        }).then(function (response) {
            $scope.currentRelation = {
                id: '',
                description: ''
            };
            $scope.loadRelations($scope.currentPage);
        }), function (error) {
            var a = 1;
        };
    };

    $scope.loadRelations = function (page) {
        $scope.currentProjectId= $('#project').val();
        if (page) {
            $scope.currentPage = page;
        } else {
            page = 1;
        }
        $http({
            method: "GET",
            url: "/api/relation/list/" + page + "?pageSize=" + pageSize + "&projectId=" + $scope.currentProjectId
        }).then(function (response) {
            $scope.relations = response.data;
//            $scope.currentProjectId = projectId;
            $('.loader').hide();
            $('.data').show();
        }), function (error) {
        };
    };
    //$scope.loadProjects(1, $scope.loadTaxons);
});


app.controller('TaxonCtrl', function ($scope, $http) {
    $scope.currentTaxon = {
        id: '',
        description: ''
    };
    $scope.taxons = {};

    $scope.setCurrentTaxon = function (title, taxon) {
        $scope.currentTaxon = angular.copy(taxon);
        $scope.modelFormHeader = title;
    };

    $scope.deleteTaxon = function (taxon) {
        $http({
            method: "DELETE",
            url: "/api/taxon/" + taxon.id
        }).then(function (response) {
            $scope.currentTaxon = {
                id: '',
                description: ''
            };
            $scope.loadTaxons($scope.currentPage);
        }), function (error) {
            var a = 1;
        };
    };

    $scope.loadTaxons = function (page) {
        $scope.currentProjectId= $('#project').val();
        if (page) {
            $scope.currentPage = page;
        } else {
            page = 1;
        }
        $http({
            method: "GET",
            url: "/api/taxon/list/" + page + "?pageSize=" + pageSize + "&projectId=" + $scope.currentProjectId
        }).then(function (response) {
            $scope.taxons = response.data;
            //$scope.currentProjectId = projectId;
            $('.loader').hide();
            $('.data').show();
        }), function (error) {
        };
    };
    //$scope.loadProjects(1, $scope.loadTaxons);
});

app.controller('RunGroovyCtrl', function ($scope, $http) {
    $scope.result = "Результат выполнения groovy script";
    $scope.groovy = {
        body: 'return a*b',
        params: '{"a":6,"b":9}'
    };

    $scope.executeScript = function () {
        $scope.result = "";

        var req = {
            method: 'POST',
            url: '/api/groovy/execute',
            data: $scope.groovy
        };

        $http(req).then(function (response) {
            $scope.result = JSON.stringify(response.data, null, "   ");
        }, function (response) {
            $scope.result = 'ошибка выполнения';
        });
    };
});

app.controller('ProjectsCtrl', function ($scope, $location, $http) {

    $scope.newProject = function () {
        $scope.currentProject = {
            description: 'Наименование проекта'
        };
    };

    $scope.setCurrentProject = function (title, project) {
        $scope.currentProject = angular.copy(project);
        $scope.modelFormHeader = title;
    };

    $scope.deleteProject = function (project) {
        $http({
            method: "DELETE",
            url: "/api/project/" + project.id
        }).then(function (response) {
            $scope.newProject();
            $scope.loadProjects($scope.currentPage);
        }), function (error) {
            var a = 1;
        };
    };

    $scope.saveProject = function (rule) {
        $http({
            url: "/api/project/create",
            method: 'POST',
            data: rule
        }).then(
                function (data) {
                    $scope.newProject();
                    $scope.loadProjects($scope.currentPage);
                },
                function (data) {
                });
    };

    $scope.loadProjects(1);
});

app.controller('RulesCtrl', function ($scope, $location, $http) {

    $scope.rules = {};

    $scope.typeOf = parse('typeOf');
    $scope.typeOfs = ['SCRIPT', 'SET_RULE', 'CHECK_RULE'];

    $scope.currentRule = {
        description: ''
    };

    $scope.rule = {
        body: '',
        result: ''
    };

    $scope.newRule = function () {
        $scope.currentRule = {
            description: 'Наименование правила',
            scrFile: '',
            typeOf: 'SET_RULE'
        };
        $('#scrFile').val('');
    };

    $scope.setCurrentRule = function (title, rule) {
        $scope.currentRule = angular.copy(rule);
        $scope.modelFormHeader = title;
    };

    $scope.deleteRule = function (rule) {
        $http({
            method: "DELETE",
            url: "/api/rule/" + rule.id
        }).then(function (response) {
            $scope.newRule();
            $scope.loadRules($scope.currentPage);
        }), function (error) {
            var a = 1;
        };
    };

    $scope.saveRule = function (rule) {
        var fd = new FormData();
        fd.append('file', $scope.scrFile);
        fd.append('description', rule.description);
        if (rule.id) {
            fd.append('id', rule.id);
        }
        fd.append('projectId', $scope.currentProjectId);
        fd.append('typeOf', rule.typeOf);

        $http.post("/api/rule/create", fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).success(function (data) {
            $scope.newRule();
            $scope.loadRules($scope.currentPage);
            $('#scrFile').val('');
        }).error(function (data) {
        });
    };

    $scope.fillForExecute = function (rule) {

        $scope.rule = {
            body: '',
            id: '',
            result: ''
        };

        $http({
            method: "GET",
            url: '/api/rule/body/' + rule.id
        }).then(function (response) {
            $scope.rule = response.data;
            $scope.rule.id = rule.id;
        }), function (error) {
        };
    };

    $scope.executeRule = function () {
        var req = {
            method: 'POST',
            url: '/api/rule/execute',
            data: $scope.rule
        };

        $http(req).then(function (response) {
            $scope.rule.result = JSON.stringify(response.data, null, "   ");
        }, function (response) {
            $scope.rule.result = 'ошибка выполнения';
        });
    };

    $scope.loadRules = function (page) {
        $scope.currentProjectId= $('#project').val();
        if (page) {
            $scope.currentPage = page;
        } else {
            page = 1;
        }
        $http({
            method: "GET",
            url: "/api/rule/list/" + page + "?pageSize=" + pageSize + '&typeOf=' + $scope.typeOf + '&projectId=' + $scope.currentProjectId
        }).then(function (response) {
            $scope.rules = response.data;
            $('.loader').hide();
            $('.data').show();
        }), function (error) {
            var a = 1;
        };
    };

    //$scope.loadRules(1);

    activeMenu($scope.typeOf);

});

app.controller('RootCtrl', function ($scope, $http) {

    $scope.groovy = {
        body: 'return a*b',
        params: '{"a":6,"b":9}',
        result: ''
    };

    $scope.typeOfs = ['VOID', 'FUNCTION', 'ACTION'];

    $scope.currentScript = {
        description: ''
    };
    $scope.scripts = {};

    $scope.currentPage = 1;

    $scope.newScript = function () {
        $scope.currentScript = {
            description: 'Наименование функции',
            scrFile: '',
            requestNames: '',
            typeOf: 'VOID'
        };
        $('#scrFile').val('');
    };

    $scope.deleteScript = function (script) {
        $http({
            method: "DELETE",
            url: "/api/function/" + script.id
        }).then(function (response) {
            $scope.newScript();
            $scope.loadScripts($scope.currentPage);
        }), function (error) {
            var a = 1;
        };
    };

    $scope.saveScript = function (script) {
        var fd = new FormData();
        fd.append('file', $scope.scrFile);
        fd.append('description', script.description);
        if (script.id) {
            fd.append('id', script.id);
        }
        fd.append('requestNames', script.requestNames);
        fd.append('typeOf', script.typeOf);

        $http.post("/api/function/create", fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).success(function (data) {
            $scope.newScript();
            $scope.loadScripts();
            $('#scrFile').val('');
        }).error(function (data) {
            var a = 1;
        });
    };

    $scope.executeScript = function () {
        var req = {
            method: 'POST',
            url: '/api/groovy/execute',
            data: $scope.groovy
        };

        $http(req).then(function (response) {
            $scope.groovy.result = JSON.stringify(response.data, null, "   ");
        }, function (response) {
            $scope.groovy.result = 'ошибка выполнения';
        });
    };

    $scope.fillForExecute = function (script) {

        $scope.groovy = {
            body: 'return a*b',
            params: '{"a":6,"b":9}',
            result: ''
        };

        $http({
            method: "GET",
            url: '/api/function/bodyById/' + script.id
        }).then(function (response) {
            $scope.groovy = response.data;
        }), function (error) {
            var a = 1;
        };
    };

    $scope.setCurrentScript = function (title, script) {
        $scope.currentScript = angular.copy(script);
        $scope.modelFormHeader = title;
    };

    $scope.loadScripts = function (page) {
        if (page) {
            $scope.currentPage = page;
        } else {
            page = 1;
        }
        $http({
            method: "GET",
            url: "/api/function/list/" + page + "?pageSize=" + pageSize
        }).then(function (response) {
            $scope.scripts = response.data;
            $('.loader').hide();
            $('.data').show();
            $scope.getPages();
        }), function (error) {
            var a = 1;
        };
    };

    $scope.loadScripts(1);
});

function activeMenu(menu) {
    $('#' + menu).removeClass('alert-warning');
    $('#' + menu).addClass('alert-success');
    $('#' + menu).addClass('active');
}

function parse(val) {
    var result = "Not found",
            tmp = [];
    window.location.search
            .substr(1)
            .split("&")
            .forEach(function (item) {
                tmp = item.split("=");
                if (tmp[0] === val)
                    result = decodeURIComponent(tmp[1]);
            });
    return result;
}