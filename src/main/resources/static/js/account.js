var vue;

$(document).ready(function () {
    initAccountVue()
})

function initAccountVue() {
    vue = new Vue({
        el: "#accountContainer",
        data: {
        },
        methods: {
            openModal() {
                $('#changePasswordModal').modal('toggle');
            },
            changePassword() {
              axios.post('/api/user/changepassword', {currentPassword: this.currentPassword, newPassword: this.newPassword})
                    .then(value => {
                        $('#changePasswordModal').modal('toggle');
                    })
                    .catch(reason => {})
            },

        }
    })
}




