const REPORT_URL = '/api/report';

var chartsVue;
var chartLabels = [];
var chartBackgrounds = [];
var chartData = [];
var chart = undefined;
var paymentsDataTable;

$(document).ready(function () {
    initChartsVue();
});

function initChartsVue() {
    chartsVue = new Vue({
        el: "#chartsVue",
        data: {
            startDate: moment().startOf('month').format('YYYY-MM-DD'),
            endDate: moment().format('YYYY-MM-DD'),
            reportData: {
                paymentsAmount: 0,
                totalOutcomeAmount: 0,
                totalIncomeAmount: 0,
                averageDailyOutcome: 0,
                incomeMinusOutcome: 0
            },
            barChartOptions: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                },
                legend: {
                    display: false
                }
            },
            pieChartOptions: { responsive: true, maintainAspectRatio: false }
        },
        created: function () {
            this.submitRange();
            // this.formatReportData();
        },
        methods: {
            submitRange() {
                axios.post(REPORT_URL, {'startDate': this.startDate, 'endDate': this.endDate})
                    .then(function (response) {
                        // console.log(response);
                        chartsVue.reportData = response.data;
                        chartsVue.formatReportData();
                        response.data.sumInCategories.forEach(function (entry, index) {
                            chartLabels[index] = entry.name;
                            chartBackgrounds[index] = entry.color;
                            chartData[index] = entry.summaryAmount;
                            Vue.nextTick(function () {
                                chartsVue.initChart('bar');
                            });
                        });
                    })
            },
            formatReportData() {
                this.reportData.budget = formatMoney(this.reportData.budget)
                this.reportData.totalOutcomeAmount = formatMoney(this.reportData.totalOutcomeAmount)
                this.reportData.totalIncomeAmount = formatMoney(this.reportData.totalIncomeAmount)
                this.reportData.averageDailyOutcome = formatMoney(this.reportData.averageDailyOutcome)
                this.reportData.incomeMinusOutcome = formatMoney(this.reportData.incomeMinusOutcome)
            },
            setDateRangeToCurrentMonth() {
                var date = new Date();
                var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
                startDate = formatDate(firstDay);
                var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
                endDate = formatDate(lastDay);
            },
            setDateRangeToToday() {
                startDate = endDate = formatDate(new Date());
            },
            initChart(type) {
                if (chart) {
                    window.chart.destroy();
                }
                var ctx = document.getElementById('chart').getContext('2d');
                chart = new Chart(ctx, {
                    type: type,
                    data: {
                        labels: chartLabels,
                        datasets: [{
                            label: 'Values in categories',
                            backgroundColor: chartBackgrounds,
                            borderColor: 'rgba(0,0,0)',
                            data: chartData
                        }]
                    },
                    options: this.getChartOptionsByType(type)
                });
            },
            getChartOptionsByType (type) {
                switch (type) {
                    case 'pie': return this.pieChartOptions;
                    case 'bar': return this.barChartOptions;
                }
            },
            convertResponseColorsToRgb(hexArray) {
                var rgbArray = [];
                hexArray.forEach(function (item, index) {
                    rgbArray[index] = this.hexToRGB(item);
                });
                return rgbArray;
            },
          /*  submitPaymentsDatatable() {
                table.destroy();
                paymentsDataTable = $("#dataTable").DataTable({
                    ajax: '/api/paymentsDT/',
                    order: [],
                    columnDefs: [
                        {"orderable": false, "targets": 5}
                    ],
                    columns: [
                        {data: "name"},
                        {data: "price"},
                        {
                            // render: formatLocalDate(row.date)
                            render: function (data, type, row) {
                                return formatLocalDate(row.date)
                            }
                        },
                        {data: "description"},
                        {
                            render: function (data, type, row) {
                                if (row.categoryName !== null) {
                                    return '<a href="/category/' + row.categoryName + '">' + row.categoryName + '</a>';
                                } else {
                                    return "";
                                }
                            }
                        }
                    ]
                });
            }*/
        }
    })
}


/*//TODO niepotrzebne chyba
function getCategoryData() {
    axios.get("/api/category/chart").then(function (response) {
        categories = response.data;
    }).catch(function (error) {
        toastr.error("Error while getting charts data");
    })
}*/


//TODO - test to delete
function initChartJs2() {
    var ctx = document.getElementById('myChart').getContext('2d');
    chart = new Chart(ctx, {
        type: 'pie',
        data: {
            // labels: chartLabels,
            /*datasets: [{
                label: 'Values in categories',
                backgroundColor: chartBackgrounds,
                borderColor: chartBackgrounds,
                data: chartData
            }]*/
            labels: [1, 2, 3, 4, 5, 6, 7],
            datasets: [{
                label: 'My First dataset',
                backgroundColor: 'rgb(52, 58, 64)',
                borderColor: 'rgba(255,99,132)',
                data: [0, 10, 5, 2, 20, 30, 45]
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false //options to make chart resizable and responsive
        }
    });
}

//TODO - test to delete
function initChartJs3() {
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


