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
    templateUrl: 'templates/tabs.html'
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
        controller: 'AccountCtrl'
      }
    }
  })
  .state('tab.qrcode', {
    url: '/qrcode',
    views: {
      'tab-search': {
        templateUrl: 'templates/qrcode.html',
        controller: 'QrCtrl'
      }
    }
  });

  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/tab/home');

});
