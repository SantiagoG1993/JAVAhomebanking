
const { createApp } = Vue;

const app = createApp({
  data() {
    return {
      clientData: {
        firstName:"",
        lastName:"",
        eMail:""
      },
      mensaje: 'Hello Vue!',
      clients:[],
      restResponse:""

    }
  },
  created() {
    
    this.loadData()


  },

  methods:{

    
    loadData(){
      axios.get("http://localhost:8080/rest/clients/")
      .then(response => {
      this.clients=response
      this.clients=this.clients.data._embedded.clients
      this.restResponse=this.clients
      console.log(this.restResponse)
      })
      .catch(error => {
        console.error(error);
      });
    },
    postClient(){
      axios.post("http://localhost:8080/rest/clients/",this.clientData)
      .then(response=>{
        this.loadData();
      })
    },

    addClient(){
      this.postClient();
    },


    deleteClient(clientId){
      axios.delete(clientId)
      .catch(error=>{
        console.log(error)
      })
      this.loadData()
    },
    modifyClient(clientId){
      axios.patch(clientId,this.clientData)
      .then(response=>{
        console.log(response)
  
        this.clientData.firstName=""
        this.clientData.lastName=""
        this.clientData.eMail=""   
        this.loadData()
      })
      .catch(error=>{
        console.log(error)
      })
  
  }
  },

  
  
});
app.mount('#app');
