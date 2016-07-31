(function() {
    'use strict';

    angular
        .module('etatcivilApp')
        .controller('PersonneDialogController', PersonneDialogController);

    PersonneDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Personne', 'Adresse', 'DeclarationNaissance'];

    function PersonneDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Personne, Adresse, DeclarationNaissance) {
        var vm = this;

        vm.personne = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.adresses = Adresse.query({filter: 'identifiantpersonne(id)-is-null'});
        $q.all([vm.personne.$promise, vm.adresses.$promise]).then(function() {
            if (!vm.personne.adresseId) {
                return $q.reject();
            }
            return Adresse.get({id : vm.personne.adresseId}).$promise;
        }).then(function(adresse) {
            vm.adresses.push(adresse);
        });
        vm.declarationnaissances = DeclarationNaissance.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personne.id !== null) {
                Personne.update(vm.personne, onSaveSuccess, onSaveError);
            } else {
                Personne.save(vm.personne, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('etatcivilApp:personneUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateNaissance = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
