(function() {
    'use strict';

    angular
        .module('roulettefilmsApp')
        .controller('FavouriteFilmDialogController', FavouriteFilmDialogController);

    FavouriteFilmDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'FavouriteFilm', 'Film', 'User'];

    function FavouriteFilmDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, FavouriteFilm, Film, User) {
        var vm = this;

        vm.favouriteFilm = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.films = Film.query({filter: 'favouritefilm-is-null'});
        $q.all([vm.favouriteFilm.$promise, vm.films.$promise]).then(function() {
            if (!vm.favouriteFilm.filmId) {
                return $q.reject();
            }
            return Film.get({id : vm.favouriteFilm.filmId}).$promise;
        }).then(function(film) {
            vm.films.push(film);
        });
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.favouriteFilm.id !== null) {
                FavouriteFilm.update(vm.favouriteFilm, onSaveSuccess, onSaveError);
            } else {
                FavouriteFilm.save(vm.favouriteFilm, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('roulettefilmsApp:favouriteFilmUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdFavouriteFilm = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
