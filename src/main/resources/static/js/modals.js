var isModalActive = false;

//trigger handling without ajaxStop cause handling wont work with reload then
$(window).on('load', function(){
    //modal delete confirm
    $('#deletePayConfirm').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var id=button.data('id')
        var name=button.data('name')
        var modal = $(this)
        modal.find('#hiddenForm').attr('action','/payment/delete/'+id)
        modal.find('.modal-body .name').text(name)
    });

    //modal edit payment
    $('#editPaymentModal').on('show.bs.modal', function(event){
    categoryName='';
        var button = $(event.relatedTarget);
        var id = button.data('id');
        $("#id").val( id );
        $('#nameEdit').val( $('#name'+id).text() );
        $('#priceEdit').val( $('#price'+id).text() );
        date = $('#date'+id).text().split('-');
        $('#dateEdit').val( date[2]+'-'+date[1]+'-'+date[0] );
        $('#descriptionEdit').val( $('#description'+id).text() );
        categoryName = $('#categoryId'+id).text();
//        $('select[name="categoryId"]').find('option:constains()').attr("selected", true);

        found = false;
        $('[name=categoryId] option').filter(function() {
            //return ($(this).text() == categoryName);
            if($(this).text() == categoryName){
                found=true; return true;
            }
        }).prop('selected', true);
        if(!found){
            console.log("weszło")
          $('select[name=categoryId]').val(-1);
        }
        found = false;

    });
});

function changeAddButton(){
    if(isModalActive==false){
            $("#addPaymentButton").text('Close');
            this.isModalActive = true;
        }else{
            $("#addPaymentButton").text('Add');
            this.isModalActive = false;
        };
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