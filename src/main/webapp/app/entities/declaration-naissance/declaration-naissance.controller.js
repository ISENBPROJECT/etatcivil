(function() {
    'use strict';

    angular
        .module('etatcivilApp')
        .controller('DeclarationNaissanceController', DeclarationNaissanceController);

    DeclarationNaissanceController.$inject = ['$scope', '$state', 'DeclarationNaissance'];

    function DeclarationNaissanceController ($scope, $state, DeclarationNaissance) {
        var vm = this;

        vm.declarationNaissances = [];

        loadAll();

        function loadAll() {
            DeclarationNaissance.query(function(result) {
                vm.declarationNaissances = result;
            });
        }

        function search(){
            DeclarationNaissance =
            DeclarationNaissance.query(function(result) {
                vm.declarationNaissances = result;
            });
        }
    }
})();
