package com.alejovillores.tp_final.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;


import java.time.LocalTime;


@Component
public class EmailService{

    public static final String SUBJECT = "Confirmacion Restaurante Alejo";
    @Autowired
    private JavaMailSender javaMailSender;


    public void send(String msg,String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("alejovillores@gmail.com");
        message.setTo(to);
        message.setSubject(msg);
        message.setText(msg);
        javaMailSender.send(message);
    }

    public SimpleMailMessage crearConfirmacion(String email, String nombre, String apellido, LocalTime horario, Long id) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("alejovillores@gmail.com");
        message.setTo(email);
        message.setSubject(SUBJECT);
        message.setText(this.makeMsg(nombre,apellido,horario,id));
        return  message;
    }

    public SimpleMailMessage crearConfirmacionCambio(String email, String nombre, String apellido, LocalTime horario, Long id) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("alejovillores@gmail.com");
        message.setTo(email);
        message.setSubject(SUBJECT);
        message.setText(this.makeCambio(nombre,apellido,horario,id));
        return  message;
    }


    private String makeMsg(String nombre, String apellido, LocalTime horario,Long id) {
        String inicio = String.format("Hola! %s %s \n",nombre,apellido);
        String body = String.format("La reserva  sido confirmada con éxito!\n\n Numero de Reserva: %d",id);
        String hora = String.format("Horario: %d : %d \n\n",horario.getHour(),horario.getMinute());
        String desdedida = "Te esperamos!";
        return inicio.concat(body).concat(hora).concat(desdedida);
    }

    private String makeCambio(String nombre, String apellido, LocalTime horario,Long id) {
        String inicio = String.format("Hola! %s %s \n",nombre,apellido);
        String body = "La reserva  sido modificada con éxito!\n";
        String hora = String.format("Horario: %d : %d \n\n",horario.getHour(),horario.getMinute());
        String desdedida = "Te esperamos!";
        return inicio.concat(body).concat(hora).concat(desdedida);
    }

    public void sendConfirmacion(SimpleMailMessage confirmacion) {
        this.javaMailSender.send(confirmacion);
    }

}
