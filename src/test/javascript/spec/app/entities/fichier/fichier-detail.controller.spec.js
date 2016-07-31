'use strict';

describe('Controller Tests', function() {

    describe('Fichier Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFichier, MockDeclarationNaissance;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFichier = jasmine.createSpy('MockFichier');
            MockDeclarationNaissance = jasmine.createSpy('MockDeclarationNaissance');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Fichier': MockFichier,
                'DeclarationNaissance': MockDeclarationNaissance
            };
            createController = function() {
                $injector.get('$controller')("FichierDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'etatcivilApp:fichierUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
