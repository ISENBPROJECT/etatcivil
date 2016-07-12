(function() {
    'use strict';

    angular
        .module('etatcivilApp')
        .controller('PieceJustificatifDetailController', PieceJustificatifDetailController);

    PieceJustificatifDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'PieceJustificatif', 'DeclarationNaissance'];

    function PieceJustificatifDetailController($scope, $rootScope, $stateParams, entity, PieceJustificatif, DeclarationNaissance) {
        var vm = this;

        vm.pieceJustificatif = entity;

        var unsubscribe = $rootScope.$on('etatcivilApp:pieceJustificatifUpdate', function(event, result) {
            vm.pieceJustificatif = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
