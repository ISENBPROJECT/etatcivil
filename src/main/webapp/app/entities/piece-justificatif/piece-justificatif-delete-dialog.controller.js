(function() {
    'use strict';

    angular
        .module('etatcivilApp')
        .controller('PieceJustificatifDeleteController',PieceJustificatifDeleteController);

    PieceJustificatifDeleteController.$inject = ['$uibModalInstance', 'entity', 'PieceJustificatif'];

    function PieceJustificatifDeleteController($uibModalInstance, entity, PieceJustificatif) {
        var vm = this;

        vm.pieceJustificatif = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PieceJustificatif.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
