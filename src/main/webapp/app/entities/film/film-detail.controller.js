(function() {
    'use strict';

    angular
        .module('roulettefilmsApp')
        .controller('FilmDetailController', FilmDetailController);

    FilmDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Film', 'Genre'];

    function FilmDetailController($scope, $rootScope, $stateParams, previousState, entity, Film, Genre) {
        var vm = this;

        vm.film = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('roulettefilmsApp:filmUpdate', function(event, result) {
            vm.film = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
