(function() {
    'use strict';

    angular
        .module('roulettefilmsApp')
        .factory('GenreSearch', GenreSearch);

    GenreSearch.$inject = ['$resource'];

    function GenreSearch($resource) {
        var resourceUrl =  'api/_search/genres/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
