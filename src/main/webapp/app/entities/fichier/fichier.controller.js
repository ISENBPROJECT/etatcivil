(function() {
    'use strict';

    angular
        .module('etatcivilApp')
        .controller('FichierController', FichierController);

    FichierController.$inject = ['$scope', '$state', 'Fichier'];

    function FichierController ($scope, $state, Fichier) {
        var vm = this;
        
        vm.fichiers = [];

        loadAll();

        function loadAll() {
            Fichier.query(function(result) {
                vm.fichiers = result;
            });
        }
    }
})();
