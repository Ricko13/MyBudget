var categories;
var chartsVue;

$(document).ready(function(){
    getCategoryData();
    initChartJs();
//    initChartJs2();
    initChartsVue();
});

function initChartsVue(){
    chartsVue = new Vue({
    el: "chartsVue",
    data: {
    },
    methods: {
    }

    })
}

function getCategoryData(){
    axios.get("/api/category/chart").then(function(response){
        categories = response.data;
        console.log(categories);
    }).catch(function(error){
        toastr.error("Error while getting charts data");
    })
}

function initChartJs(){
    var ctx = document.getElementById('myChart').getContext('2d');
    var chart = new Chart(ctx, {
        // The type of chart we want to create
        type: 'pie',
        // The data for our dataset
        data: {
            labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
            datasets: [{
                label: 'My First dataset',
                backgroundColor: 'rgb(52, 58, 64)',
                borderColor: 'rgba(255,99,132)',
                data: [0, 10, 5, 2, 20, 30, 45]
            }]
        },
        // Configuration options go here
        options: {
            responsive: true,
            maintainAspectRatio: false //options to make chart resizable and responsive
        }
    });
}

function initChartJs2(){
    var ctx = document.getElementById('myChart2');
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
            datasets: [{
                label: '# of Votes',
                data: [12, 19, 3, 5, 2, 3],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });

}

function random_rgba() {
    var o = Math.round, r = Math.random, s = 255;
    return 'rgba(' + o(r()*s) + ',' + o(r()*s) + ',' + o(r()*s) + ',' + r().toFixed(1) + ')';
}


