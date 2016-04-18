
// VARIABLES
//~ url_base     = "http://tgourdel.rtrinity.enseirb-matmeca.fr/api/";
url_base     = "tmp_json/"
url_vitrines = "Vitrines.json";
url_vitrine  = "Vitrine1.json";
url_objet    = "Objet1.json";

angular.module('starter.controllers', [])

.controller('HomeCtrl', function($scope, $ionicTabsDelegate) {
    $scope.selectTabWithIndex = function(index) {
        $ionicTabsDelegate.select(index);
    }
    
    // On fait un ng-repeat pour que l'anim. ne se fasse qu'une fois
    $scope.buttonshome = [
        { click:"selectTabWithIndex(1)",
          text:"Liste des vitrines",
          color:"positive" },
        { click:"selectTabWithIndex(2)",
          text:"Rechercher un objet",
          color:"balanced" }];
})

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


/* ======================== Fetch d'un objet ======================== */

app.controller("ObjetCtrl", function($scope, $stateParams, $http, $ionicSlideBoxDelegate) {
    
    // TODO : Récupération dynamique
    var url = url_base + url_objet; // + $stateParams.id;
    
    $http.get(url).success(function(response) {
        
        $scope.objet = response;
        
        // Update des slides ; cf http://ionicframework.com/docs/api/service/%24ionicSlideBoxDelegate/
        $ionicSlideBoxDelegate.update();
    });
    
    return $scope.objet;
});
/* ======================== QR Code ======================== */

app.controller("QrCtrl",function($scope,$cordovaBarcodeScanner){

  $scope.lireCode=function(){
    $cordovaBarcodeScanner.scan().then( function(image_a_scan){
        alert(image_a_scan.text)
    },function(error){
        alert("Erreur !"+error)
    });
  }
});
