package wf.garnier.nativedemo.books;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@ImportRuntimeHints(SecurityRuntimeHints.class)
public class SecurityConfiguration {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//@formatter:off
		return http
				.authorizeHttpRequests(auth -> {
					auth.requestMatchers("/admin/**").authenticated();
					auth.anyRequest().permitAll();
				})
				.formLogin(Customizer.withDefaults())
				.logout(logout -> logout.logoutSuccessUrl("/books"))
				.build();
		//@formatter:on
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new InMemoryUserDetailsManager(
				User.withUsername("alice").password("{noop}password").roles("admin", "user").build(),
				User.withUsername("bob").password("{noop}password").roles("bob", "user").build());
	}

}
