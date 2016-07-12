'use strict';

describe('Controller Tests', function() {

    describe('PieceJustificatif Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPieceJustificatif, MockDeclarationNaissance;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPieceJustificatif = jasmine.createSpy('MockPieceJustificatif');
            MockDeclarationNaissance = jasmine.createSpy('MockDeclarationNaissance');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'PieceJustificatif': MockPieceJustificatif,
                'DeclarationNaissance': MockDeclarationNaissance
            };
            createController = function() {
                $injector.get('$controller')("PieceJustificatifDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'etatcivilApp:pieceJustificatifUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
