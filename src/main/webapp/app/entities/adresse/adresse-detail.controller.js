(function() {
    'use strict';

    angular
        .module('etatcivilApp')
        .controller('AdresseDetailController', AdresseDetailController);

    AdresseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Adresse', 'Personne'];

    function AdresseDetailController($scope, $rootScope, $stateParams, entity, Adresse, Personne) {
        var vm = this;

        vm.adresse = entity;

        var unsubscribe = $rootScope.$on('etatcivilApp:adresseUpdate', function(event, result) {
            vm.adresse = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
