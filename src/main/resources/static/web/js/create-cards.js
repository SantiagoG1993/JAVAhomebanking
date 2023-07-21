const { createApp } = Vue;
const app = createApp({
    data() {
        return {
            cardType: "",
            cardColor: ""

        }
    },
    created() {


    },
    methods: {
        createCard() {
            swal({
                title: "Are you sure?",
                icon: "warning",
                buttons: ["No", "Yes"],
                dangerMode: true,
            })
                .then((confirmed) => {
                    if (confirmed) {
                        axios.post('/api/clients/current/cards?type=' + this.cardType + '&color=' + this.cardColor)
                            .then(res => {swal(res.data, "", "success"),window.location.href="/web/pages/cards.html"})
                            .catch(err => swal(err.response.data, "", "error"));
                    }
                });
        },
    },
});
app.mount("#app")

