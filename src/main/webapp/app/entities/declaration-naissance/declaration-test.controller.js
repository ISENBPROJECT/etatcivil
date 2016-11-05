(function () {
    'use strict';

    angular
        .module('etatcivilApp')
        .controller('DeclarationTestController', DeclarationTestController);

    DeclarationTestController.$inject = ['$timeout', '$scope', '$stateParams', 'entity', 'DeclarationNaissance', 'Personne', 'Fichier', 'Upload'];

    function DeclarationTestController($timeout, $scope, $stateParams, entity, DeclarationNaissance, Personne, Fichier, Upload) {

        var vm = this;
        vm.declarationNaissance = entity;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.datePickerOpenStatus.dateDeclaration = false;
        vm.datePickerOpenStatus.dateNaissance = false;
        vm.personnes = Personne.query();
        vm.fichiers = Fichier.query();
        vm.uploadFiles = uploadFiles;

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }

        function save() {
            vm.isSaving = true;
            DeclarationNaissance.save(vm.declarationNaissance);
        }

        function uploadFiles(files, errFiles) {
            $scope.files = files;
            var filename1;
            files.forEach(function (file) {
                filename1 = file.name;
            })
            var filename2;
            var fichier = {nomFichier: filename1, chemin: 'C:\/Users\/mroum\/OneDrive\/Documents\/repetatcivil'};

            vm.declarationNaissance.fichier = fichier;
        }
    }
})();
