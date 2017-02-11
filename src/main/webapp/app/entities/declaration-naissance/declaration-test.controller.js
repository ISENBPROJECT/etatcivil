
(function() {
	'use strict';

	angular.module('etatcivilApp').controller('DeclarationTestController',
			DeclarationTestController);

	DeclarationTestController.$inject = [ '$timeout', '$state', '$scope',
			'$stateParams', 'entity', 'DeclarationNaissance', 'Personne',
			'Fichier', 'Upload' ];

	function DeclarationTestController($timeout, $state, $scope, $stateParams,
			entity, DeclarationNaissance, Personne, Fichier, Upload) {

		var vm = this;
		vm.declarationNaissance = entity;
		vm.datePickerOpenStatus = {};
		vm.openCalendar = openCalendar;
		vm.enregistrer = enregistrer;
		vm.retourDeclaration = retourDeclaration;
		vm.save = save;
		vm.datePickerOpenStatus.dateDeclaration = false;
		vm.datePickerOpenStatus.dateNaissanceEnfant = false;
		vm.datePickerOpenStatus.dateNaissancePere = false;
		vm.datePickerOpenStatus.dateNaissanceMere = false;
		vm.personnes = Personne.query();
		vm.fichiers = Fichier.query();
		vm.uploadFiles = uploadFiles;
		vm.F_PrintFile = F_PrintFile;
		vm.retourAccueil = retourAccueil;
		vm.ouvirActeNaissancePopup = ouvirActeNaissancePopup;
		vm.ouvirTranscriptionPopup = ouvirTranscriptionPopup;

		function F_PrintFile() {
			fichierpdf.print();
		}

		function openCalendar(date) {
			vm.datePickerOpenStatus[date] = true;
		}

		function save() {
            console.log(entity);
			$state.go('declaration-naissance-detail');
		}

		function enregistrer() {

			vm.isSaving = true;
			DeclarationNaissance.save(vm.declarationNaissance);
			// DeclarationNaissance.save(vm.declarationNaissance).onsuccess(function
			// () {
			// alert("enregistrement avec succes");
			// console.log('succes');
			// })

			// $state.go('home');
			$state.go('declaration-naissance-affichagePdf');

		}

		function retourDeclaration() {
			$state.go('declaration-test');

		}

		function ouvirActeNaissancePopup() {

			var fichier = 'app/document/'+ vm.declarationNaissance.informationEnfant.prenom + '_'+ vm.declarationNaissance.informationEnfant.nom + '_acte_naissance.pdf';
			 window.open(fichier, "popup", "width=900,height=600")
		}

		function ouvirTranscriptionPopup() {

			var fichier = 'app/document/'+ vm.declarationNaissance.informationEnfant.prenom + '_'+ vm.declarationNaissance.informationEnfant.nom + '_transcription_naissance.pdf';
			window.open(fichier, "popup", "width=900,height=600")
		}

		function retourAccueil() {
			$state.go('home');

		}

		function uploadFiles(files, errFiles) {
			$scope.files = files;
			var filename1;
			files.forEach(function(file) {
				filename1 = file.name;
			})
			var filename2;
			var fichier = {
				nomFichier : filename1,
				chemin : 'src\/main\/webapp\/app\/document'
			};

			vm.declarationNaissance.fichier = fichier;
		}
	}

})();
