const { createApp } = Vue;

const app = createApp({

  data() {

    return{
    clients:[],
    accounts:[],
    loans:[],
    showAccount:false,
    accountType:"",
    selectedAccount:[]

  }
  },
  created() {
    axios.get("/api/accounts/current")
    .then(res=>{this.accounts=res.data})
    .catch(err=>console.log(err.data))

    this.loadData()
   
    axios.get("/api/loans")
    .then(res=> {console.log(res.data)})
    .catch(err=>console.log(err))
  
   

  },
  methods:{
    loadData(){
      axios.get("http://localhost:8080/api/clients/current")
      .then(response => {
      this.clients=response.data
      this.loans=this.clients.loans.filter(loan=>loan.currentPayments>0)
      console.log(this.loans);
      })
      .catch(error => {
        console.error(error);
      });

    
    },
    signOut(){
      axios.post('/api/logout')
      .then(response => window.location.href="/web/login.html")
    },
    createAccounts() {
      swal({
        title: "Are you sure?",
        icon: "warning",
        buttons: ["No", "Yes"],
        dangerMode: true,
      }).then((confirmed) => {
        if (confirmed) {
          axios
            .post(`http://localhost:8080/api/clients/current/accounts?accountType=${this.accountType}`)
            .then((res) => {
              swal(res.data, "", "success");
              window.location.href = "/web/pages/accounts.html";
            })
            .catch((err) => swal(err.response.data, "", "error"));
        } else {
          swal("Account creation canceled", "", "info");
        }
      });
    },
    
    showAccountType(){
      this.showAccount=true
    },
    deleteAccount() {
      swal({
        title: "Are you sure?",
        icon: "warning",
        buttons: ["No", "Yes"],
        dangerMode: true,
      }).then((confirmed) => {
        if (confirmed) {
          const url = "/api/accounts/delete";
          const data = this.selectedAccount[0];
          axios
            .put(url, data)
            .then((res) => {
              swal(res.data, "", "success");
              window.location.href = "/web/pages/accounts.html";
            })
            .catch((err) => swal(err.response.data, "", "error"));
        } else {
          swal("Account deletion canceled", "", "info");
        }
      });
    },
    
    show(){
      console.log(this.selectedAccount[0].number);
    }
  }
 

});app.mount('#app')


/* deleteAccount(){
  swal({
    title: "Are you sure?",
    icon: "warning",
    buttons: ["No", "Yes"],
    dangerMode: true,
  })
  .then((confirmed)=>{const url="/api/accounts/delete"
  axios.post(`http://localhost:8080/api/clients/current/accounts?accountType=${this.accountType}`)
  .then(res=>{swal(res.data, "", "success"),window.location.href="/web/pages/accounts.html"})
  .catch(err=>swal(err.response.data, "", "error"))})     
}, */