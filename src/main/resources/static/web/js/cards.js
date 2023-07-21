const {createApp} = Vue;
const app = createApp({

    data() {
        return{
            cards:[],
            debitCards:[],
            creditCards:[],
            selectedCard:[]

        }

    },
    created(){
      const currentDate = new Date();
        this.loadData()

        
        
    },
    methods:{
        loadData() {
            axios.get("http://localhost:8080/api/cards/current")
              .then(response => {
                this.cards = response.data;
                console.log(this.cards);
                this.debitCards = this.cards.filter(card => {
                  return card.type === "DEBIT";
                });         
                this.creditCards = this.cards.filter(card => {
                  return card.type === "CREDIT";
                });
              })
              .catch(error => {
                console.log(error);
              });
              console.log(this.debitCards);
          },
        signOut(){
            axios.post('/api/logout')
            .then(response => window.location.href="/web/login.html")
          },
          show(){
            console.log(this.selectedCard);
          },
          deleteCard(){
            const url="/api/cards/delete"
            axios.put(url,this.selectedCard[0])
            .then(res=>window.location.href="/web/pages/cards.html")
            .catch(err=>console.log(err))
          },
          isCardExpired(date) {
            const currentDate = new Date().toISOString().split("T")[0];
            return date < currentDate;
          }

    },
   


});app.mount("#app")