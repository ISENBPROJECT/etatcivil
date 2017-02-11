(function () {
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
             .state('declaration-naissance-recherche', {
                parent: 'entity',
                url: '/declaration-naissance-recherche',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'etatcivilApp.declarationNaissance.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/declaration-naissance/declaration-naissances-recherche.html',
                        controller: 'DeclarationNaissanceRechercheController',
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
            .state('declaration-test', {
                parent: 'entity',
                url: '/declaration-test',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'etatcivilApp.declarationNaissance.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/declaration-naissance/declaration-test.html',
                        controller: 'DeclarationTestController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('declarationNaissance');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],

                    entity: function () {
                        return {
                            dateDeclaration: new Date(),
                            informationEnfant: {nom :null,genre :null, prenom :null, dateNaissance :null, adresse:{codePostale:null,ville:null,adresseComplementaire:null}},
                            informationPere: {nom :null, prenom :null, dateNaissance :null,fonction :null,villeNaissance :null,paysNaissance :null, adresse:{codePostale:null,ville:null,adresseComplementaire:null}},
                            informationMere: {nom :null, prenom :null, dateNaissance :null,fonction :null,villeNaissance :null,paysNaissance :null, adresse:{codePostale:null,ville:null,adresseComplementaire:null}},
                            mentionMarginale: null,
                            fichier:{}
                        };
                    }
                }
            })
            .state('declaration-naissance-detail', {
                parent: 'declaration-test',
                url: '/detail',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'etatcivilApp.declarationNaissance.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/declaration-naissance/declaration-naissance-detail.html',
                        controller: 'DeclarationTestController',
                        controllerAs: 'vm'
                    }
                }
            })
            .state('declaration-naissance.new', {
                parent: 'home',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },


                views: {
                    'content@': {
                        templateUrl: 'app/entities/declaration-naissance/declaration-naissance-dialog.html',
                        controller: 'DeclarationNaissanceDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                    }
                },
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

            })


            .state('declaration-naissance.edit', {
                parent: 'declaration-naissance',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/declaration-naissance/declaration-naissance-dialog.html',
                        controller: 'DeclarationNaissanceDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['DeclarationNaissance', function (DeclarationNaissance) {
                                return DeclarationNaissance.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('declaration-naissance', null, {reload: true});
                    }, function () {
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
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/declaration-naissance/declaration-naissance-delete-dialog.html',
                        controller: 'DeclarationNaissanceDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['DeclarationNaissance', function (DeclarationNaissance) {
                                return DeclarationNaissance.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('declaration-naissance', null, {reload: true});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })

            .state('declaration-naissance-affichagePdf', {
                parent: 'declaration-naissance-detail',
                url: '/affichagePdf',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'etatcivilApp.declarationNaissance.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/declaration-naissance/affichagePdf.html',
                        controller: 'DeclarationTestController',
                        controllerAs: 'vm'
                    }
                },

            });
    }

})();
