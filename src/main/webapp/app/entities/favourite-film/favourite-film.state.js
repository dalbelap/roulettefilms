(function() {
    'use strict';

    angular
        .module('roulettefilmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('favourite-film', {
            parent: 'entity',
            url: '/favourite-film?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'roulettefilmsApp.favouriteFilm.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/favourite-film/favourite-films.html',
                    controller: 'FavouriteFilmController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('favouriteFilm');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('favourite-film-detail', {
            parent: 'entity',
            url: '/favourite-film/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'roulettefilmsApp.favouriteFilm.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/favourite-film/favourite-film-detail.html',
                    controller: 'FavouriteFilmDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('favouriteFilm');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'FavouriteFilm', function($stateParams, FavouriteFilm) {
                    return FavouriteFilm.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'favourite-film',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('favourite-film-detail.edit', {
            parent: 'favourite-film-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/favourite-film/favourite-film-dialog.html',
                    controller: 'FavouriteFilmDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FavouriteFilm', function(FavouriteFilm) {
                            return FavouriteFilm.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('favourite-film.new', {
            parent: 'favourite-film',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/favourite-film/favourite-film-dialog.html',
                    controller: 'FavouriteFilmDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                favouriteFilmTitle: null,
                                favouriteFilmYear: null,
                                favouriteFilmDurationInMinutes: null,
                                favouriteFilmSynopsis: null,
                                favouriteFilmNetflixRating: null,
                                favouriteFilmUserRating: null,
                                createdFavouriteFilm: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('favourite-film', null, { reload: 'favourite-film' });
                }, function() {
                    $state.go('favourite-film');
                });
            }]
        })
        .state('favourite-film.edit', {
            parent: 'favourite-film',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/favourite-film/favourite-film-dialog.html',
                    controller: 'FavouriteFilmDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FavouriteFilm', function(FavouriteFilm) {
                            return FavouriteFilm.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('favourite-film', null, { reload: 'favourite-film' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('favourite-film.delete', {
            parent: 'favourite-film',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/favourite-film/favourite-film-delete-dialog.html',
                    controller: 'FavouriteFilmDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FavouriteFilm', function(FavouriteFilm) {
                            return FavouriteFilm.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('favourite-film', null, { reload: 'favourite-film' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
