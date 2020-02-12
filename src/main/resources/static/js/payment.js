var paymentEditVue;
var paymentsDataTable;
var isModalActive = false;
var originalData;
var updatedData;

$(document).ready(function () {
    $('[data-toggle="tooltip"]').tooltip();

    /****************** DATATABLES */
    table.destroy();
    paymentsDataTable = $("#dataTable").DataTable({
        // ajax: '/api/paymentsDT',
        ajax: $('#paymentURL').val(),
        order: [],
        columnDefs: [
            {"orderable": false, "targets": 5}
        ],
        columns: [
            {data: "name"},
            {data: "price"},
            {data: "date"},
            {data: "description"},
            {
                render: function (data, type, row) {
                    if (row.categoryName !== null) {
                        return '<a href="/category/' + row.categoryName + '">' + row.categoryName + '</a>';
                    } else {
                        return "";
                    }
                }
            },
            {
                render: function (data, type, row) {
                    tmp = '<div style="width:80px;">';
                    tmp += '<a data-toggle="modal" href="#deletePayConfirm" class="icon-block" data-id="' + row.id + '" data-name="' + row.name + '">';
                    tmp += '<img src="/assets/delete-button.png" style="width:26%; height:5%; float:right; margin-left:10px;" data-toggle="tooltip" title="DELETE"/>';
                    tmp += '</a>';
                    tmp += '<a  data-toggle="modal" href="#editPaymentModal" class="icon-block" data-id="' + row.id + '">';
                    tmp += '<img src="/assets/edit-button.png" style="width:23%; height:5%; float:right;" data-toggle="tooltip" title="EDIT"/></a>';
                    tmp += '</div>';
                    return tmp;
                }
            }
        ]
    });

    /***************** UPDATE SUBMIT */
    $("#editPaymentForm").submit(function (e) {
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
                toastr.success('Payment updated');
            })
            .catch(function (error) {
                toastr.error('Payment update error');
            });
        $('#editPaymentModal').modal('toggle');
        reloadDataTables();
        //return false;
    });

    /**************** UPDATE MODAL */
    $('#editPaymentModal').on('show.bs.modal', function (event) {
        var paymentId = $(event.relatedTarget).data('id');
        //        var data = paymentsDataTable.data();
        let data = paymentsDataTable.data().toArray();
        data.forEach(function (el) {
            if (el.id == paymentId) {
                let date = el.date.split('-');
                $('#idEdit').val(el.id);
                $('#nameEdit').val(el.name);
                $('#priceEdit').val(el.price);
                $('#dateEdit').val(date[2] + '-' + date[1] + '-' + date[0]);
                $('#descriptionEdit').val(el.description);
                $('[name=categoryId] option').filter(function () {   /** setting category in modal */
                    if ($(this).text() === el.categoryName) {
                        return true;
                    } else {
                        $('select[name=categoryId]').val(-1);
                    }
                }).prop('selected', true);
            }
        });

    });

    /***************** ADD PAYMENT SUBMIT */
    $('#addPaymentForm').submit(function (e) {
        e.preventDefault();
        axios.post('/api/payment/add', {
            id: -1,
            name: $('#name').val(),
            price: $('#price').val(),
            date: $('#date').val(),
            description: $('#description').val(),
            categoryId: $('#categories').val()
        }).then(function (response) {
            toastr.success("Payment added succesfuly");
        }).catch(function (error) {
            toastr.error("Error while adding payment");
        })
        $('#addPaymentButton').click();
        reloadDataTables();
    });
});

function changeAddButton() {
    if (!isModalActive) {
        $("#addPaymentButton").text('Close');
        isModalActive = true;
    } else {
        $("#addPaymentButton").text('Add');
        isModalActive = false;
    }
}

function reloadDataTables() {
    setTimeout(function () {
        this.paymentsDataTable.ajax.reload();
    }, 500);
}


//trigger handling without ajaxStop cause handling wont work with reload then
$(window).on('load', function () {
//modal delete confirm
    $('#deletePayConfirm').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var id = button.data('id')
        var name = button.data('name')
        var modal = $(this)
        modal.find('#hiddenForm').attr('action', '/payment/delete/' + id)
        modal.find('.modal-body .name').text(name)
    });
});


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
