var isModalActive = false;

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

function changeAddButton(){
    if(isModalActive==false){
            $("#addPaymentButton").text('Close');
            this.isModalActive = true;
        }else{
            $("#addPaymentButton").text('Add');
            this.isModalActive = false;
        }
}

//tooltip
$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();
});

/* $('#deleteCatConfirm').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var id=button.data('id')
        var name=button.data('name')
        var modal = $(this)
        modal.find('#hiddenForm').attr('action','/category/delete/'+id)
        modal.find('.modal-body .name').text(name)
    })*/