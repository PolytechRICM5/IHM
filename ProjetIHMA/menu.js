$(document).ready(function() {

  var mouse_pos = {};
  var current_menu = {};

  $("div.sub").hide();
  // On récupère le canvas dans lequel on va dessiner
  var c=document.getElementById("canvas");
  c.width = $(window).width();
  c.height = $(window).height();
  var ctx=c.getContext("2d");

  current_menu = $("nav#menu ul");

  $(document).mousemove(function(e) {
    mouse_pos.x = e.clientX;
    mouse_pos.y = e.clientY;

  });

  function updateRow() {

    current_menu.each(function(index) {
      if(closest == undefined) {
        closest = {};
        closest.x = $(this).offset().left;
        closest.y = $(this).offset().top;
      } else {
      }
    });

    ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
    ctx.beginPath();
    ctx.moveTo(mouse_pos.x,mouse_pos.y);
    ctx.lineTo(closest.x, closest.y);
    ctx.stroke();
    ctx.closePath();
  }

});
