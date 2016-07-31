(function() {
    'use strict';

    angular
        .module('etatcivilApp')
        .controller('FichierDialogController', FichierDialogController);

    FichierDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Fichier', 'DeclarationNaissance'];

    function FichierDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Fichier, DeclarationNaissance) {
        var vm = this;

        vm.fichier = entity;
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
            if (vm.fichier.id !== null) {
                Fichier.update(vm.fichier, onSaveSuccess, onSaveError);
            } else {
                Fichier.save(vm.fichier, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('etatcivilApp:fichierUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
