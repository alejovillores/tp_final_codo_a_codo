package com.alejovillores.tp_final.repositories;

import com.alejovillores.tp_final.models.Reserva;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ReservaRepository extends CrudRepository<Reserva,Long> {
    public abstract ArrayList<Reserva> findAllByCantidad(Integer cantidad);

    public abstract boolean existsByEmail(String email);
}
