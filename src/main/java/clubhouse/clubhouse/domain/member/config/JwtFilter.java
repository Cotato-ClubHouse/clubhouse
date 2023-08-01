package clubhouse.clubhouse.domain.member.config;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import clubhouse.clubhouse.domain.member.service.MemberService;
import clubhouse.clubhouse.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

	private final String secretKey;

	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		log.info("authorization = {}", authorization);

		if (authorization == null || !authorization.startsWith("Bearer ")) {
			log.error("authorization을 잘못 보냈습니다.");
			filterChain.doFilter(request, response);
			return;
		}

		String token = authorization.split(" ")[1];

		if (jwtUtil.isExpired(token)) {
			log.error("Token이 만료 되었습니다.");
			filterChain.doFilter(request, response);
			return;
		}

		String email = jwtUtil.getEmail(token);
		log.info("email = {}", email);

		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(email, null, List.of(new SimpleGrantedAuthority("USER")));
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		filterChain.doFilter(request, response);
	}
}
