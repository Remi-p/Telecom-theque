/**
 * @author Thibaut Gourdel <tgourdel@enseirb-matmeca.fr>
 * @name HTTPrequest
 * @description
 * Service which handles HTTP requests
**/

/* RESTHeart configuration variables 
================================================= */
var RESTHEART_URL = "http://188.166.153.49";
var DB = "db";
var COLL = "videos";
var LOGIN = "admin";
var PASSWORD = "changeit";

/* httpCall service
================================================== */
app.service("httpCall", [ "$http", "$base64",
    function($http, $base64) {
 
        //Getter pour la variable videos
        this.get = function(filter) {

            // Authentification for HTTP request on RESTHeart server
            // $base64 ressource is mandatory to handle authentification in HTTP headers
            $http.defaults.headers.common["Authorization"] 
                = 'Basic ' + $base64.encode(LOGIN + ":" + PASSWORD);

            return $http({
              method: 'GET',
              url: RESTHEART_URL + '/' + DB + '/' + COLL + '/' + filter, // Database collection URL
            }).then(
                // The HTTP call succeed -> code : 200
                function successCallback(response) {
                    return response.data;
                });
        };
    }
]);
