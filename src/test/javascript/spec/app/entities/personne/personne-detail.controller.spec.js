'use strict';

describe('Controller Tests', function() {

    describe('Personne Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPersonne, MockAdresse, MockDeclarationNaissance;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPersonne = jasmine.createSpy('MockPersonne');
            MockAdresse = jasmine.createSpy('MockAdresse');
            MockDeclarationNaissance = jasmine.createSpy('MockDeclarationNaissance');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Personne': MockPersonne,
                'Adresse': MockAdresse,
                'DeclarationNaissance': MockDeclarationNaissance
            };
            createController = function() {
                $injector.get('$controller')("PersonneDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'etatcivilApp:personneUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
