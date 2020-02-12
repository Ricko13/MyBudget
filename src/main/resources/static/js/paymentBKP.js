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
//        ajax: '/api/paymentsDT',
        order: [],
        columnDefs: [
            { "orderable": false, "targets": 5}
        ],
/*        columns: [
            { data: "name"},
            { data: "price"},
            { data: "date"},
            { data: "description"},
            { render: function(data, type, row){
                    console.log(row.categoryName);
                    if(row.categoryName!==null){
                        return '<a href="/category/'+row.categoryName+'">'+row.categoryName+'</a>';
                    }else{
                        return "";
                    }
            }},
           { render: function(data, type, row){
               // tmp = '<td style="width:80px;">';
                tmp= '<a data-toggle="modal" href="#deletePayConfirm" class="icon-block" data-id="'+row.id+'" data-name="'+row.name+'">';
                tmp+= '<img src="/assets/delete-button.png" style="width:26%; height:5%; float:right; margin-left:10px;" data-toggle="tooltip" title="DELETE"/>';
                tmp+= '</a>';
                tmp+= '<a  data-toggle="modal" href="#editPaymentModal" class="icon-block" data-id="'+row.id+'">';
                tmp+= '<img src="/assets/edit-button.png" style="width:23%; height:5%; float:right;" data-toggle="tooltip" title="EDIT"/></a>';
*//*                    tmp+= '<a href="@{/payment/edit/'+${payment.id}'}" class="icon-block">';
                tmp+= '<img src="@{/assets/edit-button.png}" style="width:23%; height:5%; float:right;" data-toggle="tooltip" title="EDIT"/></a>';*//*
                          return tmp;
            }}
        ]*/
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
