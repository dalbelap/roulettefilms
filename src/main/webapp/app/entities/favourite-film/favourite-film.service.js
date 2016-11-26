(function() {
    'use strict';
    angular
        .module('roulettefilmsApp')
        .factory('FavouriteFilm', FavouriteFilm);

    FavouriteFilm.$inject = ['$resource', 'DateUtils'];

    function FavouriteFilm ($resource, DateUtils) {
        var resourceUrl =  'api/favourite-films/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdFavouriteFilm = DateUtils.convertDateTimeFromServer(data.createdFavouriteFilm);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
