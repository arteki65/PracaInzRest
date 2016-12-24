package pl.aptewicz.ftthchecker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import pl.aptewicz.ftthchecker.repository.FtthCheckerUserRepository;
import pl.aptewicz.ftthchecker.security.FtthCheckerUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private FtthCheckerUserRepository ftthCheckerUserRepository;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().and().authorizeRequests().antMatchers("/maps").hasAuthority("ADMIN").and().httpBasic().and()
				.csrf().disable().authorizeRequests().antMatchers("/edge/*").hasAuthority("SERVICEMAN")
				.antMatchers("/user").hasAuthority("SERVICEMAN").antMatchers("/route").hasAuthority("SERVICEMAN")
				.antMatchers("/ftthJob").hasAuthority("SERVICEMAN").anyRequest().hasAuthority("ADMIN");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(new FtthCheckerUserDetailsService(ftthCheckerUserRepository))
				.passwordEncoder(new StandardPasswordEncoder("Wkn756!@"));
	}

	@Autowired
	public void setFtthCheckerUserRepository(FtthCheckerUserRepository ftthCheckerUserRepository) {
		this.ftthCheckerUserRepository = ftthCheckerUserRepository;
	}
}
