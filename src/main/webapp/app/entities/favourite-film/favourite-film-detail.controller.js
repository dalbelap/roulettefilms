(function() {
    'use strict';

    angular
        .module('roulettefilmsApp')
        .controller('FavouriteFilmDetailController', FavouriteFilmDetailController);

    FavouriteFilmDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FavouriteFilm', 'Film', 'User'];

    function FavouriteFilmDetailController($scope, $rootScope, $stateParams, previousState, entity, FavouriteFilm, Film, User) {
        var vm = this;

        vm.favouriteFilm = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('roulettefilmsApp:favouriteFilmUpdate', function(event, result) {
            vm.favouriteFilm = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
