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
                .userDetailsService(userDetailsService())
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login/login.jsf").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login/login.jsf")
                .permitAll()
                .failureUrl("/login/login.jsf?error=true")
                .defaultSuccessUrl("/route/route_search.jsf")
                .and()
                .logout()
                .logoutSuccessUrl("/login/login.jsf");
    }

    @Override
    protected UserDetailsService userDetailsService() {
        UserDetails user1 = new User("persapiens", "123", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
        UserDetails user2 = new User("nyilmaz", "qwe", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
        return new InMemoryUserDetailsManager(Arrays.asList(user1, user2));
    }

}
