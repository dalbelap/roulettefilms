(function() {
    'use strict';

    angular
        .module('roulettefilmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('film', {
            parent: 'entity',
            url: '/film',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'roulettefilmsApp.film.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/film/films.html',
                    controller: 'FilmController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('film');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('film-detail', {
            parent: 'entity',
            url: '/film/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'roulettefilmsApp.film.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/film/film-detail.html',
                    controller: 'FilmDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('film');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Film', function($stateParams, Film) {
                    return Film.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'film',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('film-detail.edit', {
            parent: 'film-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/film/film-dialog.html',
                    controller: 'FilmDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Film', function(Film) {
                            return Film.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('film.new', {
            parent: 'film',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/film/film-dialog.html',
                    controller: 'FilmDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                filmTitle: null,
                                filmYear: null,
                                filmDurationInMinutes: null,
                                filmSynopsis: null,
                                netflixRatingChartNumber: null,
                                filmUrl: null,
                                filmCriticScore: null,
                                filmAudienceScore: null,
                                filmNetflixRating: null,
                                filmBoxArtLink: null,
                                filmCertRating: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('film', null, { reload: 'film' });
                }, function() {
                    $state.go('film');
                });
            }]
        })
        .state('film.edit', {
            parent: 'film',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/film/film-dialog.html',
                    controller: 'FilmDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Film', function(Film) {
                            return Film.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('film', null, { reload: 'film' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('film.delete', {
            parent: 'film',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/film/film-delete-dialog.html',
                    controller: 'FilmDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Film', function(Film) {
                            return Film.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('film', null, { reload: 'film' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
