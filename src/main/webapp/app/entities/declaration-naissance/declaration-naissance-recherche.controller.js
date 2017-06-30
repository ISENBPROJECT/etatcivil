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
        	dataSearch ='fall';
        	DeclarationNaissance.search({id: dataSearch},
        			onSuccess,onError);
        	
                                 
        }
        
        function onSuccess(data,headers) {
        	 vm.declarationNaissances = data;
        }

        function onError() {
            vm.isSaving = false;
        }
        
        
        
        
        
//        
//        function loadAll () {
//            User.query({
//                page: pagingParams.page - 1,
//                size: vm.itemsPerPage,
//                sort: sort()
//            }, onSuccess, onError);
//        }
//        function onSuccess (data, headers) {
//            //hide anonymous user from user management: it's a required user for Spring Security
//            for (var i in data) {
//                if (data[i]['login'] === 'anonymoususer') {
//                    data.splice(i, 1);
//                }
//            }
//            vm.links = ParseLinks.parse(headers('link'));
//            vm.totalItems = headers('X-Total-Count');
//            vm.queryCount = vm.totalItems;
//            vm.page = pagingParams.page;
//            vm.users = data;
//        }
//        function onError (error) {
//            AlertService.error(error.data.message);
//        }
        
        
    }
})();
