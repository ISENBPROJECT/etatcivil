(function() {
    'use strict';

    angular
        .module('etatcivilApp')
        .controller('DeclarationNaissanceDeleteController',DeclarationNaissanceDeleteController);

    DeclarationNaissanceDeleteController.$inject = ['$uibModalInstance', 'entity', 'DeclarationNaissance'];

    function DeclarationNaissanceDeleteController($uibModalInstance, entity, DeclarationNaissance) {
        var vm = this;

        vm.declarationNaissance = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DeclarationNaissance.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
