# Maze Of Madness
## Description du Jeu
MoM est un jeu vidéo isométrique immersif qui propose aux joueurs une expérience captivante de labyrinthes complexes, d'exploration et de survie. Le jeu offre deux modes de jeu distincts :

1. **Mode Campagne :** Les joueurs se lancent dans une aventure épique à travers une série de labyrinthes prédéfinis.

2. **Mode Libre :** Pour ceux qui recherchent un défi sans fin, le mode libre génère des labyrinthes aléatoires à explorer à l'infini. Chaque labyrinthe est unique, offrant une expérience de jeu différente à chaque session. Les joueurs peuvent tester leurs compétences de survie.

Il s'adresse donc aux joueurs occasionnels voulant battre la campagne mais aussi aux joueurs souhaitant affronter en boucle des monstres de plus en plus forts.

## Caractéristiques Principales

- **Gameplay Isométrique :** L'utilisation de la perspective isométrique permet de surplomber l'environnement afin d'appréhender le danger.

- **Variété de Monstres avec Comportements Uniques :** Les monstres du jeu ont des comportements distincts. Par exemple, les fantômes peuvent traverser les murs pour surprendre les joueurs qui ne les regardent pas, tandis que les zombies avancent vers le joueur une fois qu'ils l'ont détectés.

- **Éditeur de Niveaux Communautaires :** Les joueurs peuvent créer et partager leurs propres labyrinthes avec la communauté, ajoutant ainsi une dimension de rejouabilité infinie.

- **Collecte d'Objets :** Les joueurs peuvent collecter des objets utiles tels que des potions de santé et de force pour survivre aux attaques des monstres. De plus, ils peuvent découvrir et s'équiper de différentes armes pour se défendre.

- **Prise en Charge de la Manette :** Le jeu est conçu pour être accessible aux joueurs qui préfèrent jouer avec une manette, offrant une expérience de jeu fluide et intuitive mais surtout accessible.

## Objectif du Projet
L'objectif ultime de ce projet est de créer une expérience de jeu isométrique captivante qui défie les joueurs avec des labyrinthes complexes ainsi qu'une variété de monstres. Le jeu vise à offrir une rejouabilité infinie grâce à ses labyrinthes générés aléatoirement et à son éditeur de niveaux communautaires.

## Valeurs Clés
L'exploration, la survie, la créativité communautaire et l'accessibilité sont les valeurs clés de ce jeu. Nous visons à créer une communauté de joueurs passionnés qui partagent leurs propres créations et défient constamment leurs compétences.

# Auteurs

Antonin Rousseau |                                                                             | Paul Loisil |                                                         |
---------------- | --------------------------------------------------------------------------- | ----------- | ------------------------------------------------------- |
Site web         | [antoninrousseau.fr](https://antoninrousseau.fr)                            | Site web    | [furwaz.fr](https://furwaz.fr)                          |
GitHub           | [@toxicbloud](https://github.com/toxicbloud)                                | GitHub      | [@furwaz](https://github.com/furwaz)                    |
LinkedIn         | [Antonin Rousseau](https://www.linkedin.com/in/antonin-rousseau-571280159/) | LinkedIn    | [Paul LOISIL](https://www.linkedin.com/in/loisil-paul/) |

| Maxime Marcelin |                                                                           | Clément Joly |     |
| --------------- | ------------------------------------------------------------------------- | ------------ | --- |
| Site web        |                                                                           | Site web     |     |
| GitHub          | [@Maximelego](https://github.com/Maximelego)                              | GitHub       |     |
| LinkedIn        | [Maxime Marcelin](https://www.linkedin.com/in/maxime-marcelin-a7b53225a/) | LinkedIn     |     |

# Installation

## Prérequis
<div align="center">

![JDK 17](https://img.shields.io/badge/JDK-17-red.svg) &nbsp;&nbsp;&nbsp;&nbsp; ![Apache Maven](https://badgen.net/badge/icon/Maven/red?icon=maven&label) &nbsp;&nbsp;&nbsp;&nbsp; ![Terminal](https://badgen.net/badge/icon/terminal?icon=terminal&label)

</div>

## Étapes d'Installation

1. **Cloner le Projet (Si vous utilisez Git)**

   ```bash
   git clone https://github.com/toxicbloud/ACL-2023-MazeOfMadness
    ```
2. **Déplacer vous dans le dossier du projet**
    > le projet Maze Of Madness est abrégé en mom
   ```bash
   cd ACL-2023-MazeOfMadness/mom
   ```
3. **Compiler le projet**
   ```bash
   mvn clean package
   ```
   > Cela téléchargera les dépendances, compilera le code source et générera des fichier JAR dans le répertoire target du projet.
4. **Lancer le jeu**
   ```bash
    java -jar target/<nom_du_jar>.jar
    ```
    > Le nom du jar est normalement MazeOfMadness-jar-with-dependencies.jar