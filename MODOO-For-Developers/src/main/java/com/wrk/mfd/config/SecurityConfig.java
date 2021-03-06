package com.wrk.mfd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.wrk.mfd.service.CustomUserDetailService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private CustomUserDetailService customUserDetailService;
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		web.ignoring().antMatchers("/static/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http
			// csrf 허용
			// cors 허용
			.csrf().disable()
			.cors()
			
			.and()
			.authorizeRequests()
			.antMatchers("/api/**").permitAll()
			.antMatchers("/user/**").permitAll()
			.antMatchers("/auth/**").hasRole("UNAUTH")
			.antMatchers("/authCheck").authenticated()
			.antMatchers("/**").hasRole("AUTH")
			.and()
			.formLogin()
				.loginPage("/user/signin")
				.loginProcessingUrl("/user/signin/do")
				.defaultSuccessUrl("/authCheck")
				.successHandler(loginSuccessHandler)
				.permitAll()
			.and()
			.logout()
				.logoutUrl("/user/signout")
			.and()
				.exceptionHandling()
				.accessDeniedHandler(customAccessDeniedHandler);
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
	}
}
