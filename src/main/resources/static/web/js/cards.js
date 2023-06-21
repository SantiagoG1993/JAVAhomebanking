const {createApp} = Vue;
const app = createApp({

    data() {
        return{
            debitCards:[],
            creditCards:[]

        }

    },
    created(){
        this.loadData()
        
        
    },
    methods:{
        loadData(){
            axios.get("http://localhost:8080/api/clients/1")
            .then(response=>{
                const cards=response.data.cards
                this.debitCards=cards.filter((card=>{
                    return card.type=="DEBIT"
                }))
                this.creditCards=cards.filter((card=>{
                    return card.type=="CREDIT"
                }))
            })
            .catch(error=>{
                console.log(error)
            })
        }
    },


});app.mount("#app")