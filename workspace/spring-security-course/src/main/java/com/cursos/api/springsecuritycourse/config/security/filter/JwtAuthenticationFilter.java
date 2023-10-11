package com.cursos.api.springsecuritycourse.config.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cursos.api.springsecuritycourse.exception.ObjectNotFoundException;
import com.cursos.api.springsecuritycourse.persistence.entity.User;
import com.cursos.api.springsecuritycourse.service.UserService;
import com.cursos.api.springsecuritycourse.service.auth.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		System.out.println("Entró en el filtro JWT AUTHENTICATION FILTER");

		// 1. Obtener encabezado http llamado Authorization (va a tener bearer y el
		// token)
		String authorizationHeader = request.getHeader("Authorization"); // Bearer jwt

		if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		// 2. Desde ese header obtenemos el JWT desde el encabezado.
		String jwt = authorizationHeader.split(" ")[1];

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

}
