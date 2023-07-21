const { createApp } = Vue;

const app = createApp({

  data() {

    return{
    transactionSorted:[],
    initDate:"",
    finishDate:""

        }
  },
  created() {
    this.loadData()
    const params=new URLSearchParams(location.search)
    accountId=params.get('id')
    

  },
  methods:{
    loadData(){
      axios.get("http://localhost:8080/api/accounts/current")
      .then(response => {
      const accounts=response.data
      const accountById = accounts.find(accounts => accounts.id == accountId)
      const transactions = accountById.transaction
      this.transactionSorted=transactions.sort((a,b)=> a.creationDate - b.creationDate)
      console.log(this.transactionSorted);
  
      })
      .catch(error => {
        console.error(error);
      });
    },
    signOut(){
      axios.post('/api/logout')
      .then(response => window.location.href="/web/login.html")
    },
    show(){
      console.log(this.initDate, this.finishDate);
    }
  }

});app.mount('#app')