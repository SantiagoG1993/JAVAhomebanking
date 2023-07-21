const { createApp } = Vue;
const app = createApp({
    data() {
        return {
            accounts: [],
            loans: [],
            amount:"",
            payments: 0,
            originAccount: null,
            selectedLoan: [],
            selectedPayments: []




        }
    },
    created() {
        axios.get("/api/clients/current")
            .then(res => {
                this.accounts = res.data.accounts.filter(account=>account.deleted==false);
                console.log(this.accounts);
            })
            .catch(err => console.log(err))
        axios.get("/api/loans")
            .then(res => { this.loans = res.data })
            .catch(err => console.log(err));






    },
    computed: {
        showPayments() {
            const payments = this.selectedLoan.payments
            return payments
        },
        showMaxAmount() {
            const maxAmount = this.selectedLoan.maxAmount.toLocaleString()
            return "Max. amount $ " + maxAmount
        }


    },

    methods: {
        apply() {
            swal({
                title: "Are you sure?",
                icon: "warning",
                buttons: ["No", "Yes"],
                dangerMode: true,
            })
                .then((confirmed) => {
                    if (confirmed) {
                        axios.post('/api/loan',{
                            destinationAccount: this.originAccount,
                            amount: this.amount,
                            payments: this.payments,
                            name: this.selectedLoan.name
                        },
                        window.location.href="/web/pages/accounts.html"
                        )
                        .then(res => swal(res.data, "", "success"))
                        .catch(err => swal(err.response.data, "", "error"));
                    }
                })
        }
    }
    }); app.mount("#app")
