package pe.edu.utp.i2.citasmedicas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import pe.edu.utp.i2.citasmedicas.service.impl.UsuarioDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	@Qualifier("usuarioDetailsService")
	private UsuarioDetailsService usuarioDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	/**
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	*/
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests().antMatchers("/js/**", "/css/**").permitAll()
		.antMatchers("/login*").permitAll()
		.antMatchers("/home/**").hasAnyAuthority("USER", "1")
		.antMatchers("/admin/**").hasAuthority("1")
		.and()
		.formLogin()
		.loginPage("/login")
		.defaultSuccessUrl("/home/", true)
		.permitAll()
		.and()
		.logout().permitAll();
	}
}
