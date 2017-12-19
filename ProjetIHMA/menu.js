$(document).ready(function() {

  var mouse_pos = {};
  var current_menu = {};
  var selected_item;

  var STATES = {
    IDLE : 1,
    MENU_OPEN : 2,
    NO_BUBBLE : 3,
    BUBBLE : 4,
    OUT_MENU : 5
  };

  var state = STATES.IDLE;

  function distance(a,b) {
    console.log(a.outerHeight());
    ax = a.offset().left + a.outerWidth() / 2;
    ay = a.offset().top + (a.outerHeight() / 2);
    return Math.sqrt(
      Math.pow((ax - b.pageX), 2)
      + Math.pow((ay - b.pageY), 2)
    );
  }

  /* Affichage de sous-menu */
  $(".main-navigation > li").mousedown(function(evt){
    switch (state) {
      case STATES.IDLE:
        $(this).addClass("open");
        state = STATES.MENU_OPEN;
        break;
      default:
    }
  });

  $(document).mousemove(function(evt){
    switch (state) {
      case STATES.MENU_OPEN:
        state = STATES.BUBBLE;
        break;
      case STATES.BUBBLE:
        dist = 10000;
        var closest = undefined;
        if($(evt.target).hasClass('fav')) {
          closest = $(evt.target);
          dist = 0;
        } else {
          $(".open>ul li a.fav").each(function() {
            $(this).removeClass("hovered");
            curr = distance($(this),evt);
            if(curr < dist) {
              dist = curr;
              closest = $(this);
            }
          })
        }
        $(closest).addClass("hovered");
        $(".bubble").css({
          'width': dist*2,
          'height': dist*2,
          'border-radius': dist*2
        })
        break;
      default:

    }

    var bubble = $(".bubble");

    $(".bubble").css({
      left: evt.pageX - bubble.width()/2,
      top: evt.pageY - bubble.height()/2
    });
  });

  $(document).mouseup(function(evt){
    switch (state) {
      case STATES.OUT_MENU:
      case STATES.MENU_OPEN:
        $("li").removeClass("open");
        state = STATES.IDLE;
        break;
      case STATES.NO_BUBBLE:
        $("li").removeClass("open");
        state = STATES.IDLE;
        break;
      case STATES.BUBBLE:
        selectBubble(evt);
        $("li").removeClass("open");
        break;
      default:
        $("li").removeClass("open");
    }
  });

  $(document).on('mouseover mousedown', 'a, img', function() {
    return false;
  });

});
