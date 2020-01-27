$(document).ready(function(){
    /*$("#editPaymentSubmit").click(function () {
        // alert("test");
    });*/
    toastr.success('hello');

    $("#editPaymentForm").submit(function() {
        axios.post('/api/payment/update').then(function (response) {
            console.log(response);
            toastr.success('Payment updated');
        }).catch(function (error) {
            toastr.error('Payment update error');
        });
        return false;
    });
});