const {createApp} = Vue;
const app=createApp({
    data(){
        return{
            accounts:[],
            loans:[],
            amount:[],
            originAccount:null,
            selectedLoan:[],
            selectedPayments:[]




        }
    },
    created(){
        axios.get("/api/clients/current")
        .then(res=>{
            this.accounts=res.data.accounts;
        
        })
        .catch(err=>console.log(err))
        axios.get("/api/loans")
    .then(res=> {this.loans=res.data})
    .catch(err=>console.log(err));






    },
    computed:{
        selectedPayments() {
            if (this.selectedLoan) {
              return this.selectedLoan.payments;
            }
            return [];
          },
       
    },
    methods:{
        


    }
});app.mount("#app")