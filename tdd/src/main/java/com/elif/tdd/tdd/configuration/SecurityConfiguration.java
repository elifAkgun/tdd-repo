package com.elif.tdd.tdd.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	
	@Autowired
	AuthUserService authUserService;
	
	//This method provides http security object which is the configuration object for spring security.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); 
		
		http.httpBasic().authenticationEntryPoint(new BasicAuthenticationEntryPoint());
		//this order is very important because if you change that order all 
		http
			.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/v1/api/login").authenticated()
				.and()
				.authorizeRequests().anyRequest().permitAll();
		
		//we will not using session our project so we added this line
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); 
		http.headers().frameOptions().disable();

	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authUserService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
