
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


});
/* $('#deleteCatConfirm').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var id=button.data('id')
        var name=button.data('name')
        var modal = $(this)
        modal.find('#hiddenForm').attr('action','/category/delete/'+id)
        modal.find('.modal-body .name').text(name)
    })*/



/*$("#addPayment").click(function () {
    $("#myModal").modal(); return false;
});*/

/*getting data attribute for delete confirm modal*/
/*$( document ).ajaxStop(function() {  //force handlers to execute matching again after content is loaded by ajax
   ...
})*/