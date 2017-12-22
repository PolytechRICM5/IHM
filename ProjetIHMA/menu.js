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
        state = STATES.NO_BUBBLE;
        break;
      case STATES.NO_BUBBLE:
        hvd = $('.hovered');
        if( hvd.offset() && ! (evt.pageX > hvd.offset().left + hvd.width())) {
          $('.hovered').parent().removeClass('open');
        }
        if( hvd.offset() && (evt.pageX > hvd.offset().left + hvd.width()/2)) {
          console.log("coucou");
          state = STATES.BUBBLE;
        }
        $('.hovered').removeClass('hovered');
        if($(evt.target).parent('li').length > 0) {
          if(!$(evt.target).parent().parent().hasClass("main-navigation")) {
            $(evt.target).addClass('hovered');
          }
          $(evt.target).parent('li').addClass('open');
        }
        break;
      case STATES.BUBBLE:
        $('.hovered ~ ul').parent().addClass('open');
        dist = 10000;
        var closest = undefined;
        if($(evt.target).hasClass('fav')) {
          closest = $(evt.target);
          dist = 5;
        } else {
          $(".open>ul>li>a.fav").each(function() {
              if( hvd.offset() && ! (evt.pageX > hvd.offset().top + hvd.width())) {
                console.log(hvd.offset().top);
                console.log(evt.pageX);
                $('.hovered').parent().removeClass('open');
              }
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
        selectBubble(evt);
        $("li").removeClass("open");
        $('.hovered').removeClass('hovered');
        state = STATES.IDLE;
        break;
      case STATES.BUBBLE:
        if($(evt.target).parents("ul").length == 0) {
          $('ul li > ul li').removeClass('open');
          $('.hovered').removeClass('hovered');
          state = STATES.NO_BUBBLE;
          break;
        }
        selectBubble(evt);
          $("li").removeClass("open");
          $('.hovered').removeClass('hovered');
          state = STATES.IDLE;
        break;
      default:
        $("li").removeClass("open");
        $('.hovered').removeClass('hovered');
        state = STATES.IDLE;
    }
    $(".bubble").css({
      width: 0,
      height: 0
    })
  });

  $(document).on('mouseover mousedown', 'a, img', function() {
    return false;
  });


});
