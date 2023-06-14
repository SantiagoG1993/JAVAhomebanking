const { createApp } = Vue;

const app = createApp({

  data() {

    return{
    mensaje:'santi',
    clients:[],
    clientsSorted:[]
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
      this.clientsSorted=this.clients.accounts.sort((a, b)=> a.balance - b.balance)

      
  
      })
      .catch(error => {
        console.error(error);
      });
    },
  }
 

});app.mount('#app')