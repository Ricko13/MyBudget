
var budgetVue;
var budget;

$(document).ready(function () {

    initBudgetVue();

});

function initBudgetVue() {
    budgetVue = new Vue({
        el: "#budget",
        data: {
            test: budget,
        },
        computed: {
             budgetData: function () {
                 axios.get("/api/budget").then(function (response) {
                     return response;
                 });
             }  
        },
        methods: {
            getBudgetData: function(){
                axios.get("/api/budget").then(function (response) {
                    console.log(response);
                })
            }
        },
        created: function () {
            axios.get("/api/budget").then(function (response) {
                console.log(response);
                budget = response.data.budget;
            }).catch(function (error) {
                console.log(error);
            })
        }
            
        
    })
}