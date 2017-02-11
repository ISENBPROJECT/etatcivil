(function() {
    'use strict';
    angular
        .module('etatcivilApp')
        .factory('DeclarationNaissance', DeclarationNaissance);

    DeclarationNaissance.$inject = ['$resource', 'DateUtils'];

    function DeclarationNaissance ($resource, DateUtils) {
        var resourceUrl =  'api/declaration-naissances/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: false},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateDeclaration = DateUtils.convertLocalDateToServer(data.dateDeclaration);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dateDeclaration = DateUtils.convertLocalDateToServer(data.dateDeclaration);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dateDeclaration = DateUtils.convertLocalDateToServer(data.dateDeclaration);
                    return angular.toJson(data);
                }
            },
            'search': {
                method: 'POST',
                transformResponse: function (data) {
                	 if (data) {
                         data = angular.fromJson(data);
                     }
                	 return data;
                },
                isArray: true
            }

        });


    }
//    function DeclarationNaissance($resource, DateUtils) {
//        var resourceUrl =  'api/searchDeclaration/';
//        return $resource(resourceUrl, {}, {
//        	'search': {
//                method: 'POST',
//                transformResponse: function (data) {
//                	 if (data) {
//                         data = angular.fromJson(data);
//                     }
//                	 return data;
//                },
//                isArray: true
//            }
//        });
//    }
})();
