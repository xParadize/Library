package com.example.Project2Boot.config;

import com.example.Project2Boot.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    // Конфиг
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/auth/login", "/auth/registration", "/error").permitAll() // На эти страницы все имеют доступ
                .antMatchers("/auth/redirection").hasRole("CUSTOMER") // Если у пользователя есть роль CUSTOMER, перенаправляем на адрес /auth/registration
                .anyRequest().hasRole("ADMIN") // На все другие страницы доступ разрешён только админам
                .and()  // Разделитель настроек
                .formLogin().loginPage("/auth/login")   // Страница со входом
                .loginProcessingUrl("/process_login")   // По этому адресу Spring будет ждать данные из формы входа
                .defaultSuccessUrl("/books", true)  // Редирект в случае успешной авторизации пользователя с ролью ADMIN
                .failureUrl("/auth/login?error")   // Редирект в случае неуспешной авторизации + передаём параметр в представление для вывода ошибки пользователю
                .and()  // Разделитель настроек
                .logout()
                .logoutUrl("/logout")  // Редирект в случае выхода - удаляются все cookie-файлы пользователя
                .logoutSuccessUrl("/auth/login") // Редирект на страницу в случае успешного выхода
                .and()
                .exceptionHandling().accessDeniedPage("/errors/error_403"); // для ошибки 403
        return http.build();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService).passwordEncoder(getPasswordEncoder());
    }

    // Шифрование паролей
    @Bean
    public PasswordEncoder getPasswordEncoder() {
            return new BCryptPasswordEncoder();
    }
}
