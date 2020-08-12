package bg.verbo.footind.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import bg.verbo.footind.web.security.CustomAuthenticationProvider;
import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private CustomAuthenticationProvider customAuthenticationProvider;
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
       		.authenticationProvider(customAuthenticationProvider)
        	.eraseCredentials(false);
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	 http.csrf().disable();
         http.authorizeRequests().anyRequest().authenticated()
 	        .and()
 	        .formLogin()
 	        .and()
            .logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }
    
}
