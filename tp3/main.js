$(document).ready( function() {

  var mouse_pos; // Enregister l'evenement souris pour pouvoir l'utiliser dans la fonction appellée dans le setTimeOut
  var start;

  var latency = 100; //Temps avant l'affichage du sous menu
  var timeoutid = 0;
  var current_level = 1; //Niveau servant à parcourir les sous-menus
  var new_menu = null; // Menu affiché qui sera cloné
  active_stroke = false;
  var point1 = [];
  var point2 = [];

  // FOR EXPERT MODE
  var timeoutid_mode = 0;
  var latency_mode = 300; //Temps avant le passage en mode expert
  var expert_mode;
  var stroke = [];
  var isActive = false;
  var step = 2;

  // On récupère le canvas dans lequel on va dessiner
  var c=document.getElementById("canvas");
  c.width = $(window).width();
  c.height = $(window).height();
  var ctx=c.getContext("2d");

  /*
	Lorsque l'on clique, on affiche le menu à l'endroit ou le clic est effectué et on commence à tracer.
  */
  $(document).mousedown(function(e){
	expert_mode = true;
	mouse_pos = e;
	start = new Date();

	if(expert_mode) 
	{
		isActive = true;
	    stroke = [];
	    updateStroke(e);

	    ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
	    ctx.beginPath();
	    ctx.moveTo(stroke[0].x,stroke[0].y);
	    timeoutid_mode = setTimeout(openMenu,latency_mode);
	}
	
	/*
	if(!expert_mode)
	{
		openMenu()
	}
	*/
});

  /*
	Quand la souris bouge, on trace le trait depuis le point de départ
	Et on calcule quel est l'option séléctionnée selon l'angle
	Ensuite, si une option est selectionnée et si elle a un sous-menu
	alors au bout d'un momeent (latency) ce sous menu s'affiche
  */
  $(document).mousemove(function(e) {
  	clearTimeout(timeoutid_mode);
  	if(expert_mode && isActive)
  	{
  		/*
  		console.log("coucou");
	  	clearTimeout(timeoutid);
	  	timeoutid = setTimeout(openMenu, latency_mode);
	  	*/
	    updateStroke(e);
	    ctx.lineTo(stroke[stroke.length-1].x,stroke[stroke.length-1].y);
	    ctx.stroke();
  	}


  	if(active_stroke && !expert_mode) 
  	{
  		mouse_pos = e;

  		/* Attendre latency ms avant que l'action goToSubMenu soit réalisée */
  		clearTimeout(timeoutid);
  		timeoutid = setTimeout(goToSubMenu, latency);

  		point2 = getMousePos(c,e);

  		// Dessiner le trait
  		ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
  		ctx.beginPath();
  		ctx.moveTo(point1.x,point1.y);
  		ctx.lineTo(point2.x, point2.y);
  		ctx.stroke();
  		ctx.closePath();

  		// Calculer l'angle
  		dx = point2.x - point1.x;
  		dy = point1.y - point2.y;
  		angle = Math.atan2(dy,dx);
  		pi4 = Math.PI/4;

  		// Ajouter la classe selected à l'element correspondant à l'angle et au niveau actuel
  		$(".sub-menu").removeClass("selected");
  		if(Math.sqrt((dx*dx)+(dy*dy)) > 50) {
        if(angle > 3*pi4 || angle < -3*pi4){ //left
        	$(".sub-left[level='"+current_level+"']").addClass("selected");
        } else if (angle <= 3*pi4 && angle > pi4) { //top
        	$(".sub-top[level='"+current_level+"']").addClass("selected");
        } else if (angle <= pi4 && angle > - pi4) { //right
        	$(".sub-right[level='"+current_level+"']").addClass("selected");
        } else if (angle <= -pi4 && angle > - 3*pi4) { //bottom
        	$(".sub-bottom[level='"+current_level+"']").addClass("selected");
        }
    }
}
});

  /*
	Au relachement de la souris on récupère l'élément séléctionné,
	on supprime le menu cloné
	et on nettoie le canvas
  */
  $(document).mouseup(function(){
  	if(expert_mode) 
  	{
		ctx.closePath();

	    isActive = false;
	    var directions = [];

	    var prev_point = stroke[0];
	    for (var i = step-1; i < stroke.length; i+=step) {
	      curr_point = stroke[i];

	      dx = curr_point.x - prev_point.x;
	      dy = prev_point.y - curr_point.y;
	      angle = Math.atan2(dy,dx);
	      pi4 = Math.PI/4;

	      var dir = null;

	      if(Math.sqrt((dx*dx)+(dy*dy)) > 10) {

	        if(angle > 3*pi4 || angle < -3*pi4){ //left
	          dir = "left";
	        } else if (angle <= 3*pi4 && angle > pi4) { //top
	          dir = "top";
	        } else if (angle <= pi4 && angle > - pi4) { //right
	          dir = "right";
	        } else if (angle <= -pi4 && angle > - 3*pi4) { //bottom
	          dir = "bottom";
	        }

	        if(directions[directions.length-1] != dir) {
	          directions[directions.length] = dir;
	        }
	        prev_point = curr_point;
	      }
	    }

	    if(directions.length == 1) {
	      directions[1] = directions[0];
	    }

	    $("#selected_item").text(selectItem(directions));
	    ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
  	}

  	if(!expert_mode)
  	{
  		var selected_text = $("#menu-1 li[level='"+current_level+"'].selected").text();
	  	$("#selected_item").text(selected_text); //Afficher dans le HTML le texte de l'element choisit
	  	$("#menu-1").remove();

	  	active_stroke = false;

	  	ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
	  	ctx.beginPath();
	  	ctx.stroke();
	  	ctx.closePath();
	}
	var end = new Date();
	$("#time").text(end-start+"ms");
  });

  /*
	Aller dans un sous menu signifie :
	Verifier si il y a un sous menu correspondant a l'element selectionné
	Si non on ne fait rien.
	Si oui, on récupère l'id du sous menu à afficher (attribut sub dans le html)
	On supprime le menu-1 pour le remplacer par le sous menu cloné (placé au bon endroit)
  */
  function goToSubMenu() {
  	// On récupère l'element selectionné de bon niveau
  	var curr_selected = $("#menu-1 li[level='"+current_level+"'].selected:first");
  	// Si il y a un element selectionné
  	if(curr_selected.length) {
  		// On regarde s'il a un sous-menu
  		var sub = curr_selected.attr("sub");
  		if(sub) {
  			// On rentre dans le sous menu
  			current_level++;
  			$(".sub-menu").removeClass("selected"); //Tout deselectionner
  			$("#menu-1").remove();

  			// Cloner le sous-menu
  			new_menu = $("#"+sub).clone(true, true);
  			new_menu.attr("id", "menu-1");
  			$("body").prepend(new_menu);
  			new_menu.css({
  				top: mouse_pos.pageY - $(".menu").height() / 2,
  				left: mouse_pos.pageX - $(".menu").width() / 2
  			});

  			// Recuperer le nouveau point de départ du trait et effacer l'ancien trait
  			point1 = getMousePos(c,mouse_pos);
  			ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);

  			// Mettre les bonnes visibilités
  			$(new_menu).show();
  			$(".sub-menu[level!='"+current_level+"']",new_menu).hide();
  		}
  	}
  }

  /*
	Cette fonction permet de récupérer les coordonnées du clic souris fournis par evt dans le canvas 
	Voir : https://stackoverflow.com/questions/17130395/real-mouse-position-in-canvas
  */
  function  getMousePos(canvas, evt) {
	  var rect = canvas.getBoundingClientRect(), // abs. size of element
	      scaleX = canvas.width / rect.width,    // relationship bitmap vs. element for X
	      scaleY = canvas.height / rect.height;  // relationship bitmap vs. element for Y

	      return {
	    x: (evt.clientX - rect.left) * scaleX,   // scale mouse coordinates after they have
	    y: (evt.clientY - rect.top) * scaleY     // been adjusted to be relative to element
		}
	}

	function updateStroke(e) {
	    var len = stroke.length;
	    stroke[len] = getMousePos(c,e);
	}

	function selectItem(directions) {
		var curr_menu = $("#main-menu");

		for(var i = 0; i < directions.length; i++) {
			var sub_menu = $(".sub-"+directions[i], curr_menu);
			if(sub_menu.length > 0) {
				var sub_id = sub_menu.attr("sub");
				if(sub_id){
					curr_menu = $("#"+sub_id);
				} else {
					return sub_menu.text();
				}
			} else {
				return "Not an Element";
			}

		}

	}

	function openMenu() {
		expert_mode = false;
		current_level = 1;

	    // On clone le menu, on lui donne l'id menu-1 et on le place sous la souris
	    new_menu = $("#main-menu").clone(true, true);
	    new_menu.attr("id", "menu-1");
	    $("body").prepend(new_menu);
	    new_menu.css({
	    	top: mouse_pos.pageY - $(".menu").height() / 2,
	    	left: mouse_pos.pageX - $(".menu").width() / 2
	    });

	    /* Afficher et masquer les différents niveaux */
	    $(new_menu).show();
	    $(".sub-menu[level!='"+current_level+"']",new_menu).hide();

	    // Recupérer le point cliqué dans le canvas
	    point1 = getMousePos(c,mouse_pos);
	    active_stroke = true; // Signaler que l'on commence à tracer
	}

});
