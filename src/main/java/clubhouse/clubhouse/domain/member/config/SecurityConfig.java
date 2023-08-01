package clubhouse.clubhouse.domain.member.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import clubhouse.clubhouse.utils.JwtUtil;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Value("${jwt.secretKey}")
	private String secretkey;

	private final JwtUtil jwtUtil;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.httpBasic().disable()
			.formLogin().disable()
			.csrf().disable()
			.cors().and()
			.authorizeHttpRequests()
			.requestMatchers(
				new AntPathRequestMatcher("/v1/users/join"),
				new AntPathRequestMatcher("/v1/users/login"),
				new AntPathRequestMatcher("/v1/users/reissue"),
				PathRequest.toH2Console()).permitAll()
			.anyRequest().authenticated()
			.and()
			.headers().frameOptions().sameOrigin().and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilterBefore(new JwtFilter(secretkey, jwtUtil), UsernamePasswordAuthenticationFilter.class);

		return httpSecurity.getOrBuild();
	}
}
