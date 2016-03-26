
app.config(function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/')

    $stateProvider.state('home', {
        url: '/',
        templateUrl: 'views/home.html'
    })

    $stateProvider.state('vitrines', {
        url: '/vitrines',
        templateUrl: 'views/vitrines.html',
        controller: "VitrinesCtrl"
    })
    
    // Récupération des objets d'une vitrine
    $stateProvider.state('vitrine', {
      url: '/vitrine/:id',
      templateUrl: 'views/vitrine.html',
      controller: 'VitrineCtrl'
    })
    
    // Récupération d'un obj
    $stateProvider.state('objet', {
      url: '/objet/:id',
      templateUrl: 'views/objet.html',
      controller: 'ObjetCtrl'
    })
  
})
