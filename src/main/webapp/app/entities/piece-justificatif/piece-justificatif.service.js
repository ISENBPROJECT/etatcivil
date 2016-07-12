(function() {
    'use strict';
    angular
        .module('etatcivilApp')
        .factory('PieceJustificatif', PieceJustificatif);

    PieceJustificatif.$inject = ['$resource'];

    function PieceJustificatif ($resource) {
        var resourceUrl =  'api/piece-justificatifs/:id';

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
