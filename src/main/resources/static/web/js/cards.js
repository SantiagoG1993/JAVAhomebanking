const {createApp} = Vue;
const app = createApp({

    data() {
        return{
            cards:[],
            debitCards:[],
            creditCards:[]

        }

    },
    created(){
        this.loadData()
        
        
    },
    methods:{
        loadData(){
            axios.get("http://localhost:8080/api/clients/current")
            .then(response=>{
                this.cards=response.data.cards
                this.debitCards=this.cards.filter((card=>{
                    return card.type=="DEBIT"
                }))
                this.creditCards=this.cards.filter((card=>{
                    return card.type=="CREDIT"
                }))
            })
            .catch(error=>{
                console.log(error)
            })
        },
        signOut(){
            axios.post('/api/logout')
            .then(response => window.location.href="/web/login.html")
          },
    },


});app.mount("#app")