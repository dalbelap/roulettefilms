(function() {
    'use strict';

    angular
        .module('roulettefilmsApp')
        .controller('FavouriteFilmDeleteController',FavouriteFilmDeleteController);

    FavouriteFilmDeleteController.$inject = ['$uibModalInstance', 'entity', 'FavouriteFilm'];

    function FavouriteFilmDeleteController($uibModalInstance, entity, FavouriteFilm) {
        var vm = this;

        vm.favouriteFilm = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FavouriteFilm.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
