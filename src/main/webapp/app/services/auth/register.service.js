(function () {
    'use strict';

    angular
        .module('roulettefilmsApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
