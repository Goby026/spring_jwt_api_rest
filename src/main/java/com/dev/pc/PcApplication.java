package com.dev.pc;

import com.dev.pc.models.Role;
import com.dev.pc.models.Usuario;
import com.dev.pc.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class PcApplication {
//
//	@Autowired
//	private UsuarioRepository userRepository;
//
//	@Autowired
//	private PasswordEncoder encoder;
//
//	@PostConstruct
//	public void initUsers() {
//
//		List<Role> roles = new ArrayList<>();
//
//		roles.add(new Role(null,"ADMIN"));
//
//		List<Role> roles2 = new ArrayList<>();
//
//		roles2.add(new Role(null,"USER"));
//
//		List<Usuario> usuarios = Stream.of(
//				new Usuario(null, "gobydev", encoder.encode("password"), true, roles),
//				new Usuario(null, "marcel", encoder.encode("pwd1"), true, roles2),
//				new Usuario(null, "gabriela", encoder.encode("pwd2"), true, roles2),
//				new Usuario(null, "kratos", encoder.encode("pwd3"), true, roles2)
//		).collect(Collectors.toList());
//
//		userRepository.saveAll(usuarios);
//	}

	public static void main(String[] args) {
		SpringApplication.run(PcApplication.class, args);
	}

}
