(function() {
    'use strict';

    angular
        .module('etatcivilApp')
        .controller('FichierDetailController', FichierDetailController);

    FichierDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Fichier', 'DeclarationNaissance'];

    function FichierDetailController($scope, $rootScope, $stateParams, entity, Fichier, DeclarationNaissance) {
        var vm = this;

        vm.fichier = entity;

        var unsubscribe = $rootScope.$on('etatcivilApp:fichierUpdate', function(event, result) {
            vm.fichier = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
