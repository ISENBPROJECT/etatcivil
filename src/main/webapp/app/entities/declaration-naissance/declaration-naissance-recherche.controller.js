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
     
        function search(){
        	console.log(vm.declarationNaissance.informationEnfant.nom);
        	console.log(vm.declarationNaissance.informationEnfant.prenom);
        	var dataSearch = vm.declarationNaissance.id+','+vm.declarationNaissance.informationEnfant.nom+','
        		+vm.declarationNaissance.informationEnfant.prenom+','+vm.declarationNaissance.informationEnfant.dateNaissance;
            DeclarationNaissance.search(dataSearch, onSaveSuccess, onSaveError);
        }
        
        function onSaveSuccess(result) {
        	 vm.declarationNaissances = result;
        }

        function onSaveError() {
            vm.isSaving = false;
        }
    }
})();
