# Localisation-d-un-smartphone-et-envoi-des-coordonn-es-vers-un-serveur-distant
Géolocalisation Mobile et Envoi des Données vers un Serveur
📖 Introduction

Dans ce travail pratique, nous avons conçu une application Android permettant de récupérer la localisation GPS d’un smartphone puis de transmettre ces informations vers un serveur distant afin de les enregistrer dans une base de données MySQL.

Le projet combine deux parties principales :

une application mobile Android ;
un serveur web PHP connecté à MySQL.

L’objectif est de simuler un système simple de suivi de position en temps réel.

🎯 Objectifs du TP

À travers ce projet, plusieurs notions importantes ont été étudiées :

utilisation du GPS sous Android ;
gestion des permissions utilisateur ;
communication entre Android et un serveur web ;
utilisation des requêtes HTTP POST ;
stockage des données dans une base MySQL ;
création d’un backend PHP simple.
🧱 Structure Générale du Projet

Le système est divisé en deux parties :

Partie	Description
Application Android	Récupération et envoi des coordonnées GPS
Serveur PHP/MySQL	Réception et stockage des données
📱 Partie Mobile Android
🔹 Fonctionnement

L’application récupère automatiquement :

la latitude ;
la longitude ;
l’identifiant du téléphone.

Ces données sont ensuite envoyées vers le serveur grâce à la bibliothèque Volley.

🔹 Fichiers Principaux
MainActivity.java

Ce fichier contient :

la gestion du GPS ;
la récupération de la position ;
l’envoi HTTP vers le serveur ;
l’affichage des informations à l’écran.
AndroidManifest.xml

Le manifeste contient les permissions nécessaires :

accès Internet ;
accès à la localisation ;
lecture des informations de l’appareil.

Exemple :

<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
💻 Partie Serveur
🔹 Base de Données

Une base MySQL a été créée afin de stocker les coordonnées reçues depuis l’application.

Table utilisée :

position_mobile

Champs enregistrés :

id
latitude
longitude
date_save
device_id
🔹 Backend PHP

Le serveur contient plusieurs fichiers séparés afin de mieux organiser le projet :

Fichier	Rôle
Database.php	Connexion à MySQL
PositionManager.php	Insertion des données
SaveLocation.php	Réception des requêtes POST

Cette séparation rend le code plus propre et plus facile à maintenir.

🛠️ Configuration Réalisée
🔹 Serveur Web

Le backend PHP a été placé dans le dossier htdocs du serveur XAMPP.

Des permissions Linux ont été appliquées afin d’autoriser l’accès aux fichiers du projet.

🔹 Adresse du Serveur

Dans l’application Android, l’adresse IP locale du serveur a été configurée directement dans le code Java :

private static final String SERVER_URL = "http://192.168.1.50/gps/SaveLocation.php";
🔄 Déroulement du Fonctionnement
Étapes exécutées :
lancement de l’application ;
activation du GPS ;
récupération des coordonnées ;
affichage sur l’écran ;
envoi vers le serveur ;
insertion automatique dans MySQL.
📊 Résultats Obtenus

Les tests effectués montrent que :

✅ la position GPS est détectée correctement
✅ les coordonnées s’affichent dans l’application
✅ la connexion avec le serveur fonctionne
✅ les données sont enregistrées dans la base MySQL
✅ l’envoi via Volley est réalisé avec succès

🎨 Améliorations Personnelles

Plusieurs modifications ont été apportées afin de personnaliser le projet :

changement des noms des classes ;
nouvelle organisation des dossiers PHP ;
modification des noms des tables MySQL ;
ajout d’un affichage plus clair dans l’interface ;
adaptation des messages de statut ;
structure différente du code Android.

✅ Conclusion

Ce TP nous a permis de comprendre le fonctionnement de la géolocalisation sous Android ainsi que la communication entre une application mobile et un serveur distant.

Nous avons également appris à utiliser Volley pour les requêtes réseau et à connecter une application Android avec une base de données MySQL à travers un backend PHP.
