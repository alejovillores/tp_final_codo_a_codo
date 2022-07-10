const app = new Vue({
    el: "#main-form",
    data: {
        opciones: [
            {hora: "19", value: "19:00:00"},
            {hora: "20:30", value: "20:30:00"},
            {hora:"21", value: "21:00:00"},
            {hora:"22:30",value:"22:30:00"}
        ],
        ok: false,
        error:false,
        persona: {
        },
        button:true
        
    },
    created(){
        var hoy = new Date();
        this.fecha = {
            dia: hoy.getDate(),
            mes: hoy.getMonth() + 1 , // getMonth arranca desde el 0
            anio: hoy.getFullYear(),
        }
    },
    methods: {
        buscarId(){
            let id = document.querySelector("#persona-id").value
            
            let url = `http://localhost:8080/reservas/${id}`
            fetch(url)
            .then(res => res.json())
            .then(data => {
                if (data.id > 0){
                    this.persona = data;
                    this.button = false;
                }
                else{
                    this.persona = {
                        nombre:"No existe",
                        apellido:"No existe",
                        email:"No existe",
                    }
                }
            })
            .catch(error => console.error(error))
        },
        realizarReserva() {
            reserva = {
                id: document.querySelector("#persona-id").value,
                nombre: this.persona.nombre,
                apellido: this.persona.apellido,
                email: this.persona.email,
                cantidad: document.querySelector('#cantidad').value,
                timestamp: document.querySelector('#opciones').value,
                send: document.querySelector('#send').checked
            }

            console.log(reserva);
            const url = 'http://localhost:8080/reservas' ;
            console.log(url)

            var options = {
                method: 'PUT',
                body: JSON.stringify(reserva),
                headers: {'Content-Type': 'application/json'},
                redirect: 'follow'
            }
            this.ok = false
            
            fetch(url, options)
            .then(
                response => response.json()
            )
            .then(data => {
                console.log(data)
                if (data.id !== -1){
                    this.ok = true
                }
                else {
                    this.ok = false
                    this.error = true
                }
            })
            .catch(err => console.error(err))
        }
    }
})