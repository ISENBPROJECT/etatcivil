'use strict';

describe('Controller Tests', function() {

    describe('DeclarationNaissance Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDeclarationNaissance, MockPersonne, MockFichier;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDeclarationNaissance = jasmine.createSpy('MockDeclarationNaissance');
            MockPersonne = jasmine.createSpy('MockPersonne');
            MockFichier = jasmine.createSpy('MockFichier');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'DeclarationNaissance': MockDeclarationNaissance,
                'Personne': MockPersonne,
                'Fichier': MockFichier
            };
            createController = function() {
                $injector.get('$controller')("DeclarationNaissanceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'etatcivilApp:declarationNaissanceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
