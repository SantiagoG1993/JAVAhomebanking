const { createApp } = Vue;

const app = createApp({

  data() {

    return{
    accounts:[],
    accountId:[],
    accountById:[],
    transactions:[],
    transactionSorted:[]

        }
  },
  created() {
    this.loadData()


  },
  methods:{
    loadData(){
      axios.get("http://localhost:8080/api/accounts/")
      .then(response => {
      this.accounts=response.data
             const params=new URLSearchParams(location.search)
             this.accountId=params.get('id')
       this.accountById = this.accounts.find(accounts => accounts.id == this.accountId)
       this.transactions = this.accountById.transaction
       this.transactionSorted=this.transactions.sort((a,b)=> a.amount - b.amount)
    
  
      })
      .catch(error => {
        console.error(error);
      });
    },
  }

});app.mount('#app')