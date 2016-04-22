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
    
});
