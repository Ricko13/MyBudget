var paymentEditVue;
var paymentsDataTable;
var isModalActive = false;
var originalData;
var updatedData;

$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();

    /****************** DATATABLES */
    table.destroy();
    paymentsDataTable = $("#dataTable").DataTable({
        // ajax: '/api/payments',
        order: [],
        columnDefs: [
            { "orderable": false, "targets": 5}
        ]
    });

    /***************** UPDATE SUBMIT */
    $("#editPaymentForm").submit(function(e) {
       e.preventDefault();
        updatedData = {
            id: $('#idEdit').val(),
            name: $('#nameEdit').val(),
            price: $('#priceEdit').val(),
            date: $('#dateEdit').val(),
            description: $('#descriptionEdit').val(),
            categoryId: $('#categoriesEdit').children("option:selected").val()
        };
        axios.post('/api/payment/update', updatedData)
        .then(function (response) {
            toastr.success('Payment updated');   // location.reload();  console.log(response);
        })
        .catch(function (error) {
            toastr.error('Payment update error');
        });
        reloadDataTables();
        $('#editPaymentModal').modal('toggle');
        //return false;
    });

    /**************** UPDATE MODAL */
    $('#editPaymentModal').on('show.bs.modal', function(event){
        var paymentId = $(event.relatedTarget).data('id');
        let date = $('#date'+paymentId).text().split('-');
        originalData = {
            id: paymentId,
            name: $('#name'+paymentId).text(),
            price: $('#price'+paymentId).text(),
            date: date[2]+'-'+date[1]+'-'+date[0],
            description: $('#description'+paymentId).text(),
            category: $('#categoryId'+paymentId).text()  //its name, not ID
        };

        for(var key in originalData){   /** assigned modal inputs with originalData values */
            $('#'+key+'Edit').val(originalData[key]);
        }
        /**powyższe nie ustawi category*/
        $('[name=categoryId] option').filter(function() {   /** setting category in modal */
            if($(this).text() === originalData.category){ return true;}
            else{$('select[name=categoryId]').val(-1);}
        }).prop('selected', true);

    });


});

function changeAddButton(){
    if(!isModalActive){
        $("#addPaymentButton").text('Close');
        isModalActive = true;
    }else{
        $("#addPaymentButton").text('Add');
        isModalActive = false;
    };
}

function reloadDataTables(){
    for(var key in updatedData){   /** assigned modal inputs with originalData values */
        $('#'+key+updatedData.id).html(updatedData[key]);
    }
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
