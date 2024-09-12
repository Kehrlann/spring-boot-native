package wf.garnier.nativedemo.examples;

import org.springframework.aot.hint.annotation.Reflective;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.StringUtils;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.formLogin(Customizer.withDefaults())
			.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
			.build();
	}

	@Bean
	public AuthorizationService authorizationService() {
		return new AuthorizationService();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		var builder = User.withDefaultPasswordEncoder().password("password").roles("USER");
		return new InMemoryUserDetailsManager(builder.username("alice").build(), builder.username("bob").build());
	}

	static class AuthorizationService {

		@Reflective
		public boolean isAllowed(Authentication authentication) {
			System.out.println(authentication);
			if (authentication == null) {
				return false;
			}
			var name = authentication.getName();
			return StringUtils.hasLength(name) && name.toLowerCase().startsWith("a");
		}

	}

}
