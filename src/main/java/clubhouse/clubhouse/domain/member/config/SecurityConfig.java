package clubhouse.clubhouse.domain.member.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
			httpSecurity
				.httpBasic().disable()
				.cors().disable()
				.csrf((csrf) -> csrf
					.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
					.ignoringRequestMatchers(new AntPathRequestMatcher("/v1/users/**"))
				)
				.authorizeHttpRequests( authorizeHttpRequests -> authorizeHttpRequests
					.requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
					.requestMatchers(new AntPathRequestMatcher("/v1/users/**")).permitAll()
				)
				.headers().frameOptions().sameOrigin().and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

			return httpSecurity.getOrBuild();

		/* httpSecurity
			.httpBasic().disable()
			.csrf().disable()
			.cors().and()
			.authorizeHttpRequests()
			.requestMatchers("/v1/users/join").permitAll()
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.getOrBuild(); */

	}
}
