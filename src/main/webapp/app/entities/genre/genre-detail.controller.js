(function() {
    'use strict';

    angular
        .module('roulettefilmsApp')
        .controller('GenreDetailController', GenreDetailController);

    GenreDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Genre', 'Film'];

    function GenreDetailController($scope, $rootScope, $stateParams, previousState, entity, Genre, Film) {
        var vm = this;

        vm.genre = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('roulettefilmsApp:genreUpdate', function(event, result) {
            vm.genre = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
