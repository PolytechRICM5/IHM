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

  /* Affichage de sous-menu */
  $(".main-navigation>li").mousedown(function(evt){
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
        if(evt.target)
        break;
      default:

    }
  });

  $(document).mouseup(function(evt){
    switch (state) {
      case STATES.OUT_MENU:
      case STATES.MENU_OPEN:
        $("li").removeClass("open");
        state = STATES.IDLE;
        break;
      case STATES.NO_BUBBLE:
        console.log(evt.target);
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
