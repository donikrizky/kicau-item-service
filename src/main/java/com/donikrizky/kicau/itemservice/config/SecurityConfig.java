package com.donikrizky.kicau.itemservice.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.donikrizky.kicau.itemservice.security.TokenAuthenticationFilter;
import com.donikrizky.kicau.itemservice.security.TokenProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private JwtConfig jwtConfig;

	@Autowired
	private TokenProvider tokenProvider;

	 private static final String[] AUTH_WHITELIST = {
	            "/swagger-resources/**",
	            "/swagger-ui.html",
	            "/v2/api-docs",
	            "/webjars/**",
	            "/register/**",
	            "/login/**"
	    };

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		  http
          .csrf().disable()
          .formLogin().disable()
          .httpBasic().disable()
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
          .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
          .and()
          .addFilterAfter(new TokenAuthenticationFilter(jwtConfig, tokenProvider), UsernamePasswordAuthenticationFilter.class)
          .authorizeRequests()
          .antMatchers(AUTH_WHITELIST).permitAll()
          .anyRequest().authenticated();
	}

}
