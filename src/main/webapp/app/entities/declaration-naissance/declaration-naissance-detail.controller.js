(function() {
    'use strict';

    angular
        .module('etatcivilApp')
        .controller('DeclarationNaissanceDetailController', DeclarationNaissanceDetailController);

    DeclarationNaissanceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'DeclarationNaissance', 'Personne', 'PieceJustificatif'];

    function DeclarationNaissanceDetailController($scope, $rootScope, $stateParams, entity, DeclarationNaissance, Personne, PieceJustificatif) {
        var vm = this;

        vm.declarationNaissance = entity;

        var unsubscribe = $rootScope.$on('etatcivilApp:declarationNaissanceUpdate', function(event, result) {
            vm.declarationNaissance = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
