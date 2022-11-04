package be.abis.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	@Autowired BCryptPasswordEncoder encoder;
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		 auth.inMemoryAuthentication()
		     .withUser("abis01").password(encoder.encode("abis01")).roles("AbisUser").and()
		     .withUser("abis02").password(encoder.encode("abis02")).roles("AbisUser","AbisAdmin");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception{

		http
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
					.antMatchers("/persons/login").permitAll()
				    .antMatchers(HttpMethod.PUT).permitAll()
				    .antMatchers(HttpMethod.PATCH).permitAll()
				    .antMatchers("/persons/**").authenticated()
				.and()
				.httpBasic()
				.realmName("Guests")
				.and()
				.csrf()
				.disable();
	}
}
