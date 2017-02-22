package pl.aptewicz.ftthchecker.security;

import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class PasswordEncoder {

	public static void main(String[] args) {
		StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder("Wkn756!@");
		System.out.println(standardPasswordEncoder.encode("wknpim"));
	}
}
