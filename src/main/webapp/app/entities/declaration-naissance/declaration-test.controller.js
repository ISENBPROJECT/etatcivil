(function() {
    'use strict';

    angular
        .module('etatcivilApp')
        .controller('DeclarationTestController', DeclarationTestController);

    DeclarationTestController.$inject = ['$scope', '$state', 'DeclarationNaissance'];

    function DeclarationTestController ($scope, $state, DeclarationNaissance) {
        var vm = this;

        vm.declarationNaissances = [];

        loadAll();

        function loadAll() {
            DeclarationNaissance.query(function(result) {
                vm.declarationNaissances = result;
            });
        }
    }
})();
