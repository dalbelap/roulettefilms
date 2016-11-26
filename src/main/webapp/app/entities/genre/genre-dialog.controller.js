(function() {
    'use strict';

    angular
        .module('roulettefilmsApp')
        .controller('GenreDialogController', GenreDialogController);

    GenreDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Genre', 'Film'];

    function GenreDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Genre, Film) {
        var vm = this;

        vm.genre = entity;
        vm.clear = clear;
        vm.save = save;
        vm.films = Film.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.genre.id !== null) {
                Genre.update(vm.genre, onSaveSuccess, onSaveError);
            } else {
                Genre.save(vm.genre, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('roulettefilmsApp:genreUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
