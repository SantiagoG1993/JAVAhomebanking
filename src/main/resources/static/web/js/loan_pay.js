const { createApp } = Vue;
const app = createApp({
    data() {
        return {
            accounts:[],
            loans:[],
            loanName:[],
            accOrigin:[],
            originAccount: null,
            selectedLoan: [],                   
        }
    },
    created() {
        this. loadData()
        axios.get("/api/accounts/current")
        .then(res => {
            this.accounts = res.data
        })
        .catch(err => console.log(err))
    },

    methods: {
        loadData(){
            axios.get("http://localhost:8080/api/clients/current")
            .then(response => {
            const clients=response.data
            this.loans=clients.loans.filter(loan=>loan.currentPayments>0)
            })
            .catch(error => {
              console.error(error);
            });     
          },
        
        Pay(){
            console.log(this.selectedLoan.id)
            const url="/api/payment"
            const data={
               originAccount:this.originAccount,
                clientLoanId:this.selectedLoan.id,
            }
            axios.post(url,data)
            .then(res=>{window.location.href="/web/pages/accounts.html"
                console.log(res.data)})
            .catch(err=>console.log(err.response.data))
        }
    }
    }); app.mount("#app")
