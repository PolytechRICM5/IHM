$(document).ready( function() {

  //$(".menu").hide();

  var latency = 300;
  var timeoutid = 0;
  var current_level = 1;
  var mouse_pos;

  var new_menu = null;

  var c=document.getElementById("canvas");
  c.width = $(window).width();
  c.height = $(window).height();
  var ctx=c.getContext("2d");

  active_stroke = false;
  var point1 = [];
  var point2 = [];

  var selected;

  $(document).mousedown(function(e){
    //$("#target");
    current_level = 1;

    new_menu = $("#main-menu").clone(true, true);

    new_menu.attr("id", "menu-1");

    $("body").prepend(new_menu);
    new_menu.css({
      top: e.pageY - $(".menu").height() / 2,
      left: e.pageX - $(".menu").width() / 2
    });

    /* Afficher et masquer les différents niveaux */
    $(new_menu).show();
    $(".sub-menu[level!='"+current_level+"']",new_menu).hide();

    point1.x = e.pageX;
    point1.y = e.pageY;
    active_stroke = true;

  });

  $(document).mousemove(function(e) {
    if(active_stroke) {

      mouse_pos = e;
      /* Attendre latency ms avant que l'action goToSubMenu soit réalisée */
      clearTimeout(timeoutid);
      timeoutid = setTimeout(goToSubMenu, latency);

      point2.x = e.pageX;
      point2.y = e.pageY;

      ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
      ctx.beginPath();
      ctx.moveTo(point1.x,point1.y);
      ctx.lineTo(point2.x, point2.y);
      ctx.stroke();
      ctx.closePath();

      dx = point2.x - point1.x;
      dy = point1.y - point2.y;
      angle = Math.atan2(dy,dx);
      pi4 = Math.PI/4;

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

  $(document).mouseup(function(){

    var selected_text = $("#menu-1 li[level='"+current_level+"'].selected").text();
    console.log(selected_text);
    $("#menu-1").remove();

    active_stroke = false;

    ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
    ctx.beginPath();
    ctx.stroke();
    ctx.closePath();
  });

  function goToSubMenu() {
    var curr_selected = $("#menu-1 li[level='"+current_level+"'].selected:first");
    if(curr_selected.length) {
      var sub = curr_selected.attr("sub");
      console.log(sub);
      if(sub) {
        current_level++;
        console.log(current_level);
        $(".sub-menu").removeClass("selected");
        /* Afficher et masquer les différents niveaux */
    	$("#menu-1").remove();

    	new_menu = $("#"+sub).clone(true, true);
    	new_menu.attr("id", "menu-1");
    	$("body").prepend(new_menu);
	    new_menu.css({
	      top: mouse_pos.pageY - $(".menu").height() / 2,
	      left: mouse_pos.pageX - $(".menu").width() / 2
	    });
	    point1.x = mouse_pos.pageX;
    	point1.y = mouse_pos.pageY;

        $(new_menu).show();
        $(".sub-menu[level!='"+current_level+"']",new_menu).hide();
      }
    }
  }

});
