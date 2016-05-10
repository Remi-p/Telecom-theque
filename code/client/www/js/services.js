/* ==================== Services de l'application =================== */

angular.module('starter.services', [])

// Service de vérification de connectivité
.factory('CheckInternet', function($cordovaNetwork) {
    
    return {
        check: function() {
            
            // Dans le cas d'un navigateur PC on quitte direct
            if (release == "serve") {
                return true;
            }
            
            if ($cordovaNetwork.isOnline()) {
                return true; // Connexion OK
            } else {
                // Connexion non OK
                if (release == "windows") {
                    (new Windows.UI.Popups.MessageDialog("Pas de connexion à Internet.", "Erreur réseau")).showAsync().done();
                } else {
                    alert('Pas de connexion à Internet.');
                }
                return false;
            }
        }
    }
    
})

// Service de récupération de données
.factory('GetJSON', function($http, CheckInternet) {
    
    // Avant toute chose, vérifie la connexion à Internet
    if (CheckInternet.check() == false) {
        return false;
    }
    
    return {
        
        // Création de la fct getdata
        getdata: function(route) {
            
            promise = $http.get(url_api + route).then(function(response) {
                return response.data;
            });
            
            // Retourne une "promesse" ; il faut donc appeler la méthode
            // .then lorsqu'on se sert du service
            return promise;
        }
    };
    
})

// Service gérant les likes/favoris & les notes
.factory('Social', function($http, $cordovaDevice) {
    
    var url = url_api + "objets/";
    
    var uuid;
    
    if (release == "serve") {
        uuid = "Ionic-serve";
    } else {
        uuid = $cordovaDevice.getUUID();
    }
    
    return {
        
        // Fonction de récupération
        get: function(objet) {
            
            promise = $http.get(url + "meta/" + objet + "/" + uuid).then(function(response) {
                return response.data;
            });
            
            return promise;
        },
        
        // Ajout - suppression d'un like
        setlike: function(objet) {
            
            topost = { 'uuid': uuid, 'objet': objet };
            
            promise = $http.post(url + "likes/action/", topost).then(function(response) {
                return response.data;
            });
            
            return promise;
        },
        
        // Ajout - suppression d'une étoile
        setstars: function(objet, note) {
            
            topost = { 'uuid': uuid, 'objet': objet, 'note': note };
            
            promise = $http.post(url + "notes/action/", topost).then(function(response) {
                return response.data;
            });
            
            return promise;
        }
    };
    
})

// Service permettant le lancement d'une fonction après un certain
// délai, suite à plusieurs appels
// http://stackoverflow.com/questions/1909441/jquery-keyup-delay
.factory('Delay', function() {
    var timer = 0;
    return function(callback, ms){
        clearTimeout (timer);
        timer = setTimeout(callback, ms);
    };
});
