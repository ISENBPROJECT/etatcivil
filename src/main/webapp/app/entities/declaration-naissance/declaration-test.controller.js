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
        vm.uploadedFile = uploadedFile;
        vm.kaze = null;
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

        function uploadedFile(element) {
            $scope.$apply(function($scope) {
                $scope.files = element.files;
                vm.declarationNaissance.fichier.nomFichier = $scope.files
                console.log(vm.declarationNaissance.fichier)

            });
        }
    }
})();
