# Application Hydratation

Application Android qui permet de suivre sa consommation d'eau au cours de la journée.

## Description

L'utilisateur définit un objectif quotidien (en millilitres) et ajoute ses consommations d'eau au fil de la journée. L'application affiche la progression vers l'objectif et garde un historique de chaque ajout.

## Fonctionnalités

- Ajouter de l'eau bue (250 ml par clic)
- Voir sa progression vers l'objectif du jour (barre de progression)
- Consulter l'historique des consommations, avec l'heure de chaque ajout
- Supprimer un enregistrement de l'historique
- Réinitialiser toute la journée (avec confirmation)

## Technologies

- Kotlin
- Jetpack Compose pour l'interface
- Room pour stocker les données localement sur l'appareil

## Structure du projet

```
app/src/main/java/com/example/applicationhydratation/
├── MainActivity.kt        Écran principal
└── data/
    ├── WaterRecord.kt      Une consommation d'eau (montant, heure)
    ├── WaterDao.kt         Requêtes vers la base de données
    ├── WaterDatabase.kt    Configuration de la base de données
    └── WaterRepository.kt  Lien entre la base de données et l'écran
```

## Installation

1. Ouvrir le projet dans Android Studio
2. Synchroniser Gradle (bouton "Sync Now")
3. Lancer l'application sur un émulateur ou un téléphone