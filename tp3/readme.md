# Marking Menu

## Mode débutant
__Fonctionnement__
* Cliquer sur la page
* Attendre une fraction de seconde que le menu s'affiche
* Déplacer la souris vers le sous-menu voulu
* Attendre qu'un sous-menu apparaisse s'il existe sans déplacer le curseur
* Répéter l'opération jusqu'à atteindre une feuille, et relâcher le clic sur le bouton désiré.

__Test__
* En moyenne le temps pour réaliser une action est de 2 secondes
* Il est possible de se tromper si l'on ne bouge plus le curseur en ayant quitté la zone "centrale" de sélection

## Mode expert
__Fonctionnement__
* Cliquer sur la page
* Dessiner le chemin du menu désirer sans attendre
* Relâcher pour obtenir l'action désirée

__Test__
* Le temps moyen d'une action est de 500 ms
* Il n'est pas toujours facile de retenir les patterns

## Créer son propre menu
* Dans une page html, inclure le script `main.js` ainsi qu'une version récente de JQuery :

```html
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="main.js"></script>
```

* Créer un premier niveau de menu

```html
<ul class="menu" id="main-menu">
  <li class="sub-menu sub-top" level="1">A</li>
</ul>
```

 * Indiquer la class `menu` pour préciser l'existence du premier menu
 * Indiquer la class `sub-menu` pour chaque sous-menu
 * Indiquer les class `sub-top`, `sub-bottom`, `sub-right`, `sub-left` en fonction de l'orientation de l'élément
 * Préciser le "niveau" de profondeur du menu avec le tag `level`

* Ajouter un sous-sous-menu
 * commencer par ajouter un sous-menu avec un tab `sub` précisant l'id du sous-sous-menu

```html
<ul class="menu" id="main-menu">
  <li class="sub-menu sub-top" level="1" sub="sub-A">A</li>
</ul>
```

 * Déclarer la hiérarchie du nouveau sous-menu plus loin dans la page html en indiquant bien l'id spécifié précédemment, et répéter l'opération autant de fois que nécessaire.

```html
<ul class="menu" id="sub-A">
  <li class="sub-menu sub-top" level="2">A1</li>
  <li class="sub-menu sub-left" level="2">A2</li>
  <li class="sub-menu sub-right" level="2" sub="sub-A3">A3</li>
</ul>
```

 * Attention, pour les menus possédant une forte profondeur, il est nécessaire de changer la valeur de la variable `max_level` dans le script `main.js` afin d'assurer la réussite du mode expert.
