var categoryTable;
const categoryURL = '/api/categoryDT';
var editCategoryId;
var editCategoryName;

function reloadDataTables(){
        setTimeout(function () {
            this.categoryTable.ajax.reload();
        }, 500);
};

function editCategory(id, name){
        this.editCategoryId = id;
        this.editCategoryName = name;
        input = '<input id="catNameEdit" type="text" class="d-inline form-control" style="width:150px;"/>';
        input+='<button class="d-inline btn btn-secondary" onClick="submitEdit()">ok</button>';
        $('#catName'+id).html(input);
        $('#catNameEdit').val(name);

};

function submitEdit(){
    console.log("XD");
    axios.post('/api/category/update', {
        id: editCategoryId,
        name: editCategoryName
    }).then(function(response){
        console.log(response);
        //$('#catName'+id).html(editCategoryName);
        //reloadDataTables();
        toastr.success("Category edited");
    }).catch(function(error){
        console.log("XDDDD");
        toastr.error("Error while updating category");
    });
}

$(document).ready(function(){



    categoryTable = $('#categoryTable').DataTable({
        ajax: categoryURL,
        order: [],
        columnDefs: [
            { "orderable": false, "targets": 1 },
            { "width": "200px", "targets": 1}
         ],
        columns: [
            {render: function(data, type, row){
                return '<div id="catName'+row.id+'"><a href="/category/'+row.name+'">'+row.name+'</a></div>';
            }},
            {render: function(data, type, row){
                tmp = '<div style="width:80px;">';
                tmp += '<a data-toggle="modal" href="#deleteCatConfirm" class="icon-block" data-id="' + row.id + '" data-name="' + row.name + '">';
                tmp += '<img src="/assets/delete-button.png" style="width:26%; height:5%; float:right; margin-left:10px;" data-toggle="tooltip" title="DELETE"/>';
                tmp += '</a>';
                tmp += '<a onClick="editCategory('+row.id+', \''+row.name+'\')" data-toggle="modal" href="#edit" data-id="'+row.id+'" data-name="'+row.name+'">';
                tmp += '<img src="/assets/edit-button.png" style="width:23%; height:5%; float:right;" data-toggle="tooltip" title="EDIT"/></a>';
                tmp += '</div>';
                return tmp;
            }}
        ]
    });

    $('#deleteCatConfirm').on('show.bs.modal', function(event){
        var button = $(event.relatedTarget);
        $('#catName').text(button.data('name'));

        $('#categoryDeleteForm').submit(function(e){
                e.preventDefault();
                axios.get('/api/category/delete/'+button.data('id'))
                .then(function(response){
                    toastr.success("Category deleted");
                }).catch(function(error){
                    toastr.error("Error while deleting category");
                });
                $('#deleteCatConfirm').modal('toggle');
                reloadDataTables();
            });
    });








});