'use strict';

describe('Controller Tests', function() {

    describe('FavouriteFilm Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockFavouriteFilm, MockFilm, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockFavouriteFilm = jasmine.createSpy('MockFavouriteFilm');
            MockFilm = jasmine.createSpy('MockFilm');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'FavouriteFilm': MockFavouriteFilm,
                'Film': MockFilm,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("FavouriteFilmDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'roulettefilmsApp:favouriteFilmUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
