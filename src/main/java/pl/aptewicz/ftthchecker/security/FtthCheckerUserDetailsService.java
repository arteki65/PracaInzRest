package pl.aptewicz.ftthchecker.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.aptewicz.ftthchecker.domain.FtthCheckerUser;
import pl.aptewicz.ftthchecker.domain.FtthCheckerUserRole;
import pl.aptewicz.ftthchecker.repository.FtthCheckerUserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class FtthCheckerUserDetailsService implements UserDetailsService {

	private final FtthCheckerUserRepository ftthCheckerUserRepository;

	public FtthCheckerUserDetailsService(FtthCheckerUserRepository ftthCheckerUserRepository) {
		this.ftthCheckerUserRepository = ftthCheckerUserRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<FtthCheckerUser> ftthCheckerUserOptional = Optional
				.ofNullable(ftthCheckerUserRepository.findByUsername(username));

		FtthCheckerUser ftthCheckerUser = ftthCheckerUserOptional
				.orElseThrow(() -> new UsernameNotFoundException("User " + username + "not found."));

		Collection<GrantedAuthority> authorities = new ArrayList<>();
		if (FtthCheckerUserRole.ADMIN.equals(ftthCheckerUser.getFtthUserRole())) {
			for (FtthCheckerUserRole ftthCheckerUserRole : FtthCheckerUserRole.values()) {
				authorities.add(new SimpleGrantedAuthority(ftthCheckerUserRole.name()));
			}
		} else {
			authorities.add(new SimpleGrantedAuthority(ftthCheckerUser.getFtthUserRole().name()));
		}

		return new User(ftthCheckerUser.getUsername(), ftthCheckerUser.getPassword(), authorities);
	}
}
