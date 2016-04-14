Télécom'Thèque - Client
=======================

### Configuration

Pour développer la partie client, se placer dans `code/client`, et utiliser la commande :

```
npm install
```

qui, grâce au fichier `package.json`, installera les dépendances requises.

### Lancement d'Ionic

Se placer dans Telecom-theque (répertoire de ce *readme*), et exécuter la commande suivante (Ionic étant installé en local) :

```
../node_modules/ionic/bin/./ionic serve
```

### Plateforme : Windows Phone

[How to make your Ionic Cordova App to Run under Windows Phone 8.1 and Desktop](http://www.badpenguin.org/how-to-make-your-ionic-cordova-app-to-run-under-windows-phone-8-1-and-desktop)
[Windows Platform Guide](https://cordova.apache.org/docs/en/latest/guide/platforms/win8/index.html)

```
../node_modules/cordova/bin/cordova platform add windows
../node_modules/cordova/bin/cordova build --debug windows -- --phone --appx=8.1-phone --buildConfig build.json
```
