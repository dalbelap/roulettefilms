(function() {
    'use strict';

    angular
        .module('roulettefilmsApp')
        .factory('FavouriteFilmSearch', FavouriteFilmSearch);

    FavouriteFilmSearch.$inject = ['$resource'];

    function FavouriteFilmSearch($resource) {
        var resourceUrl =  'api/_search/favourite-films/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
