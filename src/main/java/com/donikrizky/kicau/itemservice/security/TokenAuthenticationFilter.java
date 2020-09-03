package com.donikrizky.kicau.itemservice.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.donikrizky.kicau.itemservice.config.JwtConfig;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
	private final JwtConfig jwtConfig;
	private final TokenProvider tokenProvider;

	public TokenAuthenticationFilter(JwtConfig jwtConfig, TokenProvider tokenProvider) {
		this.jwtConfig = jwtConfig;
		this.tokenProvider = tokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String header = request.getHeader(jwtConfig.getHeader());

		if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
			chain.doFilter(request, response);
			return;
		}

		String token = header.replace(jwtConfig.getPrefix(), "");

		try {
			Long userId = tokenProvider.getUserIdFromToken(token);

			Date logoutTime = tokenProvider.getLogoutTimeFromToken(token);
			if (tokenProvider.validateToken(token, logoutTime)) {

				List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userId, null,
						authorities);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}

		} catch (Exception e) {
			SecurityContextHolder.clearContext();
		}

		chain.doFilter(request, response);
	}
}
