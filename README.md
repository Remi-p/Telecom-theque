Telecom-theque
==============

Application de parcours virtuel du Télécom'thèque

-------------------------------------------------

# Utilisation

## Partie client

### Configuration

Pour développer la partie client, se placer dans `code/client`, et utiliser la commande :

```
npm install
```

qui, grâce au fichier `package.json`, installera les dépendances requises.

### Lancement d'Ionic

Se placer dans Telecom-theque, et exécuter la commande suivante (ionic étant installer en local) :

```
../node_modules/ionic/bin/./ionic serve
```

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

* ...
