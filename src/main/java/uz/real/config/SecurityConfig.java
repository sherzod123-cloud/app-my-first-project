package uz.real.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import uz.real.service.AuthenticationCheckService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled =true,
        securedEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationCheckService authenticationCheckService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationCheckService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(
                        "/",
                        "/auth/signup",
                        "/auth/signin",
                        "/about", "/history",
                        "/contacts", "/departments",
                        "/documents", "/course",
                        "/raspisaniya", "/photogalery",
                        "/videogalery", "/map",
                        "/news", "/teachers")
                .permitAll()
//                .mvcMatchers(HttpMethod.GET, "/admin/document").hasAuthority("document")
//                .mvcMatchers(HttpMethod.POST, "/admin/save/document").hasAuthority("saveDocument")
//                .mvcMatchers(HttpMethod.PUT, "/admin/edit/document/{id}").hasAuthority("editDocument")
//                .mvcMatchers(HttpMethod.GET, "/admin/users").hasAuthority("users")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().loginPage("/admin/login")
                .defaultSuccessUrl("/admin/home", true)
                .failureUrl("/admin/login?failed=true").permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID", "my-site-remember-me")
                .invalidateHttpSession(true).permitAll()
                .and()
                .rememberMe()
                .rememberMeParameter("remember-me")
                .rememberMeCookieName("my-site-remember-me")
                .tokenValiditySeconds(60 * 60 * 24 * 7)
                .and()
                .csrf()
                .disable();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
       web
               .ignoring().antMatchers("/assets/**");
    }
}
