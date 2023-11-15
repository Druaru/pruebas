package com.cursos.api.springsecuritycourse.config.security.filter;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cursos.api.springsecuritycourse.exception.ObjectNotFoundException;
import com.cursos.api.springsecuritycourse.persistence.repository.security.JwtTokenRepository;
import com.cursos.api.springsecuritycourse.persistence.security.JwtToken;
import com.cursos.api.springsecuritycourse.persistence.security.User;
import com.cursos.api.springsecuritycourse.service.UserService;
import com.cursos.api.springsecuritycourse.service.auth.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@Component
public class JwtAuthenticationFilter /*extends OncePerRequestFilter*/ {

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private JwtTokenRepository jwtRepository;

	@Autowired
	private UserService userService;

//	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 1. Obtener authorization header
		// 2. Obtener token
		String jwt = jwtService.extractJwtFromRequest(request);
		if (jwt == null || !StringUtils.hasText(jwt)) {
			filterChain.doFilter(request, response);
			return;
		}

		// 2.1. Obtener token no expirado y valido desde base de datos
		Optional<JwtToken> token = jwtRepository.findByToken(jwt);
		boolean isValid = validateToken(token);

		if (!isValid) {
			filterChain.doFilter(request, response);
			return;
		}

		// 3. Obtenemos el subject/username desde el token, a la vez valida el formato
		// del token, firma y fecha de expiración
		String username = jwtService.extractUsername(jwt);

		// 4. Setear el objeto authentication dentro del security context holder
		User user = userService.findOneByUsername(username)
				.orElseThrow(() -> new ObjectNotFoundException("User not found. Username " + username));
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null,
				user.getAuthorities());
		authToken.setDetails(new WebAuthenticationDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authToken);

		// 5. Ejecutar el resto de filtros

		filterChain.doFilter(request, response);

	}

	private boolean validateToken(Optional<JwtToken> optionalJwtToken) {
		if (!optionalJwtToken.isPresent()) {
			System.out.println("El token no fue generado en nuestro sistema o no existe.");
		}
		JwtToken token = optionalJwtToken.get();
		Date now = new Date(System.currentTimeMillis());

		boolean isValid = token.isValid() && token.getExpiration().after(now);
		
		if(!isValid) {
			System.out.println("Token inválido");
			updateTokenStatuts(token);
		}
		
		return isValid;

	}

	private void updateTokenStatuts(JwtToken token) {
		token.setValid(false);
		jwtRepository.save(token);
		
	}

}
