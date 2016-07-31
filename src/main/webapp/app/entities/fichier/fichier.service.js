(function() {
    'use strict';
    angular
        .module('etatcivilApp')
        .factory('Fichier', Fichier);

    Fichier.$inject = ['$resource'];

    function Fichier ($resource) {
        var resourceUrl =  'api/fichiers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
