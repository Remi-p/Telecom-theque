Télécom'thèque
==============

Application de parcours virtuel du Télécom'thèque

Thibaut G. / Paul M. / Rémi P. // ENSEIRB-MATMECA 2016, T2

-------------------------------------------------

# Utilisation

## Partie client

[code/client/](code/client/)

# Fonctionnalités attendues

L'application permettra de consulter les fiches descriptives des objets du musée (Télécom'thèque).

L'architecture de l'application sera de la forme client-serveur, créées respectivement avec la stack [Ionic](http://ionicframework.com/) et [Jersey](https://jersey.java.net/).

## Fonctionnalités serveur

* Liste des vitrines
* Liste des objets
* Rechercher un objet

## Fonctionnalités client

* Lister les vitrines
* Lister les objets d'une vitrine
* Consulter les détails d'un objet
* Accès direct à un objet via un QR code

# Fonctionnalités supplémentaires

## Fonctionnalités serveur

* Endpoint renvoyant la date minimum & maximum des objets de la BDD (pour la sélection de la période)
* Endpoint renvoyant true ou false en fonction de l'existence d'un objet
* Endpoint gérant le nombre de likes d'un objet
* Endpoint gérant les notes sur un objet

## Fonctionnalités client

* Présentation des images d'un objet avec un slider
* Animations dans l'application
* Recadrage automatique des images
* Loader entre les pages
* Recherche automatiquement lancée
    * Après avoir tappé le texte
    * Après avoir déplacé les curseurs pour la période
* Possibilité de "liker" un objet
* Possibilité de noter un objet
* Indique lorsqu'aucune connexion n'est disponible
* Modification du CSS en fonction de l'orientation (paysage, portrait)
