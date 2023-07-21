const {createApp}=Vue;
app=createApp({

    data() {
        return{
            destinationRadio:"",
            accounts:[],
            originAccount:"",
            destinationAccount:"",
            description:"",
            amount:0



        }
    },
    created(){
        this.loadData()
        
        


    },
    computed:{

        montoDisponible:function(){
            const account=this.accounts.find(acc=>acc.number==this.originAccount)
            return account ? account.balance : "";
            

        }
    },
    methods:{
        loadData(){
            axios.get("http://localhost:8080/api/clients/current")
            .then(res=>{
                this.accounts=res.data.accounts.filter(accounts=>accounts.deleted==false)
            
            })
            .catch(err=> console.log(err))
        },
        transfer() {
            let num = this.amount.toFixed(1);
            swal({
              title: "Are you sure?",
              icon: "warning",
              buttons: ["No", "Yes"],
              dangerMode: true,
            })
            .then((confirmed) => {
              if (confirmed) {
                axios.post('/api/transaction', {
                  amount: num,
                  description: this.description,
                  originAccount: this.originAccount,
                  destinationAccount: this.destinationAccount
                })
                .then(res => swal(res.data, "", "success"))
                .catch(err => swal(err.response.data, "", "error"));
              }
            });
          }
          


    },
    
});app.mount("#app")