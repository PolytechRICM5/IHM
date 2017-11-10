$(document).ready( function() {

  var c=document.getElementById("canvas");
  c.width = $(window).width();
  c.height = $(window).height();
  var ctx=c.getContext("2d");

  var stroke = [];
  var isActive = false;

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
      if(stroke.length < 5) {

      ctx.lineTo(stroke[stroke.length-1].x,stroke[stroke.length-1].y);
      ctx.stroke();
    }
    }

  });

  $(document).mouseup(function(e){

    console.log(stroke);
    ctx.closePath();
    isActive = false;

  });

  function updateStroke(e) {
    var len = stroke.length;
    stroke[len] = [];
    stroke[len].x = e.pageX;
    stroke[len].y = e.pageY;
  }

});
