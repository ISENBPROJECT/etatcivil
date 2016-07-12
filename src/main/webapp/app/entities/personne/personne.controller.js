(function() {
    'use strict';

    angular
        .module('etatcivilApp')
        .controller('PersonneController', PersonneController);

    PersonneController.$inject = ['$scope', '$state', 'Personne'];

    function PersonneController ($scope, $state, Personne) {
        var vm = this;
        
        vm.personnes = [];

        loadAll();

        function loadAll() {
            Personne.query(function(result) {
                vm.personnes = result;
            });
        }
    }
})();
