# Liste des fonctionnalités à implémenter pour le sprint 4

## Paul
### Le jeu contient un mode multijoueur
#### Description
- Le menu principal contient un bouton "Multijoueur" qui permet d'ouvrir un meun multi-joueur.
- Ce menu contient un bouton "Héberger" qui permet d'héberger une partie.
- Ce menu contient un bouton "Rejoindre" qui permet de rejoindre une partie.
- Le menu héberger contient des informations sur l'adresse et le port de l'hebergeur, et un bouton "Démarrer" qui permet de lancer le serveur et afficher un bouton "Jouer".
- Le menu rejoindre contient un champ pour entrer l'adresse de l'hébergeur, un champ pour entrer le port de l'hébergeur, et un bouton "Rejoindre" qui permet de rejoindre la partie.
- Le menu héberger et rejoindre contiennent un bouton "Retour" qui permet de revenir au menu principal.
- Quand tous les joueurs sont prêts, le bouton "Jouer" peut être cliqué pour lancer la partie.
- Toutes les fonctionnalités du jeu sont disponibles en multijoueur.
    - Les joueurs peuvent se déplacer dans le labyrinthe
    - Les joueurs peuvent attaquer les monstres
    - Les monstres peuvent attaquer les joueurs
    - Les monstres peuvent se déplacer dans le labyrinthe
    - Les joueurs peuvent ramasser des potions
    - Les joueurs peuvent ramasser des armes
    - Les joueurs peuvent changer de labyrinthe (si un seul joueur change, tout le monde change)
    - Les joueurs peuvent perdre la partie (si tout le monde est mort)

## Antonin
### Le joueur a une animations d'attaque
#### Description
Au moment d'attaquer, le joueur a une animation d'attaque qui se joue.

### Des armes distantes sont implémentées
#### Description
Les armes distantes de plusieurs types sont ajoutées au jeu, avec des comportements différents:
- Arc: Tire des flèches qui infligent des dégâts à distance
    Les flèches sont des projectiles qui se déplacent en ligne droite jusqu'à ce qu'elles touchent un mur ou un monstre. Elles sont alors détruites et infligent des dégâts au monstre si touché.
- Lance: Lance l'objet qui inflige des dégâts à distance
    L'objet est un projectile qui se déplace en ligne droite jusqu'à un maximum de 5 blocs, ou jusqu'a ce qu'il touche un mur ou un monstre. Il reste alors sur place et peut être ramassé par le joueur. Si pendant son déplacement il touche un monstre, il inflige des dégâts au monstre.
- Bombe: le joueur peut poser une bombe au sol
    La bombe est un objet qui reste sur place et qui explose au bout de 5 secondes. L'explosion inflige des dégâts à tous les monstres et au joueur dans un rayon de 3 blocs,
    s'ils ne sont pas cachés derrière un mur. L'objet est détruit après l'explosion.

### Des armes de mêlée sont implémentées
#### Description
Les armes de mélée de plusieurs types sont ajoutées au jeu, avec des comportements différents:
- Epée: Inflige des dégâts à plusieurs monstres à proximité
    L'épée est une arme de mélée qui inflige des dégâts moyens (20) à tous les monstres dans un rayon de 1 bloc autour du joueur, si ces derniers sont dans son champ de vision.
- Hache: Inflige des dégâts à un monstre à proximité
    La hache est une arme de mélée qui inflige des dégâts forts (30) à un monstre dans un rayon de 1 bloc autour du joueur, si ce dernier est dans son champ de vision.
- Nounours: Inflige des dégâts à un monstre à proximité
    Le nounours est une arme de mélée qui inflige des dégâts faibles (10) à un monstre dans un rayon de 1 bloc autour du joueur, si ce dernier est dans son champ de vision.

### Les armes ont du son
#### Description
- Les armes de mélée font un bruit propres à elles lorsqu'elles sont utilisées
- Les armes distantes font un bruit propres à elles lorsqu'elles sont utilisées

## Clément
### Ajouter des sons au jeu
#### Description
- Le joueur fait du bruit en se déplaçant
- Le joueur fait du bruit lorsqu'il se fait attaquer
- Le joueur fait du bruit lorsqu'il attaque
- Le joueur fait du bruit lorsqu'il meurt
- Les monstres font du bruit en se déplaçant
- Les monstres font du bruit lorsqu'ils se font attaquer
- Les monstres font du bruit lorsqu'ils attaquent
- Les monstres font du bruit lorsqu'ils meurent
- Les potions font un bruit lorsqu'elles sont ramassées


## Maxime
### La barre de vie du joueur se confond trop facilement avec celles des monstres
#### Description
- la barre de vie du joueur devrait être d'une autre couleur que celle des monstres
    Vert pour le joueur, rouge pour les monstres

### Les armes apparaissent aléatoirement dans le labyrinthe
#### Description
- Les différetes armes du jeu sont disponibles dans le labyrinthe a des endroits aléatoires
    Elles apparaissent en fonction de leurs rareté et la disposition du labyrinthe

### Les fantomes apparaissent aléatoirement dans le labyrinthe
#### Description
- Les fantomes apparaissent a des endroits aléatoires dans le labyrinthe
    Ils apparaissent en fonction de leurs rareté et la disposition du labyrinthe
    
### Ajuster la difficulté du jeu
#### Description
- Le nombre et la disposition des pièges dans les labyrinthes est trop élevé
    Il faut réduire le nombre de pièges et ajuster leur disposition pour que le jeu soit plus facile et agréable.
