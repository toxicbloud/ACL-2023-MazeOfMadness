# 

## Fonctionnalites

### Labyrinthe

- Le labyrinthe est généré à partir d'un fichier texte
- Le labyrinthe est généré aléatoirement
- La labyrinth est affiché à l'écran en vue isométrique
- Le labyrinthe contient des cases spéciales: pièges, eau, point de départ, point d'arrivée, etc.

### Joueur

- Le joueur se déplace dans le labyrinthe, sans traverser les murs
- Le joueur a plusieurs points de vie: 100 points de vie (affichés en permanence)
- Le joueur peut attaquer les monstres
- Le joueur peut passer au labyrinth suivant s'il a atteint le point d'arrivée
- Le joueur a finir la partie lorsqu'il a atteint le point d'arrivée du dernier labyrinthe (écran de réussite)
- Le joueur perd la partie s'il n'a plus de points de vie (écran de fin de partie)
- Le joueur peut ramasser des potions: vie, force, vitesse (effets directs)

### Monstres

- Les monstres sont généres aléatoirement dans le labyrinthe:
    - Spectres: Traversent les murs mais peu de vie
    - Zombies: Ne traversent pas les murs et peu de vie
    - Bosses: Ne traversent pas les murs et beaucoup de vie
- Les monstres se déplacent aléatoirement dans le labyrinthe
    - Spectres: vitesse soutenue ( 100% vitesse normale )
    - Zombies: vitesse réduite ( 70% vitesse normale )
    - Bosses: vitesse normale ( 90% vitesse normale )
- Les monstres se déplacent intelligement dans le labyrinthe
- Les monstres attaquent le joueur lorsqu'ils sont à proximité
    - Spectres: degats normaux (20pts)
    - Zombies: peu de degats (10pts)
    - Bosses: beaucoup de degats (50pts)
- Les spectres se déplacent que si le joueur ne les regarde pas
- Les bosses peuvent faire des degats de zone
- Les bosses peuvent faire des degats à distance

### Potions

- Les potions sont générées aléatoirement dans le labyrinthe
    - Vie: 

### Score

- Les monstres ont un gain de score différent:
    - Spectres: 40 points
    - Zombies: 20 points
    - Bosses: 200 points
- Le score est affiché en permanence à l'écran:
- Le score est calculé en fonction du temps de jeu et des monstres tués (score = pts monstres tués - temps en secondes)

### Interface

- Le jeu contient un menu principal:
    - Mode campagne
    - Mode libre
    - Quitter
- Le jeu contient un menu campagne:
    - Selection du layrinthe
    - Retour
- Le jeu contient un écran de fin:
    - Game over (+ score)
    - Retour
- Le jeu contient un écran de réussite:
    - Victoire (+ score)
    - Retour
- Le jeu peut être joué au clavier/souris
- Le jeu peut être joué à la manette
