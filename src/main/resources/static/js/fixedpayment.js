const URL = '/api/fixedpayment'

var paymentEditVue;
var paymentsDataTable;
var isModalActive = false;
var originalData;
var updatedData;
var movingToHistory = false;


$(document).ready(function () {
    $('[data-toggle="tooltip"]').tooltip();



    /****************** DATATABLES */
    table.destroy();  //TODO CO Z TYM - usuń inicjowanie datatables w template.html::js-include
    paymentsDataTable = $("#dataTable").DataTable({
        ajax: URL,
        order: [],
        columnDefs: [
            {"orderable": false, "targets": 5}
        ],
        columns: [
            {// {data: "name"},
                render: function (data, type, row) {
                    return `<div style="width: 200px; text-align: center">${row.name}</div>`
                }
            },
            {// {data: "price"},
                render: function (data, type, row) {
                    return formatMoney(row.price)
                }
            },
            {
                render: function (data, type, row) {
                    return `<div style='width: 200px;'>${row.description ? row.description : ''}</div>`
                }
            },
            {
                render: function (data, type, row) {
                    if (row.categoryName !== null && row.categoryName !== undefined) {
                        return '<div class="categoryCell"><strong><a href="/category/' + row.categoryName + '" style="color:' + row.categoryColor + '">' + row.categoryName + '</a></strong></div>';
                    } else {
                        return "";
                    }
                }
            },
            {
                data: "latestExecution"
            },
            {
                render: function (data, type, row) {
                    tmp = '<div>';
                    tmp += '<a data-toggle="modal" href="#deletePayConfirm" class="icon-block" data-id="' + row.id + '" data-name="' + row.name + '">';
                    tmp += '<img src="/assets/delete-button.png" style="width:20px !important; height:5% !important; float:right; margin-left:10px;" data-toggle="tooltip" title="DELETE"/>';
                    tmp += '</a>';
                    tmp += '<a  data-toggle="modal" href="#editPaymentModal" class="icon-block" data-id="' + row.id + '" data-move="false">';
                    tmp += '<img src="/assets/edit-button.png" style="width:20px !important; height:5% !important; float:right;" data-toggle="tooltip" title="EDIT"/></a>';
                    tmp += '</div>';
                    return tmp;
                }
            }
        ]
    });

    /***************** UPDATE/MOVE SUBMIT */
    $("#editPaymentForm").submit(function (e) {
        e.preventDefault();
        if($('#priceEdit').val() <= 0) {
            toastr.warning('Price must be greater than 0')
            return;
        }
        updatedData = {
            id: $('#idEdit').val(),
            name: $('#nameEdit').val(),
            price: $('#priceEdit').val(),
            description: $('#descriptionEdit').val(),
            categoryId: $('#categoriesEdit').children("option:selected").val()
        };

        axios.post(URL + '/update', updatedData)
            .then(function (response) {
                toastr.success('Fixed Payment updated');
            })
            .catch(function (error) {
                toastr.error('Fixed Payment update error');
            });

        $('#editPaymentModal').modal('toggle');
        reloadDataTables();
    });

    /**************** UPDATE/MOVE MODAL */
    $('#editPaymentModal').on('show.bs.modal', function (event) {
        // checkIfMovingToHistory($(event.relatedTarget));

        var paymentId = $(event.relatedTarget).data('id');
        //        var data = paymentsDataTable.data();
        let data = paymentsDataTable.data().toArray();
        data.forEach(function (el) {
            if (el.id === paymentId) {
                $('#idEdit').val(el.id);
                $('#nameEdit').val(el.name);
                $('#priceEdit').val(el.price);
                // $('#dateEdit').val(el.date);
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

/*    function checkIfMovingToHistory(eventRelatetTarget) {
        if (eventRelatetTarget.data('move') === true) {
            $('#editPaymentTitle').html("Move to history");
            $('#editPaymentSubmit').html("Accept and move");
            movingToHistory = true;
        } else {
            $('#editPaymentTitle').text("Edit payment");
            $('#editPaymentSubmit').text("Submit");
            movingToHistory = false;
        }
    }*/

    /***************** ADD PAYMENT SUBMIT */
    $('#addPaymentForm').submit(function (e) {
        e.preventDefault();
        axios.post(URL + '/add', {
            id: -1,
            name: $('#name').val(),
            price: $('#price').val(),
            description: $('#description').val(),
            categoryId: $('#categories').val()
        }).then(function (response) {
            toastr.success("Fixed payment added succesfuly");
        }).catch(function (error) {
            toastr.error("Error while adding fixed payment");
        });
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
    var id;

    $('#deletePayConfirm').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Button that triggered the modal
        id = button.data('id');
        var name = button.data('name');
        var modal = $(this);
        modal.find('#hiddenForm').attr('action', '/payment/delete/' + id)
        modal.find('.modal-body .name').text(name)
    });

    $('#hiddenForm').submit(function (e) {
        e.preventDefault();
        axios.get(URL + '/delete/' + id)
            .then(function (response) {
                toastr.success("Fixed payment deleted");
                reloadDataTables();
            }).catch(function (error) {
            toastr.error("Error while deleting fixed payment");
        });
        $('#deletePayConfirm').modal('toggle');
    });


});
