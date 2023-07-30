package clubhouse.clubhouse.domain.member.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.PostMapping;

import clubhouse.clubhouse.domain.member.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final MemberService memberService;

	@Value("${jwt.secretKey}")
	private String secretkey;

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
					PathRequest.toH2Console()).permitAll()
				.anyRequest().authenticated()
				.and()
				.headers().frameOptions().sameOrigin().and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilterBefore(new JwtFilter(memberService, secretkey), UsernamePasswordAuthenticationFilter.class);

			return httpSecurity.getOrBuild();

			/*.csrf((csrf) -> csrf
					.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
					.ignoringRequestMatchers(new AntPathRequestMatcher("/v1/users/**"))
				)
				.authorizeHttpRequests( authorizeHttpRequests -> authorizeHttpRequests
					.requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
					.requestMatchers(new AntPathRequestMatcher("/v1/users/**")).permitAll()
				)*/

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
