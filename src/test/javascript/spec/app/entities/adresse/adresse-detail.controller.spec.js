'use strict';

describe('Controller Tests', function() {

    describe('Adresse Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAdresse, MockPersonne;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAdresse = jasmine.createSpy('MockAdresse');
            MockPersonne = jasmine.createSpy('MockPersonne');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Adresse': MockAdresse,
                'Personne': MockPersonne
            };
            createController = function() {
                $injector.get('$controller')("AdresseDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'etatcivilApp:adresseUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
