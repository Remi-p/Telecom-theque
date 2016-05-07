// Ionic Starter App
// Thibaut G. / Paul M. / Rémi P. / ENSEIRB-MATMECA 2016, T2

// Variable permettant de s'adapter aux devices
//  "serve" = lorsque `ionic serve` est utilisée
//  "android" = build pour appareils Android
//  "windows" = build pour windows phone
release = "serve";

var app = angular.module('starter', ['ionic', 'starter.controllers', 'starter.services','ngCordova']);

app.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
    // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
    // for form inputs)
    if (window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
      cordova.plugins.Keyboard.disableScroll(true);

    }
    if (window.StatusBar) {
      // org.apache.cordova.statusbar required
      StatusBar.styleDefault();
    }
  });
});

// Remarque : le reste du Javascript est réparti dans les autres fichiers
