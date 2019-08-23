
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


function deletePos(id){
}


$("#addPayment").click(function () {
    $("#myModal").modal(); return false;
});

/*getting data attribute for delete confirm modal*/
$( document ).ajaxStop(function() {  //force handlers to execute matching again after content is loaded by ajax

    $('#deletePayConfirm').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var id=button.data('id')
        var name=button.data('name')
        var modal = $(this)
        modal.find('#hiddenForm').attr('action','/payment/delete/'+id)
        modal.find('.modal-body .name').text(name)
    })

    $('#deleteCatConfirm').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var id=button.data('id')
        var name=button.data('name')
        var modal = $(this)
        modal.find('#hiddenForm').attr('action','/category/delete/'+id)
        modal.find('.modal-body .name').text(name)
    })

})
//trigger handling without ajaxStop cause handling wont work with reload then
$(window).on('load', function(){
    $('#deletePayConfirm').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var id=button.data('id')
        var name=button.data('name')
        var modal = $(this)
        modal.find('#hiddenForm').attr('action','/payment/delete/'+id)
        modal.find('.modal-body .name').text(name)
    })

    $('#deleteCatConfirm').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var id=button.data('id')
        var name=button.data('name')
        var modal = $(this)
        modal.find('#hiddenForm').attr('action','/category/delete/'+id)
        modal.find('.modal-body .name').text(name)
    })
});