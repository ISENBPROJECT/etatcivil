(function() {
    'use strict';

    angular
        .module('etatcivilApp')
        .controller('PieceJustificatifDialogController', PieceJustificatifDialogController);

    PieceJustificatifDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PieceJustificatif', 'DeclarationNaissance'];

    function PieceJustificatifDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PieceJustificatif, DeclarationNaissance) {
        var vm = this;

        vm.pieceJustificatif = entity;
        vm.clear = clear;
        vm.save = save;
        vm.declarationnaissances = DeclarationNaissance.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pieceJustificatif.id !== null) {
                PieceJustificatif.update(vm.pieceJustificatif, onSaveSuccess, onSaveError);
            } else {
                PieceJustificatif.save(vm.pieceJustificatif, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('etatcivilApp:pieceJustificatifUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
