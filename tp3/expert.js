$(document).ready( function() {

  var c=document.getElementById("canvas");
  c.width = $(window).width();
  c.height = $(window).height();
  var ctx=c.getContext("2d");

  var stroke = [];
  var isActive = false;
  var step = 2;

  $(document).mousedown(function(e){

    isActive = true;
    stroke = [];
    updateStroke(e);

    ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
    ctx.beginPath();
    ctx.moveTo(stroke[0].x,stroke[0].y);

  });

  $(document).mousemove(function(e){

    if(isActive) {
      updateStroke(e);

      ctx.lineTo(stroke[stroke.length-1].x,stroke[stroke.length-1].y);
      ctx.stroke();
    }

  });

  $(document).mouseup(function(e){

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

    $("#selected_item").text(selectItem(directions));

  });

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
        return "error";
      }

    }

  }

  function getMousePos(canvas, evt) {
	  var rect = canvas.getBoundingClientRect(), // abs. size of element
	      scaleX = canvas.width / rect.width,    // relationship bitmap vs. element for X
	      scaleY = canvas.height / rect.height;  // relationship bitmap vs. element for Y

	  return {
	    x: (evt.clientX - rect.left) * scaleX,   // scale mouse coordinates after they have
	    y: (evt.clientY - rect.top) * scaleY     // been adjusted to be relative to element
	  }
	}

});
