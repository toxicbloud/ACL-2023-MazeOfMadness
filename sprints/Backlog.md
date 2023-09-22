# Liste exaustive des fonctionnalites

> Priorités : 1 (haute), 2 (moyenne), 3 (basse)

## Labyrinthe

- Le labyrinthe est généré à partir d'un fichier texte ``(1)``
- Le labyrinthe est généré aléatoirement `(2)`
- Le labyrinthe est affiché à l'écran en vue isométrique `(1)`
- Le labyrinthe contient des cases spéciales: pièges, eau, point de départ, point d'arrivée, etc. `(2)`
- Le labyrinthe peut être créé dans un éditeur de niveau `(3)`

## Joueur

- Le joueur se déplace dans le labyrinthe, sans traverser les murs `(1)`
- Le joueur a plusieurs points de vie: 100 points de vie (affichés en permanence) `(2)`
- Le joueur peut attaquer les monstres `(1)`
- Le joueur peut passer au labyrinthe suivant s'il a atteint le point d'arrivée `(1)`
- Le joueur a fini la partie lorsqu'il a atteint le point d'arrivée du dernier labyrinthe (écran de réussite) `(2)`
- Le joueur perd la partie s'il n'a plus de points de vie (écran de fin de partie) `(1)`
- Le joueur peut ramasser des potions: vie, force, vitesse (effets directs) `(3)`

## Monstres

- Les monstres sont généres aléatoirement dans le labyrinthe: `(1)`
    - Spectres: Traversent les murs mais peu de points de vie
    - Zombies: Ne traversent pas les murs et peu de points de vie
    - Bosses: Ne traversent pas les murs et beaucoup de points de vie
- Les monstres se déplacent aléatoirement dans le labyrinthe `(1)`
    - Spectres: vitesse soutenue ( 100% vitesse normale )
    - Zombies: vitesse réduite ( 70% vitesse normale )
    - Bosses: vitesse normale ( 90% vitesse normale )
- Les monstres se déplacent intelligement dans le labyrinthe. Ils cherchent à rejoindre la position du joueur. `(2)`
- Les monstres attaquent le joueur lorsqu'ils sont à proximité `(2)`
    - Spectres: degats normaux (20pts/s)
    - Zombies: peu de degats (10pts/s)
    - Bosses: beaucoup de degats (50pts/s)
- Les spectres ne se déplacent que si le joueur ne les regarde pas `(3)`
- Les bosses peuvent faire des dégâts de zone `(2)`
- Les bosses peuvent faire des dégâts à distance `(2)`
- Les monstres augmentent de difficulté a chaque labyrinthe `(3)`

## Potions

- Les potions sont générées aléatoirement dans le labyrinthe
    - Vie : Restaure une quantité de points de vie au joueur **Usage unique** `(2)`
    - Vitesse : Augmente la vitesse de déplacement du joueur dans le labyrinthe pendant un temps limité **Usage unique** `(3)`
    - Force : Augmente le nombre de points de dégâts du joueur contre les monstres pendant un temps limité **Usage unique** `(3)`

## Score

- Les monstres donnent un gain de score différent: `(3)`
    - Spectres: 40 points
    - Zombies: 20 points
    - Bosses: 200 points
- Le score est affiché en permanence à l'écran: `(3)`
- Le score est calculé en fonction du temps de jeu et des monstres tués (score = pts monstres tués - temps en secondes) `(3)`

## Interface

- Le jeu contient un menu principal: `(1)`
    - Mode campagne
    - Mode libre
    - Quitter
- Le jeu contient un menu campagne: `(1)`
    - Selection du layrinthe
    - Retour
- Le jeu contient un écran de fin: `(1)`
    - Game over (+ score)
    - Retour
- Le jeu contient un écran de réussite: `(1)`
    - Victoire (+ score)
    - Retour
- Le jeu peut être joué au clavier/souris `(1)`
- Le jeu peut être joué à la manette `(3)`
