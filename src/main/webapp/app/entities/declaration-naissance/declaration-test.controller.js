(function () {
    'use strict';

    angular
        .module('etatcivilApp')
        .controller('DeclarationTestController', DeclarationTestController);

    DeclarationTestController.$inject = ['$timeout', '$scope', '$stateParams', 'entity', 'DeclarationNaissance', 'Personne','Fichier','Upload'];

    function DeclarationTestController($timeout, $scope, $stateParams, entity, DeclarationNaissance, Personne,Fichier,Upload) {

        var vm = this;
        vm.declarationNaissance = entity;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.kaze = null;
        vm.datePickerOpenStatus.dateDeclaration = false;
        vm.datePickerOpenStatus.dateNaissance = false;
        vm.personnes = Personne.query();
        vm.fichiers = Fichier.query();

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }

        function save() {
            vm.isSaving = true;
            DeclarationNaissance.save(vm.declarationNaissance);
        }

        $scope.uploadFiles = function(files, errFiles) {
            $scope.files = files;
            var filename1;
            var filename2;
            files.forEach(function(file) {
                filename1  = file.name;
            })
            DeclarationNaissance.upload(filename1);
            $scope.errFiles = errFiles;

          /*  angular.forEach(files, function(file) {
                file.upload = Upload.upload({
                    url: 'https://angular-file-upload-cors-srv.appspot.com/upload',
                    data: {file: file}
                });

                file.upload.then(function (response) {
                    $timeout(function () {
                        file.result = response.data;
                    });
                }, function (response) {
                    if (response.status > 0)
                        $scope.errorMsg = response.status + ': ' + response.data;
                }, function (evt) {
                    file.progress = Math.min(100, parseInt(100.0 *
                        evt.loaded / evt.total));
                });
            });*/
        }
    }
})();
