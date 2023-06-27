const { createApp } = Vue;

const app = createApp({

  data() {

    return{
    clients:[],
    accountsSorted:[],
    loans:[]
  }
  },
  created() {
    this.loadData()

  
  
  },
  methods:{
    loadData(){
      axios.get("http://localhost:8080/api/clients/current")
      .then(response => {
      this.clients=response.data
      this.accountsSorted=this.clients.accounts.sort((a, b)=> a.balance - b.balance)
      this.loans=this.clients.loans
      })
      .catch(error => {
        console.error(error);
      });
    },
    signOut(){
      axios.post('/api/logout')
      .then(response => window.location.href="/web/login.html")
    },
    createAccounts(){
      axios.post("http://localhost:8080/api/clients/current/accounts")
      .then(response=> window.location.href="/web/pages/accounts.html")
    }
  }
 

});app.mount('#app')