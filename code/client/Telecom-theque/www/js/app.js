// Ionic Starter App

// VARIABLES
url_base     = "tmp_json/";
url_vitrines = "Vitrines.json";
url_vitrine  = "Vitrine1.json";
url_objet    = "Objet1.json";

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
var app = angular.module('starter', ['ionic']);

app.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
    if(window.cordova && window.cordova.plugins.Keyboard) {
      // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
      // for form inputs)
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);

      // Don't remove this line unless you know what you are doing. It stops the viewport
      // from snapping when text inputs are focused. Ionic handles this internally for
      // a much nicer keyboard experience.
      cordova.plugins.Keyboard.disableScroll(true);
    }
    if(window.StatusBar) {
      StatusBar.styleDefault();
    }
  });
})

/* ===================== Fetch des vitrines ========================= */

app.controller("VitrinesCtrl", function($scope, $http) {
    
    var url = url_base + url_vitrines;
    
    $http.get(url).success(function(response) {
        
        $scope.vitrines = response;
    });
    
    return $scope.vitrines;
});

/* ================ Fetch des objets pour une vitrine =============== */

app.controller("VitrineCtrl", function($scope, $stateParams, $http) {
    
    // TODO : Récupération dynamique
    var url = url_base + url_vitrine; // + $stateParams.id;
    
    $http.get(url).success(function(response) {
        
        $scope.vitrine = response;
    });
    
    return $scope.vitrine;
});

/* ======================== Fetch d'un objet ======================== */

app.controller("ObjetCtrl", function($scope, $stateParams, $http) {
    
    // TODO : Récupération dynamique
    var url = url_base + url_objet; // + $stateParams.id;
    
    $http.get(url).success(function(response) {
        
        $scope.objet = response;
    });
    
    return $scope.objet;
});
