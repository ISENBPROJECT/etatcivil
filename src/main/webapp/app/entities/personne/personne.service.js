(function() {
    'use strict';
    angular
        .module('etatcivilApp')
        .factory('Personne', Personne);

    Personne.$inject = ['$resource', 'DateUtils'];

    function Personne ($resource, DateUtils) {
        var resourceUrl =  'api/personnes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateNaissance = DateUtils.convertLocalDateFromServer(data.dateNaissance);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dateNaissance = DateUtils.convertLocalDateToServer(data.dateNaissance);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dateNaissance = DateUtils.convertLocalDateToServer(data.dateNaissance);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
