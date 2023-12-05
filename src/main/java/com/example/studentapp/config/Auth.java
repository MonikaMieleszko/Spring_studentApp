package com.example.studentapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Arrays;

//te dwie adnotacje są koniecznie do spring security
@Configuration
@EnableWebSecurity
public class Auth {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
//tworzy managera użytkowników, które przechowują dane użytkowników w pamięci
    //UserDetails - podstawowy interface Spring Security reprezentujący informacje o udostępnionym użytkowniku
    @Bean
    public InMemoryUserDetailsManager get() {
        UserDetails user = User.withUsername("test")
                .password(passwordEncoder().encode("test"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(Arrays.asList(user, admin));
    }

    //niestandardowy łańcuch zabezpieczeń, będziemy sterować wszystkimi parametrami
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests() //rozpoczyna konfigurację autoryzacji
                //blok związany z linkami - adresacja
                .antMatchers("/students").hasAnyRole("USER") //dostęp do ścieżki students mają tylko użytkownicy z rolą User
                .antMatchers("/tasks").hasAnyRole("ADMIN")
                .antMatchers("/js/**", "/css/**","/vendor/**" ).permitAll() //wszyscy mają dostęp dp plików w katalogach JS, CSS, VENDOR
                .antMatchers("/").permitAll() //wszyscy mają dostęp do strony głównej
                //łączymy z kolejną konfiguracją - autoryzacja
                .and()
                //blok związany z obsługą
                .csrf().disable() // wyłącza ochronę przed atakami csrf - przydatne w testowaniu do postmana, żeby nie było, że jest logowanie przeciwnika
                .headers().frameOptions().disable() // wyłącza opcję ramek, nie pozwala osadzić strony w iframe -> na jednej stronie można wczytać drugą stronę -> to jest zablokowane
                .and()
                //poniżej przechodzimy do bloku konfiguracji corowej - logowanie
                .formLogin() //informuje go, że teraz będę konfigurował formularz autoryzacji
                .loginPage("/login") //wskazuje endpoint, w którym wyświetlam formularz logowania
                .usernameParameter("username") //nadaję nazwę jaka będzie, jako name w inpucie formularza (key dla inputu loginu)
                .passwordParameter("password") //nadaję nazwę jaka będzie jako name w inpucie hasła formularza (key dla inputu hasła)
                .failureForwardUrl("/login?error") //co się stanie w momencie wpisania błędnych danych
                .defaultSuccessUrl("/") //co się stanie w momencie prawidłowego wpisania danych
                .and()
                //zarządzanie wylogowaniem
                .logout() //mówimy springowi, że przechodzimgy do obsługi wylogowania
                .logoutSuccessUrl("/") //po wylogowaniu, gdzie ma nas przekierować
                .logoutUrl("/logout");
        return http.build();
    }
}
