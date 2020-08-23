var categoryTable;
const categoryURL = '/api/categoryDT';
var editCategoryId;
var editCategoryName;

function reloadDataTables() {
    setTimeout(function () {
        this.categoryTable.ajax.reload();
    }, 500);
}

function editCategory(id, name) {
    this.editCategoryId = id;
    this.editCategoryName = name;
    input = '<input id="catNameEdit" type="text" class="d-inline form-control" style="width:150px;"/>';
    input += '<button class="d-inline btn btn-link" onClick="submitEdit()">ok</button>';
    $('#catName' + id).html(input);
    $('#catNameEdit').val(name);

}

function submitEdit() {
    axios.post('/api/category/update', {
        id: editCategoryId,
        name: $('#catNameEdit').val()
    }).then(function (response) {
        console.log(response);
        //$('#catName'+id).html(editCategoryName);
        reloadDataTables();
        toastr.success("Category edited");
    }).catch(function (error) {
        toastr.error("Error while updating category");
    });
}

function changeColor(id) {
    let newColor = $(`#color${id}`).val();
    console.log(newColor);
    axios.post('/api/category/color/change', { id: id, color: newColor })
        .then(value => toastr.success('Color changed'))
        .catch(reason => {
            toastr.error('Color change failed')
            console.log(reason)
        })
}

$(document).ready(function () {


    categoryTable = $('#categoryTable').DataTable({
        ajax: categoryURL,
        order: [],
        columnDefs: [
            {orderable: false, targets: 1},
            {width: "200px", targets: 1},
            {className: 'dt-body-right', targets: 1}
        ],
        columns: [
            {
                render: function (data, type, row) {
                    var toReturn = '<div id="catName' + row.id + '"><a href="/category/' + row.name + '">' + row.name + '</a>'
                    // return toReturn + '<div id="color' + row.id + '" style="background-color:' + row.color + '; width: 50px; height: 20px; float: left;"></div> </div>';
                    return toReturn + '<div style="float: left;"><input id="color' + row.id + '" type="color" onchange="changeColor(' + row.id +')"  value="'+ row.color +'"/></div>'
                }
            },
            /*{
                render: function(data, type, row){
                    return `<div id="color${row.id}" style="background-color: ${row.color}; width: 50px; height: 20px;"></div>`;
                    // return '<div id="color'+row.id+'" style="background-color:'+row.color+'; width: 50px; height: 20px;">'+row.color+'</div>';
                }
            },*/
            {
                render: function (data, type, row) {
                    tmp = '<div >';  /*style="width:80px;"*/
                    tmp += '<a data-toggle="modal" href="#deleteCatConfirm" class="icon-block" style="margin-top: auto; margin-bottom: auto;" data-id="' + row.id + '" data-name="' + row.name + '">';
                    tmp += '<img src="/assets/delete-button.png" style="width:20px !important; height:5% !important; float:right; margin-left:10px;" data-toggle="tooltip" title="DELETE"/>';
                    tmp += '</a>';
                    tmp += '<a onClick="editCategory(' + row.id + ', \'' + row.name + '\')" data-toggle="modal" href="#edit" data-id="' + row.id + '" data-name="' + row.name + '">';
                    tmp += '<img src="/assets/edit-button.png" style="width:20px !important; height:5% !important; float:right;" data-toggle="tooltip" title="EDIT"/></a>';
                    tmp += '</div>';
                    return tmp;
                }
            }
        ]
    });

    $('#deleteCatConfirm').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        $('#catName').text(button.data('name'));

        $('#categoryDeleteForm').submit(function (e) {
            e.preventDefault();
            axios.get('/api/category/delete/' + button.data('id'))
                .then(function (response) {
                    toastr.success("Category deleted");
                }).catch(function (error) {
                toastr.error("Error while deleting category");
            });
            $('#deleteCatConfirm').modal('toggle');
            reloadDataTables();
        });
    });


});