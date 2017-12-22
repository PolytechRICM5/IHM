$(document).ready(function() {

  /* Tous les états de notre automate */
  var STATES = {
    IDLE : 1,
    MENU_OPEN : 2,
    NO_BUBBLE : 3,
    BUBBLE : 4
  };

  var state = STATES.IDLE;

  /* Calcule la distance euclidienne entre le centre d'un élément du menu et la souris */
  function distance(a,b) {
    ax = a.offset().left + a.outerWidth() / 2;
    ay = a.offset().top + (a.outerHeight() / 2);
    return Math.sqrt(
      Math.pow((ax - b.pageX), 2)
      + Math.pow((ay - b.pageY), 2)
    );
  }

  /* Calcul de la taille réelle de la Bubble en fonction de la distance au plus proche point de l'élément à la souris */
  function computeBubbleSize(element, evt) {
    var dist = 0;
    // Calcul de x et y les plus proches de la souris, qui seront réutilisés dans la fonction
    x = Math.min(
      Math.abs(evt.pageX - (element.offset().left + element.outerWidth())),
      Math.abs(evt.pageX - element.offset().left)
    );
    y = Math.min(
      Math.abs(evt.pageY - (element.offset().top + element.outerHeight())),
      Math.abs(evt.pageY - element.offset().top)
    );
    if (
      evt.pageX >= element.offset().left
      && evt.pageX <= element.offset().left + element.outerWidth()
    ) { // Si la souris se trouve sur l'axe X du menu
      dist = y
    }
    else if (
      evt.pageY >= element.offset().top
      && evt.pageY <= element.offset().top + element.outerHeight()
    ) { // Si la souris se trouve sur l'axe Y du menu
      dist = x
    } else { // Sinon, en fonction des angles du menu
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
        if( hvd.offset() && (evt.pageX > hvd.offset().left + hvd.width()/2)) { // Détection d'entrée dans la partie droite (bubble) du menu
          if(hvd.parent('li').children('ul').length == 0 || hvd.hasClass("fav")) {
            console.log("to bubble");
            state = STATES.BUBBLE;
            break;
          }
        }
        if(!$(evt.target).parent().parent().hasClass("main-navigation")) { // Ouverture du premier élément du menu
          $(evt.target).addClass('hovered');
          $(evt.target).parent('li').addClass('open');
        }
        break;
      case STATES.BUBBLE:
        hvd = $('.hovered');
        if( hvd.offset() && (evt.pageX <= hvd.offset().left + hvd.width()/2)) { // On quitte le mode bubble (côté gauche du menu)
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
          $(".open>ul>li>a.fav").each(function() { // Sélection de l'élément à hover
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
        /* Affichage du hover et ajustement de la taille du bubble */
        $(closest).addClass("hovered");
        $(".bubble").css({
          'width': dist*2,
          'height': dist*2,
          'border-radius': dist*2
        })
        break;
      default:

    }
    /* Déplacement de la div bubble sous la souris */
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
      default: // Dans tous les cas, on active l'item sélectionné, et on remove toutes les sélections
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

  /* Empêche le grab and drop des liens et images (évite certains problèmes) */
  $(document).on('mouseover mousedown', 'a, img', function() {
    return false;
  });


});
