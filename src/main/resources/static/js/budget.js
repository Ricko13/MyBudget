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
                axios.post("/api/income", budgetVue.request)
                    .then(function (response) {
                        toastr.success("Added income")
                    }).catch(function (error) {
                    toastr.error("Error while adding income: ");
                    console.log("Error: " + error);
                });
                reloadDataTables();
                budgetVue.getBudgetData();
            },
            getBudgetData: function () {
                axios.get("/api/budget").then(function (response) {
                    budgetVue.budget = response.data.budget;
                }).catch(function (error) {
                    console.log("Error while getting budget data: " + error);
                });
            }
        }
        /*,
        computed: {
             budgetData: function () {
                 axios.get("/api/budget").then(function (response) {
                     return response;
                 });
             }  
        }, */
    })
}

function initIncomeDT() {
    incomeDataTable = $('#incomeDataTable').DataTable({
        ajax: "/api/incomeDT",
        columns: [
            {data: "title"},
            {data: "value"},
            {data: "timestamp"},
            {
                render: function () {
                    return "";
                }
            }
        ],
        columnDefs: [
            {"orderable": false, "targets": 3}
        ]
    });
}

function reloadDataTables() {
    setTimeout(function () {
        this.incomeDataTable.ajax.reload();
    }, 500);
}