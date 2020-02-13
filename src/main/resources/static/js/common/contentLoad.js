//TODO not used but can be optimalised for exercise
function changeContent(content){
 switch (content) {
     case "history":
          $('.container').fadeOut(150, function(){
              $('.container').load("/history .container > *", function () {
                  if($('.container-small').length)
                      $('.container-small').removeClass('container-small').addClass('container');
                  $('.container').fadeIn(150);
              })
          });
         break;
     case "home":
         $('.container').fadeOut(150, function(){
             $('.container').load("/ .container > *", function () {
                 if($('.container-small').length)
                     $('.container-small').removeClass('container-small').addClass('container');
                 $('.container').fadeIn(150);
             })
         });
         break;
     case "category":
         $('.container').fadeOut(150, function(){
             $('.container').load("/category .container > *", function () {
                 if($('.container-small').length)
                     $('.container-small').removeClass('container-small').addClass('container');
                 $('.container').fadeIn(150);
             })
         });
        break;
     case "newPayment":
         $('.container').fadeOut(150, function(){
             $('.container').load("/payment/add .container > *", function () {
                 $('.container').addClass('container-small');
                 $('.container').fadeIn(150);
             })
         });
         break;
     case "newCategory":
         $('.container').fadeOut(150, function(){
             $('.container').load("/category/add .container > *", function () {
                 $('.container').addClass('container-small');
                 $('.container').fadeIn(150);
             })
         });
         break;
 }
}
/* Mozesz uzyc tej opcji aby nie wywolywac metody w onclicku - BEST WAY
* $('#myLink').click(function(){ MyFunction(); return false; });*/

