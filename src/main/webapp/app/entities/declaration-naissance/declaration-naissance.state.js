(function() {
    'use strict';

    angular
        .module('etatcivilApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('declaration-naissance', {
            parent: 'entity',
            url: '/declaration-naissance',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'etatcivilApp.declarationNaissance.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/declaration-naissance/declaration-naissances.html',
                    controller: 'DeclarationNaissanceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('declarationNaissance');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('declaration-naissance-detail', {
            parent: 'entity',
            url: '/declaration-naissance/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'etatcivilApp.declarationNaissance.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/declaration-naissance/declaration-naissance-detail.html',
                    controller: 'DeclarationNaissanceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('declarationNaissance');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'DeclarationNaissance', function($stateParams, DeclarationNaissance) {
                    return DeclarationNaissance.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('declaration-naissance.new', {
            parent: 'declaration-naissance',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/declaration-naissance/declaration-naissance-dialog.html',
                    controller: 'DeclarationNaissanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dateDeclaration: null,
                                identifiantEnfant: null,
                                identifiantPere: null,
                                identifiantMere: null,
                                mentionMarginale: null,
                                numeroCarteIdentite: null,
                                numeroPassPort: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('declaration-naissance', null, { reload: true });
                }, function() {
                    $state.go('declaration-naissance');
                });
            }]
        })
        .state('declaration-naissance.edit', {
            parent: 'declaration-naissance',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/declaration-naissance/declaration-naissance-dialog.html',
                    controller: 'DeclarationNaissanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DeclarationNaissance', function(DeclarationNaissance) {
                            return DeclarationNaissance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('declaration-naissance', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('declaration-naissance.delete', {
            parent: 'declaration-naissance',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/declaration-naissance/declaration-naissance-delete-dialog.html',
                    controller: 'DeclarationNaissanceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DeclarationNaissance', function(DeclarationNaissance) {
                            return DeclarationNaissance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('declaration-naissance', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
