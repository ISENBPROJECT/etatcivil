(function () {
    'use strict';

    angular
        .module('etatcivilApp')
        .controller('DeclarationTestController', DeclarationTestController);

    DeclarationTestController.$inject = ['$timeout','$state', '$scope', '$stateParams', 'entity', 'DeclarationNaissance', 'Personne', 'Fichier', 'Upload'];

    function DeclarationTestController($timeout,$state, $scope, $stateParams, entity, DeclarationNaissance, Personne, Fichier, Upload) {

        var vm = this;
        vm.declarationNaissance = entity;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.enregistrer = enregistrer;
        vm.retourDeclaration = retourDeclaration;
        vm.save = save;
        vm.datePickerOpenStatus.dateDeclaration = false;
        vm.datePickerOpenStatus.dateNaissanceEnfant = false;
        vm.datePickerOpenStatus.dateNaissancePere = false;
        vm.datePickerOpenStatus.dateNaissanceMere = false;
        vm.personnes = Personne.query();
        vm.fichiers = Fichier.query();
        vm.uploadFiles = uploadFiles;

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }

        function save() {
        	$state.go('declaration-naissance-detail');
        }
        
        function enregistrer() {
        
        	vm.isSaving = true;
            DeclarationNaissance.save(vm.declarationNaissance).onsuccess(function () {
                alert("enregistrement avec succes");
                console.log('succes');
            })
            
            $state.go('home');
        	
        }
        
        function retourDeclaration() {
            
            $state.go('declaration-test');
            
        	
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
