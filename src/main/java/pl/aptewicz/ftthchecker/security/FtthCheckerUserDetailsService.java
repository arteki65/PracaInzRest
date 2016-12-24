package pl.aptewicz.ftthchecker.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.aptewicz.ftthchecker.domain.FtthCheckerUser;
import pl.aptewicz.ftthchecker.domain.FtthCheckerUserRole;
import pl.aptewicz.ftthchecker.domain.FtthCustomer;
import pl.aptewicz.ftthchecker.repository.FtthCheckerUserRepository;
import pl.aptewicz.ftthchecker.repository.FtthCustomerRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class FtthCheckerUserDetailsService implements UserDetailsService {

	private final FtthCheckerUserRepository ftthCheckerUserRepository;

	private final FtthCustomerRepository ftthCustomerRepository;

	public FtthCheckerUserDetailsService(FtthCheckerUserRepository ftthCheckerUserRepository,
			FtthCustomerRepository ftthCustomerRepository) {
		this.ftthCheckerUserRepository = ftthCheckerUserRepository;
		this.ftthCustomerRepository = ftthCustomerRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<FtthCheckerUser> ftthCheckerUserOptional = Optional
				.ofNullable(ftthCheckerUserRepository.findByUsername(username));

		Collection<GrantedAuthority> authorities = new ArrayList<>();
		if (ftthCheckerUserOptional.isPresent()) {
			FtthCheckerUser ftthCheckerUser = ftthCheckerUserOptional.get();
			if (FtthCheckerUserRole.ADMIN.equals(ftthCheckerUser.getFtthUserRole())) {
				for (FtthCheckerUserRole ftthCheckerUserRole : FtthCheckerUserRole.values()) {
					authorities.add(new SimpleGrantedAuthority(ftthCheckerUserRole.name()));
				}
			} else {
				authorities.add(new SimpleGrantedAuthority(ftthCheckerUser.getFtthUserRole().name()));
			}

			return new User(ftthCheckerUser.getUsername(), ftthCheckerUser.getPassword(), authorities);
		} else {
			Optional<FtthCustomer> ftthCustomerOptional = Optional
					.ofNullable(ftthCustomerRepository.findByUsername(username));
			FtthCustomer ftthCustomer = ftthCustomerOptional
					.orElseThrow(() -> new UsernameNotFoundException("User not found"));
			authorities.add(new SimpleGrantedAuthority(FtthCheckerUserRole.CUSTOMER.name()));
			return new User(ftthCustomer.getUsername(), ftthCustomer.getPassword(), authorities);
		}
	}
}
