
// VARIABLES
//~ url_base     = "http://tgourdel.rtrinity.enseirb-matmeca.fr/api/";
url_base     = "tmp_json/"
url_vitrines = "Vitrines.json";
url_vitrine  = "Vitrine1.json";
url_objet    = "Objet1.json";

angular.module('starter.controllers', [])

.controller('HomeCtrl', function($scope) {})

.controller('ChatsCtrl', function($scope, Chats) {
  // With the new view caching in Ionic, Controllers are only called
  // when they are recreated or on app start, instead of every page change.
  // To listen for when this page is active (for example, to refresh data),
  // listen for the $ionicView.enter event:
  //
  //$scope.$on('$ionicView.enter', function(e) {
  //});

  $scope.chats = Chats.all();
  $scope.remove = function(chat) {
    Chats.remove(chat);
  };
})

.controller('ChatDetailCtrl', function($scope, $stateParams, Chats) {
  $scope.chat = Chats.get($stateParams.chatId);
})

.controller('AccountCtrl', function($scope) {
  $scope.settings = {
    enableFriends: true
  };
});

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
