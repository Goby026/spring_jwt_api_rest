package com.dev.pc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PcApplication implements CommandLineRunner {

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

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		String password = "123456";

		for (int i = 0; i < 4; i++){
			String passwordBcrypt = passwordEncoder.encode(password);
			System.out.println(passwordBcrypt);
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(PcApplication.class, args);
	}
//
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/api/**").allowedOrigins("http://localhost:4200/");
//			}
//		};
//	}

}
