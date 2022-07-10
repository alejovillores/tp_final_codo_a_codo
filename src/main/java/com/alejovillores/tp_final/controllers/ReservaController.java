package com.alejovillores.tp_final.controllers;

import com.alejovillores.tp_final.exceptions.ReservaHechaException;
import com.alejovillores.tp_final.exceptions.ReservaNoEncontradaError;
import com.alejovillores.tp_final.models.Reserva;
import com.alejovillores.tp_final.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    public static final int ERROR = -1;
    @Autowired
    ReservaService reservaService;

    @CrossOrigin
    @GetMapping()
    public ArrayList<Reserva> obtenerReservas() {
        System.out.println("Se realizo un GET de las reservas");
        return this.reservaService.obtenerReservas();
    }

    @CrossOrigin
    @GetMapping(path = "/{id}")
    public Reserva obtenerReserva(@PathVariable("id") Long id) {
        System.out.println("Se realizo un GET de las reservas");
        Reserva reserva;
        try{
            reserva = this.reservaService.obtenerReserva(id);
        }catch (ReservaNoEncontradaError e){
            reserva = new Reserva();
            reserva.setId(-1L);
        }
        return reserva;
    }

    @CrossOrigin
    @PostMapping()
    public Reserva guardarReserva(@RequestBody Reserva reserva){
        System.out.println("Se recibio un POST");
        //System.out.println(email);

        try {
            return this.reservaService.agregarReserva(reserva,reserva.isSend());
        }catch (ReservaHechaException | MailException exception){
            reserva.setId((long) ERROR);
            System.out.println("Hubo ERROR " + exception);
            return reserva;
        }
    }

    @CrossOrigin
    @PutMapping()
    public Reserva modificarReserva(@RequestBody Reserva reserva){
        System.out.println("Se recibio un POST");
        try {
            return this.reservaService.editarReserva(reserva,reserva.isSend());
        }catch (MailException exception){
            reserva.setId((long) ERROR);
            System.out.println("Hubo ERROR " + exception);
            return reserva;
        }
    }

    @CrossOrigin
    @DeleteMapping( path = "/{id}/{email}")
    public String eliminarPorId(@PathVariable("id") Long id, @PathVariable("email") String email) {
        String msg = "Reserva eliminada con exito!";
        try {
            this.reservaService.eliminarReserva(id,email);
        } catch (Exception e) {
            msg = "No se pudo eliminar la reserva correctamente";
        }
        return msg;
    }

}
