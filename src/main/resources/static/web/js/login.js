const { createApp } = Vue;
const app = createApp({

    data() {
        return {
            username: "",
            password: "",
            showForm: false,
            firstName: "",
            lastName: "",
            registerPassword: "",
            registerEmail:""
        }
    },
    created() {


    },
    methods: {
        logIn() {
            axios.post('/api/login', `email=${this.username}&password=${this.password}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response =>
                    window.location.href = '/web/pages/accounts.html')
                .catch(error => {
                    const errorMessage = document.getElementById("error_message")
                    errorMessage.textContent = "Invalid username or password, please try again"
                })
        },
        showRegisterForm() {
            this.showForm = true
            const errorMessage = document.getElementById("error_message")
            errorMessage.textContent = ""
        },
        createAccount() {
            axios.post('/api/clients', `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.registerEmail}&password=${this.registerPassword}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
            .then(response=>axios.post('/api/login', `email=${this.registerEmail}&password=${this.registerPassword}`, {     headers: { 'content-type': 'application/x-www-form-urlencoded' } })
            .then(response =>
                window.location.href = '/web/pages/accounts.html'))
                
        },
    }
}); app.mount("#app")