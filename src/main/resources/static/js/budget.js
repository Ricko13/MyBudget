var incomeDataTable;
var budgetVue;
var budget;

$(document).ready(function () {
    initIncomeDT();
    initBudgetVue();
    Vue.use(VueMask.VueMaskPlugin);
    budgetVue.getBudgetData();
});

function initBudgetVue() {
    budgetVue = new Vue({
        el: "#budget",
        data: {
            budget: "",
            request: {
                title: "",
                value: ""
            }
        },
        created: function () {  // created/mounted/updated/destroyed
        },
        methods: {
            submitIncome: function () {
                if(budgetVue.request.title !== ""){
                    axios.post("/api/income", budgetVue.request)
                        .then(function (response) {
                            toastr.success("Income added succesfully")
                        }).catch(function (error) {
                            toastr.error("Error while adding income: ");
                        });
                }
                setTimeout(function () {
                    reloadDataTables();
                    budgetVue.getBudgetData();
                }, 500);
            },
            getBudgetData: function () {
                axios.get("/api/budget")
                    .then(function (response) {
                        budgetVue.budget = formatMoney(response.data.budget);
                    }).catch(function (error) {
                        toastr.error("Error while getting budget data")
                    });
            },
            renderTimestamp(date) { //TODO tmp because issue with timestamp type in database
                let arrDateTime = date.toString().split('T')
                let arrDate = arrDateTime[0].split('-')
                return arrDate[2] + '-' + arrDate[1] + '-' + arrDate[0]
            },
            reloadDataTables() {
                setTimeout(function () {
                    this.incomeDataTable.ajax.reload();
                }, 500);
            }
        }
    })
}

function initIncomeDT() {
    incomeDataTable = $('#incomeDataTable').DataTable({
        ajax: "/api/incomeDT",
        columns: [
            {data: "title"},
            {
                render: function (data, type, row) {
                    return formatMoney(row.value);
                }
            },
            {
                render: function (data, type, row) {
                    return budgetVue.renderTimestamp(row.timestamp);
                }
            },
            {
                render: function (data, type, row) {
                    tmp = '<div style="width:30px;">'
                    tmp += '<a href="#deleteIncomeConfirm" onClick="deleteIncome(' + row.id + ')" class="icon-block" style="margin-top: auto; margin-bottom: auto;">';
                    tmp += '<img src="/assets/delete-button.png" style="width:20px !important; height:5% !important; float:right; margin-left:10px;"/>';
                    tmp += '</a>';
                    tmp += '</div>';
                    return tmp;

                   /* tmp = '<div style="width:80px;">';
                    tmp += '<a data-toggle="modal" href="#deletePayConfirm" class="icon-block" data-id="' + row.id + '" data-name="' + row.name + '">';
                    tmp += '<img src="/assets/delete-button.png" style="width:26%; height:5%; float:right; margin-left:10px;" data-toggle="tooltip" title="DELETE"/></a>';
                    tmp += '</div>';
                    return tmp;*/
                }
            }
        ],
        columnDefs: [
            {"orderable": false, "targets": 3}
        ],
        order: [[ 2, "desc" ]]
    });
}

function reloadDataTables() {
    setTimeout(function () {
        this.incomeDataTable.ajax.reload();
        budgetVue.getBudgetData();
    }, 500);
}

function deleteIncome(id) {
    axios.get(`/api/income/${id}`)
        .then(value => toastr.success('Income deleted successfully!'))
        .catch(reason => toastr.error('Error while deleting income'));
    this.reloadDataTables();
}