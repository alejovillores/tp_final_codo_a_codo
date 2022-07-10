const app = new Vue({
    el: "#app",
    data: {
        mostrar:false,
        id:0,
        email: '',
        respuesta:'',
        
    },
    methods:{
        cancelarReserva(){

            this.mostrar = true
            em = this.email
            i = this.id

            const url = `http://localhost:8080/reservas/${i}/${em}` ;
            console.log(url)


            var options = {
                method: 'DELETE',
            }
            
            fetch(url,options)
            .then(data => data.text())
            .then(respuesta => {
                this.mostrar = false
                this.respuesta = respuesta
            })
            .catch(err => console.error(err))

        }
    }
})