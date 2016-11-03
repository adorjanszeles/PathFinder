package pathfinder.ui.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import pathfinder.services.UserService;

/**
 * A spring boot security konfigurációs állománya.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 01.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
            .userDetailsService(this.userDetailsService())
            .authorizeRequests()
            .antMatchers("/login/login.jsf").permitAll()
            .antMatchers("/path/*.jsf").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
            .antMatchers("/user/user_details.jsf").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
            .antMatchers("/vehicle/*.jsf").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
            .antMatchers("/templates/*.jsf").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
            .antMatchers("/city/*.jsf").access("hasRole('ROLE_ADMIN')")
            .antMatchers("/route/*.jsf").access("hasRole('ROLE_ADMIN')")
            .antMatchers("/user/registration.jsf").access("hasRole('ROLE_ADMIN')")
            .antMatchers("/user/user_search.jsf").access("hasRole('ROLE_ADMIN')")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login/login.jsf")
            .permitAll()
            .failureUrl("/login/login.jsf?error=true")
            .defaultSuccessUrl("/user/user_details.jsf");
    }

//    @Override
//    protected UserDetailsService userDetailsService() {
//        List<UserDetails> userDetailsList = new ArrayList<>();
//        userDetailsList.add(new User("admin", "admin", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN")));
//        List<pathfinder.model.nodes.User> allUser = userService.getAllUser();
//        for (pathfinder.model.nodes.User userElement : allUser) {
//            String role = RoleEnum.USER.equals(userElement.getRole()) ? "ROLE_USER" : "ROLE_ADMIN";
//            UserDetails userDetails = new User(userElement.getName(), userElement.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(role));
//            userDetailsList.add(userDetails);
//        }
//        return new InMemoryUserDetailsManager(userDetailsList);
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new PathFinderUserDetailsServiceImpl(userService));
    }

}
