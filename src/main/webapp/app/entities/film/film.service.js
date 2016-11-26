(function() {
    'use strict';
    angular
        .module('roulettefilmsApp')
        .factory('Film', Film);

    Film.$inject = ['$resource'];

    function Film ($resource) {
        var resourceUrl =  'api/films/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
