(function () {
    'use strict';

    angular
        .module('etatcivilApp')
        .controller('DeclarationTestController', DeclarationTestController);

    DeclarationTestController.$inject = ['$timeout', '$scope', '$stateParams', 'entity', 'DeclarationNaissance', 'Personne','Fichier'];

    function DeclarationTestController($timeout, $scope, $stateParams, entity, DeclarationNaissance, Personne,Fichier) {
        var vm = this;
        vm.declarationNaissance = entity;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.datePickerOpenStatus.dateDeclaration = false;
        vm.datePickerOpenStatus.dateNaissance = false;
        vm.personnes = Personne.query();
        vm.fichiers = Fichier.query();

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }

        function save() {
            vm.isSaving = true;
            DeclarationNaissance.save(vm.declarationNaissance);
        }
    }
})();
