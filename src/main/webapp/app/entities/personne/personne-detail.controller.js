(function() {
    'use strict';

    angular
        .module('etatcivilApp')
        .controller('PersonneDetailController', PersonneDetailController);

    PersonneDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Personne', 'Adresse', 'DeclarationNaissance'];

    function PersonneDetailController($scope, $rootScope, $stateParams, entity, Personne, Adresse, DeclarationNaissance) {
        var vm = this;

        vm.personne = entity;

        var unsubscribe = $rootScope.$on('etatcivilApp:personneUpdate', function(event, result) {
            vm.personne = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
