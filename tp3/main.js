$(document).ready( function() {

  //$(".menu").hide();

  var latency = 1000;
  var timeoutid = 0;
  var current_level = 1;

  var new_menu = null;

  var c=document.getElementById("canvas");
  c.width = $(window).width();
  c.height = $(window).height();

  active_stroke = false;
  var point1 = [];
  var point2 = [];

  var selected;

  $(document).mousedown(function(e){
    //$("#target");
    current_level = 1;

    new_menu = $(".menu").clone(true, true);

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

      /* Attendre latency ms avant que l'action goToSubMenu soit réalisée */
      clearTimeout(timeoutid);
      timeoutid = setTimeout(goToSubMenu, latency);

      point2.x = e.pageX;
      point2.y = e.pageY;

      var c=document.getElementById("canvas");
      var ctx=c.getContext("2d");
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
            $(".sub-left").addClass("selected");
        } else if (angle <= 3*pi4 && angle > pi4) { //top
            $(".sub-top").addClass("selected");
        } else if (angle <= pi4 && angle > - pi4) { //right
            $(".sub-right").addClass("selected");
        } else if (angle <= -pi4 && angle > - 3*pi4) { //bottom
            $(".sub-bottom").addClass("selected");
        }
      }
    }
  });

  $(document).mouseup(function(){
    $("#menu-1").remove();

    active_stroke = false;

    var c=document.getElementById("canvas");
    var ctx=c.getContext("2d");
    ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
    ctx.beginPath();
    ctx.stroke();
    ctx.closePath();
  });

  function goToSubMenu() {
    current_level++;

        /* Afficher et masquer les différents niveaux */
        $(new_menu).show();
        $(".sub-menu[level!='"+current_level+"']",new_menu).hide();
  }

});