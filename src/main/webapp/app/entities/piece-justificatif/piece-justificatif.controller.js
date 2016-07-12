(function() {
    'use strict';

    angular
        .module('etatcivilApp')
        .controller('PieceJustificatifController', PieceJustificatifController);

    PieceJustificatifController.$inject = ['$scope', '$state', 'PieceJustificatif'];

    function PieceJustificatifController ($scope, $state, PieceJustificatif) {
        var vm = this;
        
        vm.pieceJustificatifs = [];

        loadAll();

        function loadAll() {
            PieceJustificatif.query(function(result) {
                vm.pieceJustificatifs = result;
            });
        }
    }
})();
