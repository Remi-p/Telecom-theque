// ======= Controlleurs ======= \\

angular.module('starter.controllers', [])

/* Contrôleur appliqué à la vue abstraite tabs (donc à toutes les
 * autres vues */
.controller('AllCtrl', function($scope, $rootScope, $ionicLoading) {
    
    $scope.$on("$ionicView.beforeLeave", function(event, data){
        // Loading tant que les données ne sont pas récupérées
        $ionicLoading.show();
        
        //~ console.log((new Date()).getTime() + ": beforeLeave");
    });
    
    $scope.$on("$ionicView.enter", function(event, data) {
        $ionicLoading.hide(); // Fin du loading
        
        //~ console.log((new Date()).getTime() + ": Enter");
    });

})

// tab-home.html
.controller('HomeCtrl', function($scope, $ionicTabsDelegate) {
    
    // Fonction permettant d'aller sur un autre onglet depuis un lien
    // (contenant un appel à la fonction)
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

/* ===================== Fetch des vitrines ========================= */
// tab-vitrines.html
app.controller("VitrinesCtrl", function($scope, GetJSON) {
    
    // On récupère les données pour la vue
    GetJSON.getdata("vitrines/").then(function(d) {
        $scope.vitrines = d;
    });
    
});

/* ================ Fetch des objets pour une vitrine =============== */
// vitrine.html
app.controller("VitrineCtrl", function($scope, $stateParams, GetJSON) {
    
    GetJSON.getdata("vitrines/" + $stateParams.id).then(function(d) {
        $scope.vitrine = d;
    });
    
});

/* ======================== Fetch d'un objet ======================== */
// objet.html
app.controller("ObjetCtrl", function($scope, $stateParams, GetJSON, $ionicSlideBoxDelegate) {
    
    GetJSON.getdata("objets/" + $stateParams.id).then(function(d) {
        $scope.objet = d;
        
        // Update des slides pour avoir la bonne taille ; cf 
        // http://ionicframework.com/docs/api/service/%24ionicSlideBoxDelegate/
        $ionicSlideBoxDelegate.update();
    });
    
});

/* ======================== QR Code et Fetch des objets======================== */
// tab-search.html

// Fonction permettant le lancement d'une fonction après un certain
// délai, suite à plusieurs appels
// http://stackoverflow.com/questions/1909441/jquery-keyup-delay
var delay = (function(){
    var timer = 0;
    return function(callback, ms){
        clearTimeout (timer);
        timer = setTimeout(callback, ms);
    };
})();

app.controller("SearchCtrl",function($scope,$cordovaBarcodeScanner,GetJSON){

    //Permet d'obtenir intervalle la date min et max des objets 

    GetJSON.getdata("objets/dates").then(function(d){
        $scope.dates=d;
    });
   
    $scope.spinner = true; // Cache le loader

    $scope.validSearch=function(param,param2,param3){
        
        // Délai permettant de ne pas faire de requête à chaque keyUp
        delay(function(){
            
            // Lancement de la recherche si + de 2 caractères
            if (param.length > 2) {
            
                $scope.spinner = false; // Affichage du 'loading'
            
                GetJSON.getdata("objets/recherche?nom="+param+"&amim="+param2+"&amax="+param3).then(function(d) {
                    $scope.search= d;
                    $scope.spinner = true;
                });
            }
        }, 400 );
    }
    

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

