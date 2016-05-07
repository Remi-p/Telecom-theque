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
app.controller("ObjetCtrl", function($scope, $stateParams, GetJSON, $ionicSlideBoxDelegate, Like) {
    
    $scope.spinnerlike = false;
    
    GetJSON.getdata("objets/" + $stateParams.id).then(function(d) {
        $scope.objet = d;
        
        // Update des slides pour avoir la bonne taille ; cf 
        // http://ionicframework.com/docs/api/service/%24ionicSlideBoxDelegate/
        $ionicSlideBoxDelegate.update();
    });
    
    Like.getlikes($stateParams.id).then(function(d) {
        // (cf. http://stackoverflow.com/questions/263965/how-can-i-convert-a-string-to-boolean-in-javascript)
        $scope.like = (d.like === 'true');
        $scope.nb_like = d.nb;
    });
    
    $scope.setLike = function() {
        
        // Le spinner remplace l'icône
        $scope.spinnerlike = true;
        
        Like.setlike($stateParams.id).then(function(d) {
            
            if (d == "Ajout") {
                $scope.like = true;
                $scope.nb_like = $scope.nb_like + 1;
            }
            else if (d == "Suppression") {
                $scope.like = false;
                $scope.nb_like = $scope.nb_like - 1;
            }
            // else (default) : on ne touche à rien
            
            $scope.spinnerlike = false;
            
        }, function(e) { // Erreur
            $scope.spinnerlike = false;
        });
    };
    
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

//http://codepen.io/shaoner/pen/ZWoYgE?editors=1010
//Fonction utilisée par le curseur
function MultiRangeDirective($compile) {
  
  var directive = {
    restrict: 'E',
    scope: {

      ngModelMin: '=',
      ngModelMax: '=',
      ngMin: '=',
      ngMax: '=',
      ngStep: '='
    },
    link: link
  };

  return directive;

  ////////////////////

  function link($scope, $element, $attrs) {
    var min, max, step, $inputMin = angular.element('<input type="range">'),
      $inputMax;
    
    if (typeof $scope.ngMin != 'undefined') {
      min = $scope.ngMin;
      $inputMin.attr('min', min);
    } else {
      min = 0;
    }
    if (typeof $scope.ngMax != 'undefined') {
      max = $scope.ngMax;
      $inputMin.attr('max', max);
    } else {
      max = 2016;
    }
    if (typeof $scope.ngStep != 'undefined') {
      $inputMin.attr('step', $scope.ngStep);
    }else{
      step=1;
    }
    $inputMax = $inputMin.clone();
    $inputMin.attr('ng-model', 'ngModelMin');
    $inputMax.attr('ng-model', 'ngModelMax');
    $compile($inputMin)($scope);
    $compile($inputMax)($scope);
    $element.append($inputMin).append($inputMax);
    $scope.ngModelMin = $scope.ngModelMin || min;
    $scope.ngModelMax = $scope.ngModelMax || max;

    $scope.$watch('ngModelMin', function(newVal, oldVal) {
      if (newVal > $scope.ngModelMax) {
        $scope.ngModelMin = oldVal;
      }
    });

    $scope.$watch('ngModelMax', function(newVal, oldVal) {
      if (newVal < $scope.ngModelMin) {
        $scope.ngModelMax = oldVal;

      }
    });
  }
}



app.controller("SearchCtrl",function(useYear,$scope,$cordovaBarcodeScanner,GetJSON, $window){
   //Permet d'obtenir intervalle la date min et max des objets dynamiquement 

   var annees=useYear;
   console.log(annees);
   $scope.yearmin =annees.amin;
   $scope.yearmax =annees.amax;
   $scope.from =annees.amin;
   $scope.to =annees.amax;


    $scope.spinner = true; // Cache le loader

     // afficher rechercher par nom 
    $scope.IsVisibleName = false;

    $scope.showHideName = function () {
      $scope.IsVisibleName = $scope.IsVisibleName ? false : true;
      $scope.IsVisibleYear=false;
    }

    $scope.validSearch=function(param,param2,param3){
        
        // Délai permettant de ne pas faire de requête à chaque keyUp
        delay(function(){
            
            // Lancement de la recherche si + de 2 caractères
            if (param.length > 2) {
            
                $scope.spinner = false; // Affichage du 'loading'
                
                GetJSON.getdata("objets/recherche?nom="+param+"&amin="+param2+"&amax="+param3).then(function(d) {
                    $scope.search= d;
                    $scope.spinner = true;
                    $yearminsearch=param2;
                    $yearmaxsearch=param3;
                });
            }
        }, 400 );
    }


    //afficher rechercher par année 
    $scope.IsVisibleYear = false;
    $scope.showHideYear = function () {
      $scope.IsVisibleYear = $scope.IsVisibleYear ? false : true;
      $scope.IsVisibleName=false;
    }
    //chercher par année 
    $scope.searchByYear=function(min,max){
      GetJSON.getdata("objets/recherche?nom=&amin="+min+"&amax="+max).then(function(d) {
        $scope.search= d;
      });
    }

    //Qr code
    $scope.lireCode=function(){
        //alert("coucou")
        $cordovaBarcodeScanner.scan().then(function(barcodeData) {
            
            // http://stackoverflow.com/questions/8954637/jquery-regex-validation-for-letters-and-numbers-onlyu
            var BLIDRegExpression = /^[a-zA-Z0-9]{24}$/;

            if (!BLIDRegExpression.test(barcodeData.text)) {
                
                if (release == "windows") {
                    // Pop-up pour Windows Phone :
                    // http://stackoverflow.com/questions/30215520/visual-studio-2015-rc-cordova-app-windows-phone-universal-alert-undefined
                    (new Windows.UI.Popups.MessageDialog("Erreur", "Objet non reconnu")).showAsync().done();
                } else {
                    alert('Objet non reconnu');
                }
                
                return false;
            }
            
            // Test si l'objet existe
            GetJSON.getdata("objets/test/" + barcodeData.text).then(function(d) {
                if (d == "true") {
                    $window.location.href = '#/tab/search/' + barcodeData.text;
                }
                else {
                    if (release == "windows") {
                        (new Windows.UI.Popups.MessageDialog("Erreur", "Objet non trouvé")).showAsync().done();
                    } else {
                        alert('Objet non trouvé');
                    }
                }
            });
        }, function(error) {
            if (release == "windows") {
                (new Windows.UI.Popups.MessageDialog("Erreur", error)).showAsync().done();
            } else {
                alert("Error : " + error);
            }
        });
    }

});

app.directive('uiMultiRange', MultiRangeDirective);
