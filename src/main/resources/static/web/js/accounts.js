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
      axios.get("http://localhost:8080/api/clients/1")
      .then(response => {
      this.clients=response.data
      this.accountsSorted=this.clients.accounts.sort((a, b)=> a.balance - b.balance)
      console.log(this.accountsSorted)
      this.loans=this.clients.loans
      console.log(this.loans)

      

      
  
      })
      .catch(error => {
        console.error(error);
      });
    },
  }
 

});app.mount('#app')