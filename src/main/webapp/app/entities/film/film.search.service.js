(function() {
    'use strict';

    angular
        .module('roulettefilmsApp')
        .factory('FilmSearch', FilmSearch);

    FilmSearch.$inject = ['$resource'];

    function FilmSearch($resource) {
        var resourceUrl =  'api/_search/films/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
