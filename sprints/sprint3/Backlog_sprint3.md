# Liste des fonctionnalités à implémenter pour le sprint 2

## Paul
### Resolution du bug concernant le blocage du joueur.
#### Description :
Au changement d'un niveau à l'autre, le joueur se retrouve "bloqué" dans le
dernier mouvement qu'il a réalisé au niveau précédent.
Ce bug doit être corrigé de manière à pouvoir enchainer les niveaux du jeu sans encombres.

### Parser des niveaux : Ajouter la sauvegarde des Monstres et des Potions.
#### Description :
L'algorithme chargé de récupérer les données d'un niveau du jeu à partir d'un fichier
ne supporte que les blocs du niveau. Les monstres et futures potions du jeu ne peuvent
donc pas encore être chargées à partir d'un fichier.
Cette fonctionnalité consiste à améliorer le parser de niveaux pour intégrer le support
des monstres et potions dans le chargement des niveaux.

### Editeur de Niveaux
#### Description :
Il n'y a pour le moment aucun moyen de créer un niveau pour les utilisateurs.
Les niveaux existants du jeu ont été faits à la main, en écrivant le fichier de
sauvegarde directement.
le but de cette fonctionnalité est de concevoir, designer, et programmer un éditeur
de niveaux. Permettant de créer, éditer, supprimer des niveaux plus simplement qu'en
modifiant un fichier de sauvegarde.


## Antonin
### Les monstres augmentent le score du joueur.
#### Description : 
- Les spectres donnent 40 points de score.
- Les zombies ajoutent 20 points de score.
- Les Boss ajoutent 200 points de score.

### Le score est affiché en permanence à l'écran.
#### Description : 
Le score est affiché en haut a gauche de l'écran.

### Ajouter les boutons de retour en arrière des menus et sur la scène du jeu.
#### Description : 
On ne peut pas revenir au menu principal lorsqu'on choisit un niveau ou lorsqu'on joue, il faut ajouter
un bouton retour sur le menu de selection de la campagne et un menu pause dans la scene de jeu accessible
via la touche Echap.

## Clément
### Les monstres attaquent le joueur lorsqu'ils sont à portée.
#### Description : 
- Les monstres infligent des dégâts au joueur lorsqu'ils sont à portée d'attaque.

## Maxime
### Corriger le problème avec l'apparition des échelles (Issue #69)
#### Description : 
- Les échelles sont enfoncées dans le sol et doivent être réhaussée d'un niveau.

### Les potions sont générées aléatoirement dans le labyrinthe.
#### Description : 
 - Des potions apparaîteront dans le labyrinthe de manière aléatoire.
Le détail des potions est disponible dans la fonctionnalité suivante.

### Le joueur peut ramasser les potions dans le labyrinthe.
#### Description : 
- Les potions sont à usage unique.
- Plusieurs types de potions sont disponibles : 
 - Santé : Les potions de santé restaurent une quantité de points de vie au joueur.  
 - Vitesse : Les potions de vitesse augmentent la vitesse de déplacement du joueur.
 - Force : Les potions de vitesse augmentent les dégats infligés aux monstres par le joueur. 
- A l'exception de la potion de santé, toutes les potions ont des effets subsistant pendant le niveau en cours.

### Ajouter des instructions sur les contrôles dans le README
#### Description : 
- Les contrôles des déplacements du joueur doivent être affichés dans le README principal du zprojet. 
