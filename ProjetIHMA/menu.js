$(document).ready(function() {

  var STATES = {
    IDLE : 1,
    MENU_OPEN : 2,
    NO_BUBBLE : 3,
    BUBBLE : 4
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
    }
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
        $('.hovered').removeClass('hovered'); // Pour anticiper le changement d'état
        if( hvd.offset() && ! (evt.pageX > hvd.offset().left + hvd.width())) {
          hvd.parent().removeClass('open');
        }
        if( hvd.offset() && (evt.pageX > hvd.offset().left + hvd.width()/2)) {
          if(hvd.parent('li').children('ul').length == 0 || hvd.hasClass("fav")) {
            console.log("to bubble");
            state = STATES.BUBBLE;
            break;
          }
        }
        if(!$(evt.target).parent().parent().hasClass("main-navigation")) {
          $(evt.target).addClass('hovered');
          $(evt.target).parent('li').addClass('open');
        }
        break;
      case STATES.BUBBLE:
        hvd = $('.hovered');
        if( hvd.offset() && (evt.pageX <= hvd.offset().left + hvd.width()/2)) {
          console.log('to no bubble');
          $(".bubble").css({
            width: 0,
            height: 0
          })
          state = STATES.NO_BUBBLE;
          break;
        }
        $('.hovered ~ ul').parent().addClass('open'); // Ouvrir un sous-menu
        dist = 10000;
        var closest = undefined;
        if($(evt.target).hasClass('fav')) {
          closest = $(evt.target);
          dist = 5;
        } else {
          $(".open>ul>li>a.fav").each(function() {
              if( hvd.offset() && ! (evt.pageX > hvd.offset().left + hvd.width())) {
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
      case STATES.MENU_OPEN:
      case STATES.NO_BUBBLE:
      case STATES.BUBBLE:
      default:
        selectBubble(evt);
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
