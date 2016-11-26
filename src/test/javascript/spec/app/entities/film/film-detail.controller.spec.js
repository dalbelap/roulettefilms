'use strict';

describe('Controller Tests', function() {

    describe('Film Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockFilm, MockGenre;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockFilm = jasmine.createSpy('MockFilm');
            MockGenre = jasmine.createSpy('MockGenre');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Film': MockFilm,
                'Genre': MockGenre
            };
            createController = function() {
                $injector.get('$controller')("FilmDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'roulettefilmsApp:filmUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
