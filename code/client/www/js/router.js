/* ============ Règle de routage de l'application ========= */

app.config(function($stateProvider, $urlRouterProvider) {

    // Ionic uses AngularUI Router which uses the concept of states
    // Learn more here: https://github.com/angular-ui/ui-router
    // Set up the various states which the app can be in.
    // Each state's controller can be found in controllers.js
    $stateProvider

    // setup an abstract state for the tabs directive
    .state('tab', {
        url: '/tab',
        abstract: true,
        templateUrl: 'templates/tabs.html',
        controller: 'AllCtrl' // Un contrôleur pour les contrôler tous
    })

    // Each tab has its own nav history stack:
    .state('tab.home', {
        url: '/home',
        views: {
            'tab-home': {
                templateUrl: 'templates/tab-home.html',
                controller: 'HomeCtrl'
            }
        }
    })

    .state('tab.vitrines', {
        url: '/vitrines',
        views: {
            // Le nom de la vue est important, c'est ce qui détermine
            // l'appartenance au même onglet (=> historique de nav.)
            'tab-vitrines': {
                templateUrl: 'templates/tab-vitrines.html',
                controller: 'VitrinesCtrl'
            }
        }
    })
    .state('tab.vitrine', {
        url: '/vitrine/:id',
        views: {
            'tab-vitrines': {
                templateUrl: 'templates/vitrine.html',
                controller: 'VitrineCtrl'
            }
        }
    })
    // Récupération d'un obj
    .state('tab.objet', {
        url: '/objet/:id',
        views: {
            'tab-vitrines': {
                templateUrl: 'templates/objet.html',
                controller: 'ObjetCtrl'
            }
        }
    })
    .state('tab.search', {
        url: '/search',
        views: {
            'tab-search': {
                templateUrl: 'templates/tab-search.html',
                controller: 'SearchCtrl'
            }
        },
        resolve: {
            useYear: function(GetJSON){
                return GetJSON.getdata("objets/dates");

            }
        }
    })
    // Récupération d'un obj suite à une recherche
    // (permet de rester dans l'onglet + historique de nav.)
    .state('tab.search-obj', {
        url: '/search/:id',
        views: {
            'tab-search': {
                templateUrl: 'templates/objet.html',
                controller: 'ObjetCtrl'
            }
        }
    });

    // if none of the above states are matched, use this as the fallback
    $urlRouterProvider.otherwise('/tab/home');

});
