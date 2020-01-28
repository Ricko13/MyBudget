var paymentEditVue;
var paymentsDataTable;

$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();


    table.destroy();
    paymentsDataTable = $("#dataTable").DataTable({
        // ajax: '/api/payments',
        order: [],
        columnDefs: [
            { "orderable": false, "targets": 5}
        ]
    });


    $("#editPaymentForm").submit(function() {
        /*axios.post('/api/payment/update').then(function (response) {
            console.log(response);
            toastr.success('Payment updated');
        }).catch(function (error) {
            toastr.error('Payment update error');
        });*/
        axios.post('/api/payment/update', {
                id: $('#id').val(),
                name: $('#nameEdit').val(),
                price: $('#priceEdit').val(),
                date: $('#dateEdit').val(),
                description: $('#descriptionEdit').val(),
                categoryId: $('#categoriesEdit').children("option:selected").val()
        })
        .then(function (response) {
            console.log(response);
            toastr.success('Payment updated');
        })
        .catch(function (error) {
            toastr.error('Payment update error');
        });
        reloadDataTables();
        $('#editPaymentModal').modal('toggle');
        return false;
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

        found = false;
        $('[name=categoryId] option').filter(function() {
            if($(this).text() == categoryName){
                found=true; return true;
            }
        }).prop('selected', true);
        if(!found){
            $('select[name=categoryId]').val(-1);
        }
        found = false;

    });

    function changeAddButton(){
        if(!isModalActive){
            $("#addPaymentButton").text('Close');
            this.isModalActive = true;
        }else{
            $("#addPaymentButton").text('Add');
            this.isModalActive = false;
        };
    }

});

function reloadDataTables(){
    console.log('reloadDatatables');

}

/*
function initPaymentEditVue(){
    paymentEditVue=new Vue({
        el: '#editPaymentForm',
        data: {
            name:'',
            value:'',
            date:'',
            categoryId:'',
            description:''
        },
        methods: {
            postEditRequest: function () {

            }
        }
    })
}*/
