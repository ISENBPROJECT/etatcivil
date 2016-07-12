(function() {
    'use strict';

    angular
        .module('etatcivilApp')
        .controller('DeclarationNaissanceDialogController', DeclarationNaissanceDialogController);

    DeclarationNaissanceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DeclarationNaissance', 'Personne', 'PieceJustificatif'];

    function DeclarationNaissanceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DeclarationNaissance, Personne, PieceJustificatif) {
        var vm = this;

        vm.declarationNaissance = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.personnes = Personne.query();
        vm.piecejustificatifs = PieceJustificatif.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.declarationNaissance.id !== null) {
                DeclarationNaissance.update(vm.declarationNaissance, onSaveSuccess, onSaveError);
            } else {
                DeclarationNaissance.save(vm.declarationNaissance, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('etatcivilApp:declarationNaissanceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateDeclaration = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
