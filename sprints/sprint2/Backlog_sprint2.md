# Liste des fonctionnalités à implémenter pour le sprint 2

## Paul
### Les monstres sont générés aléatoirement dans le labyrinthe
#### Description :
Les monstres sont ajoutés au labyrinthe après sa génération, à l’aide de la classe `MonsterSpawner`, ils sont de plusieurs types et sont répartis de manière égale et logique dans l’ensemble du labyrinthe.
### Les monstres se déplacent aléatoirement dans le labyrinthe
#### Description :
Les monstres se déplacent de manière aléatoire dans le labyrinthe, sans traverser les murs ou réagir aux interactions. (Approche du joueur, par exemple)
### Les monstres se déplacent intelligemment dans le labyrinthe.
#### Description :
Les monstres se déplacent de manière intelligente dans le labyrinthe, en contournant les murs et les obstacles pour atteindre une position cible (le joueur, par exemple).

## Clément
### Afficher en permanence la barre de vie des entités
#### Description :
Une barre de vie est affichée en permanence au-dessus des entités afin de savoir le nombre de points de vie qu’ils leur restent.
### Afficher l’écran de fin de partie lorsque le joueur n’a plus de points de vie (100 pts de base)
#### Description :
Une fois que la barre de vie du joueur tombe à 0, un écran de fin, indiquant que le joueur a perdu, apparaît à l’écran. Cet écran comporte un récapitulatif des scores du joueur pendant sa partie.  

### Le joueur peut attaquer les monstres
#### Description :
Le joueur attaque les monstres avec son arme , il peut attaquer à une certaine portée donner par l’arme, l’arme de base est une paire de poings

## Maxime
### En mode libre, les cases sont générées aléatoirement.
#### Description :
Corriger les bugs de génération de labyrinthe (Salles inaccessibles, Labyrinthes impossibles à finir, etc.)
### Le labyrinthe contient des cases spéciales : pièges, point de départ, point d’arrivée, piques 
- En mode libre, les cases sont générées aléatoirement.
- Les cases d'eau sont placées dans les couloirs entre les salles.
- Les cases pièges sont placées dans les salles.
- La case début et la case Fin sont placées une seule fois dans le labyrinthe, de façon éloignée l’une de l’autre.


## Antonin
### Quand un joueur passe sur une case, un effet lui est appliqué.
#### Description :
- **eau** : ralentit sa vitesse lorsqu’il rentre dans l’eau, redevient normal lorsqu'il en sort
- **lave** : inflige des dégâts au joueur pendant qu’il est sur la case
- **piques** : la case est cachée, elle a la texture du sol environnant et se révèle lorsqu’on marche dessus, elle n’inflige des dégâts qu’une seule fois
- **fin** : en mode libre en passe au labyrinthe suivant, en mode campagne ça passe au niveau suivant où on gagne