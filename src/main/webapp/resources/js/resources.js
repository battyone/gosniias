var services = angular.module('services', ['ngResource']);

services.factory('API', ['$resource', function ($resource) {
        return {
            scripts: function () {
                return $resource('/api/groovy/:param', {}, {
                    list: {
                        method: 'GET',
                        params: {
                            param: 'list'
                        }
                    }
                });
            }
        };
    }]);

