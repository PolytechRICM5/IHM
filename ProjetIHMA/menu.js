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
    ax = a.offset().left + a.outerWidth() / 2;
    ay = a.offset().top + (a.outerHeight() / 2);
    return Math.sqrt(
      Math.pow((ax - b.pageX), 2)
      + Math.pow((ay - b.pageY), 2)
    );
  }

  function computeBubbleSize(element, evt) {
    var dist = 0;
    x = Math.min(
      Math.abs(evt.pageX - (element.offset().left + element.outerWidth())),
      Math.abs(evt.pageX - element.offset().left)
    );
    y = Math.min(
      Math.abs(evt.pageY - (element.offset().top + element.outerHeight())),
      Math.abs(evt.pageY - element.offset().top)
    );
    if(
      evt.pageX >= element.offset().left
      && evt.pageX <= element.offset().left + element.outerWidth()
    ) {
      dist = y
    }
    else if(
      evt.pageY >= element.offset().top
      && evt.pageY <= element.offset().top + element.outerHeight()
    ) {
      dist = x
    } else {
      dist = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
      //dist = distance(element, evt);
    }
    /*
    */
    return dist + 5;
  }

  function selectBubble(evt) {
    res = $(".hovered").text();
    $('.selection').text(res);
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
          dist = 5;
        } else {
          $(".open>ul>li>a.fav").each(function() {
            $(this).removeClass("hovered");
            curr = distance($(this),evt);
            if(curr < dist) {
              dist = curr;
              closest = $(this);
            }
          })
          dist = computeBubbleSize(closest, evt);
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
      case STATES.NO_BUBBLE:
        break;
      case STATES.BUBBLE:
        selectBubble(evt);
        $('.hovered').removeClass('hovered');
      default:
        state = STATES.IDLE
    }
    $("li").removeClass("open");
    $(".bubble").css({
      width: 0,
      height: 0
    })
  });

  $(document).on('mouseover mousedown', 'a, img', function() {
    return false;
  });

});
