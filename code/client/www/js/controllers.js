// ======================== Contrôleurs ============================= \\

angular.module('starter.controllers', [])

/* Contrôleur appliqué à la vue abstraite tabs (donc à toutes les
 * autres vues */
.controller('AllCtrl', function($scope, $rootScope, $ionicLoading) {
    
    $scope.$on("$ionicView.beforeLeave", function(event, data){
        // Loading tant que les données ne sont pas récupérées
        $ionicLoading.show();
    });
    
    $scope.$on("$ionicView.enter", function(event, data) {
        $ionicLoading.hide(); // Fin du loading
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
app.controller("ObjetCtrl", function($scope, $stateParams, GetJSON, $ionicSlideBoxDelegate, Social) {
    
    // Les loaders
    $scope.spinnerlike = false;
    $scope.spinnerstar = false;
    
    GetJSON.getdata("objets/" + $stateParams.id).then(function(d) {
        $scope.objet = d;
        
        // Update des slides pour avoir la bonne taille ; cf 
        // http://ionicframework.com/docs/api/service/%24ionicSlideBoxDelegate/
        $ionicSlideBoxDelegate.update();
    });
    
    // Récupération des likes
    Social.get($stateParams.id).then(function(d) {
        
        // (cf. http://stackoverflow.com/questions/263965/how-can-i-convert-a-string-to-boolean-in-javascript)
        $scope.like = (d.like === 'true');
        
        $scope.nb_like = d.nb;
        
        $scope.note = d.note;
        $scope.star = (d.vote === 'true');
        $scope.nb_vote = d.nbv;
        $scope.moy_vote = d.moy;
    });
    
    $scope.setLike = function() {
        
        // Le spinner remplace l'icône
        $scope.spinnerlike = true;
        
        Social.setlike($stateParams.id).then(function(d) {
            
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
    
    $scope.setStars = function(note) {
        
        $scope.spinnerstar = true;
        
        Social.setstars($stateParams.id, note).then(function(d) {
            
            // Update de la moyenne
            $scope.moy_vote = d.moy;
            
            console.log(d);
            
            if (d.msg == "Ajout") {
                $scope.star = true;
                $scope.nb_vote = $scope.nb_vote + 1;
                $scope.note = note;
            }
            else if (d.msg == "Suppression") {
                $scope.star = false;
                $scope.nb_vote = $scope.nb_vote - 1;
                $scope.note = 0;
            }
            // else (default) : on ne touche à rien
            
            $scope.spinnerstar = false;
            
        }, function(e) { // Erreur
            $scope.spinnerstar = false;
        });
    }
    
});

/* ====================== QR Code et recherche ====================== */
// tab-search.html

app.controller("SearchCtrl",function(useYear,$scope,$cordovaBarcodeScanner,GetJSON, $window, Delay){
    
    //Permet d'obtenir intervalle la date min et max des objets dynamiquement 
    var annees=useYear;
    
    //~ console.log(annees);
    $scope.yearmin =annees.amin;
    $scope.yearmax =annees.amax;
    $scope.from =annees.amin;
    $scope.to =annees.amax;
    $scope.nom = "";
    $scope.type = '';

    $scope.spinner = true; // Cache le loader

    $scope.validSearch=function(param,param2,param3, type){
        
        // type : 'date' <= lancé en déplaçant les curseurs
        //        'word' <= lancé en écrivant des caractères
        
        // Délai permettant de ne pas faire de requête à chaque keyUp
        Delay(function(){
            
            // Lancement de la recherche si + de 2 caractères, ou lancé
            // depuis le range de la date
            if ((type == "date" || param.length > 2) && $scope.type != '') {
                
                $scope.spinner = false; // Affichage du 'loading'
                
                GetJSON.getdata("objets/recherche?nom="+param+"&amin="+param2+"&amax="+param3).then(function(d) {
                    $scope.search= d;
                    $scope.spinner = true;
                    $yearminsearch=param2;
                    $yearmaxsearch=param3;
                });
            }
            
            // Au départ, $scope.type est = '' ; ça permet d'ignorer le
            // premier lancement initié par l'attribution de from & to
            $scope.type = type;
        }, 400 );
    }

    //Qr code (redirection ou message d'erreurs)
    $scope.lireCode=function(){
        
        $cordovaBarcodeScanner.scan().then(function(barcodeData) {
            
            // http://stackoverflow.com/questions/8954637/jquery-regex-validation-for-letters-and-numbers-onlyu
            var BLIDRegExpression = /^[a-zA-Z0-9]{24}$/;

            if (!BLIDRegExpression.test(barcodeData.text)) {
                
                if (release == "windows") {
                    // Pop-up pour Windows Phone :
                    // http://stackoverflow.com/questions/30215520/visual-studio-2015-rc-cordova-app-windows-phone-universal-alert-undefined
                    (new Windows.UI.Popups.MessageDialog("Objet non reconnu", "Erreur")).showAsync().done();
                } else {
                    alert('Objet non reconnu');
                }
                
                return false;
            }
            
            // Test si l'objet existe
            GetJSON.getdata("objets/test/" + barcodeData.text).then(function(d) {
                if ((typeof d.existence !== 'undefined') && d.existence == "true") {
                    $window.location.href = '#/tab/search/' + barcodeData.text;
                }
                else {
                    if (release == "windows") {
                        (new Windows.UI.Popups.MessageDialog("Objet non trouvé", "Erreur")).showAsync().done();
                    } else {
                        alert('Objet non trouvé');
                    }
                }
            }, function(e) { // Erreur d'existence                
                if (release == "windows") {
                    (new Windows.UI.Popups.MessageDialog("Objet non trouvé", "Erreur")).showAsync().done();
                } else {
                    alert('Objet non trouvé');
                }
            });
        }, function(error) { // Erreur avec .scan()
            if (release == "windows") {
                (new Windows.UI.Popups.MessageDialog(error, "Erreur")).showAsync().done();
            } else {
                alert("Error : " + error);
            }
        });
    }

});
