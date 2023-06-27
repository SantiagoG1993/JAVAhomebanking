const {createApp}=Vue;
const app = createApp({
        data(){
            return{
                cardType:"",
                cardColor:""

            }
        },
        created(){


        },
        methods:{
            createCard(){
                axios.post('/api/clients/current/cards?type=' + this.cardType + '&color=' + this.cardColor)
                .then(response=> console.log("tarjeta creada!"))
            }

        }
});app.mount("#app")