package pl.aptewicz.ftthchecker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import pl.aptewicz.ftthchecker.repository.FtthCheckerUserRepository;
import pl.aptewicz.ftthchecker.repository.FtthCustomerRepository;
import pl.aptewicz.ftthchecker.security.FtthCheckerUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private FtthCheckerUserRepository ftthCheckerUserRepository;

	private FtthCustomerRepository ftthCustomerRepository;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().defaultSuccessUrl("/maps", true).and().authorizeRequests().antMatchers("/maps")
				.hasAuthority("ADMIN").and().httpBasic().and().csrf().disable().authorizeRequests()
				.antMatchers("/edge/*").hasAuthority("SERVICEMAN").antMatchers("/user").hasAuthority("SERVICEMAN")
				.antMatchers("/route").hasAuthority("SERVICEMAN").antMatchers("/ftthJob").hasAuthority("SERVICEMAN")
				.antMatchers("/ftthCustomer").hasAuthority("CUSTOMER").antMatchers("/ftthIssue")
				.hasAnyAuthority("CUSTOMER", "ADMIN").antMatchers("/path/**").hasAuthority("ADMIN")
				.antMatchers("/hierarchy/**").hasAuthority("ADMIN").anyRequest().hasAuthority("ADMIN");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(new FtthCheckerUserDetailsService(ftthCheckerUserRepository, ftthCustomerRepository))
				.passwordEncoder(new StandardPasswordEncoder("Wkn756!@"));
	}

	@Autowired
	public void setFtthCheckerUserRepository(FtthCheckerUserRepository ftthCheckerUserRepository) {
		this.ftthCheckerUserRepository = ftthCheckerUserRepository;
	}

	@Autowired
	public void setFtthCustomerRepository(FtthCustomerRepository ftthCustomerRepository) {
		this.ftthCustomerRepository = ftthCustomerRepository;
	}
}
