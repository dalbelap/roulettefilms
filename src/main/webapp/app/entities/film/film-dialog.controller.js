(function() {
    'use strict';

    angular
        .module('roulettefilmsApp')
        .controller('FilmDialogController', FilmDialogController);

    FilmDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Film', 'Genre'];

    function FilmDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Film, Genre) {
        var vm = this;

        vm.film = entity;
        vm.clear = clear;
        vm.save = save;
        vm.genres = Genre.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.film.id !== null) {
                Film.update(vm.film, onSaveSuccess, onSaveError);
            } else {
                Film.save(vm.film, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('roulettefilmsApp:filmUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
