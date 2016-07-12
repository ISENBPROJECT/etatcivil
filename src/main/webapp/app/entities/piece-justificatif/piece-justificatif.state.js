(function() {
    'use strict';

    angular
        .module('etatcivilApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('piece-justificatif', {
            parent: 'entity',
            url: '/piece-justificatif',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'etatcivilApp.pieceJustificatif.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/piece-justificatif/piece-justificatifs.html',
                    controller: 'PieceJustificatifController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pieceJustificatif');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('piece-justificatif-detail', {
            parent: 'entity',
            url: '/piece-justificatif/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'etatcivilApp.pieceJustificatif.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/piece-justificatif/piece-justificatif-detail.html',
                    controller: 'PieceJustificatifDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pieceJustificatif');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PieceJustificatif', function($stateParams, PieceJustificatif) {
                    return PieceJustificatif.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('piece-justificatif.new', {
            parent: 'piece-justificatif',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/piece-justificatif/piece-justificatif-dialog.html',
                    controller: 'PieceJustificatifDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nomFichier: null,
                                chemin: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('piece-justificatif', null, { reload: true });
                }, function() {
                    $state.go('piece-justificatif');
                });
            }]
        })
        .state('piece-justificatif.edit', {
            parent: 'piece-justificatif',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/piece-justificatif/piece-justificatif-dialog.html',
                    controller: 'PieceJustificatifDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PieceJustificatif', function(PieceJustificatif) {
                            return PieceJustificatif.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('piece-justificatif', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('piece-justificatif.delete', {
            parent: 'piece-justificatif',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/piece-justificatif/piece-justificatif-delete-dialog.html',
                    controller: 'PieceJustificatifDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PieceJustificatif', function(PieceJustificatif) {
                            return PieceJustificatif.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('piece-justificatif', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
