const { createApp } = Vue;
const app = createApp({
    data() {
        return {
            loanName:"",
            maxAmount:0,
            payments:"",
            percentage:0
        }
    },
    created() {

    },

    methods: {
        createLoan(){
            swal({
                title: "Are you sure?",
                icon: "warning",
                buttons: ["No", "Yes"],
                dangerMode: true,
              }).then((confirmed) => {
                if (confirmed) {
                  const url="/api/loan/create"
                  const data={
                      name:this.loanName,
                      maxAmount:this.maxAmount,
                      payments: this.payments.split(',').map(payment => parseInt(payment.trim())),
                      percentage:this.percentage
                  }
                  axios.post(url,data)
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
    }
    }); app.mount("#app")
