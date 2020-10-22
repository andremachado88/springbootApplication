package curso.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import curso.springboot.service.ImplementacaoUserDetailsService;

/** Mapeia URL, endereços, autoriza ou bloqueia acessos a URL */
@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;
	
	@Override
		protected void configure(HttpSecurity http) throws Exception {
			// TODO Auto-generated method stub
		
			/* Ativando a proteção contra usuários que não estão validados por TOKEN*/
			http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			
			/* Ativando a permissão para o acesso a pagina inicial do sistema Ex: sistema.com.br/index.html */
			.disable().authorizeRequests().antMatchers("/").permitAll()
			.antMatchers("/index").permitAll().
			
			/* URL de logout - Redireciona após o user deslogar do sistema*/
			anyRequest().authenticated().and().logout().logoutSuccessUrl("/index").
			
			
			/* Mapeai URL de logout e invalida o usuário */
			logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			
			/* Filtra requisição de login para autenticação*/
			.and().addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), 
					               UsernamePasswordAuthenticationFilter.class)
			
			/* Filtra demais requisições para verificar a presença do TOKEN JWT no header HTTP*/
			.addFilterBefore(new JwtApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);
			
		}
	
	@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			// TODO Auto-generated method stub
		
		/** Service que irá consultar o usuário no banco de dados */
		auth.userDetailsService(implementacaoUserDetailsService)
		
		/** Padrão de codificação de senha */
		.passwordEncoder(new BCryptPasswordEncoder());
		//			super.configure(auth);
		}

}
