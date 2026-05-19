# TP 11 : Localisation GPS et Tracking Mobile vers Serveur distant

Ce projet consiste à développer une application Android capable de récupérer les coordonnées géographiques (latitude et longitude) d'un smartphone et de les envoyer en temps réel vers un serveur distant (PHP/MySQL) pour stockage.

## 🚀 Objectifs Pédagogiques
- Récupérer la position GPS d'un appareil Android.
- Gérer les permissions Android (Localisation et État du téléphone).
- Envoyer des données via des requêtes HTTP POST avec la bibliothèque **Volley**.
- Structurer un backend PHP orienté objet avec une base de données MySQL.

## 🛠️ Architecture du Système

### 1. Partie Serveur (Fedora / XAMPP)
- **Base de données :** MySQL (Base `localisation`, table `position`).
- **Structure PHP :**
    - `connexion/DbManager.php` : Gère la liaison PDO.
    - `service/GeoService.php` : Logique d'insertion des données.
    - `CreatePosition.php` : Point d'entrée pour les requêtes POST du mobile.

### 2. Partie Mobile (Android Studio)
- **MainActivity.java** : Gère le `LocationManager` et l'envoi Volley.
- **AndroidManifest.xml** : Déclare les permissions `ACCESS_FINE_LOCATION`, `INTERNET` et `READ_PHONE_STATE`.
- **Layout** : Interface simple affichant les coordonnées, l'ID de l'appareil et le statut de l'envoi.

## 📋 Configuration et Installation

### Serveur MySQL
Exécuter le script suivant dans phpMyAdmin :
```sql
CREATE DATABASE localisation;
USE localisation;
CREATE TABLE position (
    id INT AUTO_INCREMENT PRIMARY KEY,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    date_position DATETIME NOT NULL,
    imei VARCHAR(50) NOT NULL
);
```

### Serveur PHP (XAMPP sur Fedora)
1. Placer le dossier du backend dans `/opt/lampp/htdocs/localisation/`.
2. S'assurer que les permissions sont correctes : `sudo chmod -R 755 /opt/lampp/htdocs/localisation`.
3. Désactiver temporairement SELinux si nécessaire : `sudo setenforce 0`.

### Application Android
1. Modifier l'adresse IP dans `MainActivity.java` :
   ```java
   private static final String ENDPOINT_URL = "http://192.168.1.23/localisation/CreatePosition.php";
   ```
2. Compiler et lancer l'application sur un émulateur ou un smartphone réel.

## 📱 Démonstration Vidéo
https://github.com/user-attachments/assets/530c419e-43a5-435f-b8c9-d3311a3f063e

## ✅ Résultats Attendus
- [x] Détection automatique de la position GPS.
- [x] Affichage en temps réel sur l'interface mobile.
- [x] Transmission réussie au serveur via Volley.
- [x] Insertion automatique dans la base de données MySQL.
