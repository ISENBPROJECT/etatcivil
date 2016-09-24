(function() {
    'use strict';

    angular
        .module('etatcivilApp')
        .controller('DeclarationNaissanceRechercheController', DeclarationNaissanceRechercheController);

    DeclarationNaissanceRechercheController.$inject = ['$scope', '$state', 'DeclarationNaissance'];

    function DeclarationNaissanceRechercheController ($scope, $state, DeclarationNaissance) {
        var vm = this;
        
        vm.declarationNaissances = [];
        vm.search = search;

        loadAll();
        function loadAll() {
            DeclarationNaissance.query(function(result) {
                vm.declarationNaissances = result;
                console.log(vm.declarationNaissances)
            });
        }
//        console.log(vm.declarationNaissance.informationEnfant.prenom);
//        function search() {
//            DeclarationNaissance.search(function(result) {
//                vm.declarationNaissances = result;
//            });
//        }
//        
        function search(){
//            DeclarationNaissance.search(vm.declarationNaissance, onSaveSuccess, onSaveError);
        	console.log(vm.declarationNaissance.informationEnfant.nom);
        	console.log(vm.declarationNaissance.informationEnfant.prenom);
        	var dataSearch = vm.declarationNaissance.id+','+vm.declarationNaissance.informationEnfant.nom+','
        		+vm.declarationNaissance.informationEnfant.prenom+','+vm.declarationNaissance.informationEnfant.dateNaissance;
            DeclarationNaissance.search(dataSearch);

        }
        
        function onSaveSuccess(result) {
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }
    }
})();
