const { createApp } = Vue;

const app = createApp({

  data() {

    return{
    transactionSorted:[]

        }
  },
  created() {
    this.loadData()
    const params=new URLSearchParams(location.search)
    accountId=params.get('id')


  },
  methods:{
    loadData(){
      axios.get("http://localhost:8080/api/accounts/")
      .then(response => {
      const accounts=response.data
      const accountById = accounts.find(accounts => accounts.id == accountId)
      const transactions = accountById.transaction
      this.transactionSorted=transactions.sort((a,b)=> a.amount - b.amount)
  
      })
      .catch(error => {
        console.error(error);
      });
    },
    signOut(){
      axios.post('/api/logout')
      .then(response => window.location.href="/web/login.html")
    }
  }

});app.mount('#app')