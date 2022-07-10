const reservas = new Vue({
    el: '#reservas-table',
    data: {
        reservas:[],
        checks: {},
    },
    created(){
        this.init();
    },
    methods:{
        init(){
            url = "http://localhost:8080/reservas"
            fetch(url)
            .then(res => res.json())
            .then(data => {
                console.log(data)
                this.reservas = data;
            })
            .catch(err => console.error(err))

            this.reservas.forEach(element => {
                if (!element.id in this.checks){
                    this.checks[element.id] = false;
                }
            });
        },
        actualizar(){
            this.init();
        },
        limpiar(){
            this.reservas = []
            this.checks.clear()
        },
        print(){
            window.print();
        }
    }
})