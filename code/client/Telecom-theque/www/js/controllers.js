angular.module('starter.controllers', [])

.controller('HomeCtrl', function($scope, $ionicTabsDelegate) {
    $scope.selectTabWithIndex = function(index) {
        $ionicTabsDelegate.select(index);
    }
    
    // On fait un ng-repeat pour que l'anim. ne se fasse qu'une fois
    $scope.buttonshome = [
        { click:"selectTabWithIndex(1)",
          text:"Liste des vitrines",
          color:"positive",
          icon:"ios-browsers" },
        { click:"selectTabWithIndex(2)",
          text:"Rechercher un objet",
          color:"balanced",
          icon:"ios-search" },
        { click:"selectTabWithIndex(2)",
          text:"Scanner un QR Code",
          color:"royal",
          icon:"qr-scanner" }];
          
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

app.controller("VitrinesCtrl", function($scope, GetJSON) {
    
    GetJSON.getdata("vitrines/").then(function(d) {
        $scope.vitrines = d;
    });
    
});


/* ================ Fetch des objets pour une vitrine =============== */

app.controller("VitrineCtrl", function($scope, $stateParams, GetJSON) {
    
    GetJSON.getdata("vitrines/" + $stateParams.id).then(function(d) {
        $scope.vitrine = d;
    });
    
});


/* ======================== Fetch d'un objet ======================== */

app.controller("ObjetCtrl", function($scope, $stateParams, GetJSON, $ionicSlideBoxDelegate) {
    
    GetJSON.getdata("objets/" + $stateParams.id).then(function(d) {
        $scope.objet = d;
        
        // Update des slides ; cf http://ionicframework.com/docs/api/service/%24ionicSlideBoxDelegate/
        $ionicSlideBoxDelegate.update();
    });
    
});


/* ======================== QR Code et Fetch des objets======================== */

app.controller("SearchCtrl",function($scope,$cordovaBarcodeScanner,GetJSON){
    
  //  GetJSON.getdata("objets/").then(function(d) {
   //     $scope.objets = d;
   // });

    $scope.validSearch=function(param,param2,param3){
      GetJSON.getdata("objets/recherche?nom="+param+"&amim="+param2+"&amax="+param3).then(function(d) {
        $scope.search= d;
      });
    }
    //Filtre siécle
   // $scope.IsVisible = false;
   // $scope.showHide = function () {
  //  $scope.IsVisible = $scope.IsVisible ? false : true;
   // }
   //$("#example_id").ionRangeSlider();
  //Qr code
  $scope.lireCode=function(){
    $cordovaBarcodeScanner.scan().then( function(image_a_scan){
        alert(image_a_scan.text)
    },function(error){
        alert("Erreur !"+error)
    });
  }

 
});

