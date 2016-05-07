/* ========== Services de l'application ======== */

angular.module('starter.services', [])

// Service de récupération de données
.factory('GetJSON', function($http) {
    
    var url = "http://tgourdel.rtrinity.enseirb-matmeca.fr/api/";
    
    return {
        
        // Création de la fct getdata
        getdata: function(route) {
            
            promise = $http.get(url + route).then(function(response) {
                return response.data;
            });
            
            // Retourne une "promesse" ; il faut donc appeler la méthode
            // .then lorsqu'on se sert du service
            return promise;
        }
    };
    
})

// Service gérant les likes/favoris
.factory('Like', function($http, $cordovaDevice) {
    
    var url = "http://tgourdel.rtrinity.enseirb-matmeca.fr/api/objets/likes/";
    
    var uuid;
    
    if (release == "serve") {
        uuid = "Ionic-serve";
    } else {
        uuid = $cordovaDevice.getUUID();
    }
    
    return {
        
        // Fonction de récupération
        getlikes: function(objet) {
            
            promise = $http.get(url + objet + "/" + uuid).then(function(response) {
                return response.data;
            });
            
            return promise;
        },
        
        setlike: function(objet) {
            
            topost = { 'uuid': uuid, 'objet': objet };
            
            promise = $http.post(url + "add/", topost).then(function(response) {
                return response.data;
            });
            
            return promise;
        }
    };
    
});
