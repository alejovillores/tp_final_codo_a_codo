const app = new Vue({
    el: '#app-form', // el -> Element
    data: {  // data que le voy a pasar al elemento json.
      opciones: [
        {hora: "19", value: "19:00:00"},
        {hora: "20:30", value: "20:30:00"},
        {hora:"21", value: "21:00:00"},
        {hora:"22:30",value:"22:30:00"}
      ],
      ok: false,
      error:false,
    },
    created(){
        this.init();
    },
    methods: {
        init(){
            var nav = document.querySelector('#nav').clientHeight;
            var pantalla = window.innerHeight;
            var resto = Math.floor((pantalla - nav)/16); 
            var hoy = new Date();
            this.fecha = {
                dia: hoy.getDate(),
                mes: hoy.getMonth() + 1 , // getMonth arranca desde el 0
                anio: hoy.getFullYear(),
            }

            
            document.getElementById('main-form').style.height = `${resto}rem`;
            document.getElementsByClassName('footer')[0].style.position = `static`;
        },
        realizarReserva() {
            reserva = {
                nombre: document.querySelector('#nombre').value,
                apellido: document.querySelector('#apellido').value,
                email: document.querySelector('#email').value,
                cantidad: document.querySelector('#cantidad').value,
                timestamp: document.querySelector('#opciones').value,
                send: document.querySelector('#send').checked
            }

            console.log(reserva);
            const url = 'http://localhost:8080/reservas' ;
            console.log(url)

            var options = {
                method: 'POST',
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

    },
    
})
