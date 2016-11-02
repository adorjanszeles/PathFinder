package pathfinder.ui.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

/**
 * A spring boot security konfigurációs állománya.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 01.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
            .userDetailsService(this.userDetailsService())
            .authorizeRequests()
            .antMatchers("/login/login.jsf").permitAll()
            .antMatchers("/path/*.jsf").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
            .antMatchers("/user/user_details.jsf").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
            .antMatchers("/templates/*.jsf").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
            .antMatchers("/city/*.jsf").access("hasRole('ROLE_ADMIN')")
            .antMatchers("/route/*.jsf").access("hasRole('ROLE_ADMIN')")
            .antMatchers("/vehicle/*.jsf").access("hasRole('ROLE_ADMIN')")
            .antMatchers("/user/registration.jsf").access("hasRole('ROLE_ADMIN')")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login/login.jsf")
            .permitAll()
            .failureUrl("/login/login.jsf?error=true")
            .defaultSuccessUrl("/user/user_details.jsf");
    }

    @Override
    protected UserDetailsService userDetailsService() {
        // TODO dbből kell lekérni a usereket...
        UserDetails user1 = new User("admin", "admin", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
        UserDetails user2 = new User("user", "user", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
        return new InMemoryUserDetailsManager(Arrays.asList(user1, user2));
    }

}
