package com.alejovillores.tp_final;

import com.alejovillores.tp_final.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;


import java.time.LocalTime;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailServiceTests {

	@Autowired
	private EmailService emailService;

	@Test
	public void test01SeEnviaMailDeConfirmacionAUsuario(){
		String email = "avillores@fi.uba.ar";
		String nombre = "Solchi";
		String apellido = "Fontenla";
		LocalTime horario = LocalTime.now();


		SimpleMailMessage confirmacion = this.emailService.crearConfirmacion(email,nombre,apellido,horario, 1L);

		assertDoesNotThrow(() -> this.emailService.sendConfirmacion(confirmacion));
	}

}
