package br.edu.ifpb.pweb2.emissordec.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class EmissorDecSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
        .antMatchers("/css/**", "/imagens/**")
        .permitAll()
        .antMatchers("/estudantes/form").hasRole("ADMIN")
        .antMatchers("/instituicoes/form").hasRole("ADMIN")
        .antMatchers("/periodos/form").hasRole("ADMIN")
        .antMatchers("/declaracoes/**").hasAnyRole("ADMIN", "CLIENTE")
        .anyRequest()
        .authenticated()
        .and()
        .formLogin((form) -> form
                .loginPage("/auth")
                .defaultSuccessUrl("/home", true)
                .permitAll())
        .logout(logout -> logout.logoutUrl("/auth/logout"));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(encoder);
                // .withUser(
                //     User.builder().username("user").password(encoder.encode("user1234")).roles("CLIENTE").build()
                // )
                // // .withUser(User.builder().username("sagan").password(encoder.encode("cosmos")).roles("CLIENTE").build()
                // // )
                // .withUser(User.builder().username("admin").password(encoder.encode("admin123")).roles("CLIENTE","ADMIN").build());
    }
}
