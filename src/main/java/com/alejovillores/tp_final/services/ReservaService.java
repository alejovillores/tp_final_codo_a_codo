package com.alejovillores.tp_final.services;

import com.alejovillores.tp_final.exceptions.ReservaHechaException;
import com.alejovillores.tp_final.exceptions.ReservaNoEncontradaError;
import com.alejovillores.tp_final.models.Reserva;
import com.alejovillores.tp_final.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@Service
public class ReservaService {
    @Autowired
    ReservaRepository reservaRepository;
    @Autowired
    EmailService emailService;

    public ArrayList<Reserva> obtenerReservas(){
        return (ArrayList<Reserva>) this.reservaRepository.findAll();
    }

    public ArrayList<Reserva> obtenerReservaPorCantidad(Integer cantidad) {
        return (ArrayList<Reserva>) this.reservaRepository.findAllByCantidad(cantidad);
    }

    public Reserva agregarReserva(Reserva nuevaReserva, boolean email){
        if (this.reservaRepository.existsByEmail(nuevaReserva.getEmail())){
            throw new ReservaHechaException();
        }

        Reserva reservaGuardada = this.reservaRepository.save(nuevaReserva);

        if (email){
            SimpleMailMessage confirmacion = emailService.crearConfirmacion(
                    reservaGuardada.getEmail(),
                    reservaGuardada.getNombre(),
                    reservaGuardada.getApellido(),
                    reservaGuardada.getTimestamp(),
                    reservaGuardada.getId()
            );
            this.emailService.sendConfirmacion(confirmacion);
        }
        return reservaGuardada;
    }

    public Reserva editarReserva(Reserva nuevaReserva, boolean email){

        Reserva reservaGuardada = this.reservaRepository.save(nuevaReserva);
        if (email){
            SimpleMailMessage confirmacion = emailService.crearConfirmacionCambio(
                    reservaGuardada.getEmail(),
                    reservaGuardada.getNombre(),
                    reservaGuardada.getApellido(),
                    reservaGuardada.getTimestamp(),
                    reservaGuardada.getId()
            );
            this.emailService.sendConfirmacion(confirmacion);
        }
        return reservaGuardada;
    }

    public void eliminarReserva(Long id,String email) throws Exception{
        if (this.reservaRepository.existsByEmail(email) && this.reservaRepository.existsById(id)){
            this.reservaRepository.deleteById(id);
        }
        else{
            throw new Exception();
        }
    }

    public Reserva obtenerReserva(Long id) throws ReservaNoEncontradaError {

        Optional<Reserva> reserva = this.reservaRepository.findById(id);
        if (reserva.isEmpty()){
            throw new ReservaNoEncontradaError();
        }

        return reserva.get();
    }
}
