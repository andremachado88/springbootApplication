package curso.springboot.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import curso.springboot.model.Usuario;
import curso.springboot.repository.UsuarioRepository;
import curso.springboot.springboot.ApplicationContextLoaded;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Component
public class JWTTokenAutenticacaoService {

		/* Tempo de validade do Token  ex: 2 dias = 172800000 */
		private static final long EXPIRATION_TIME = 172800000;
		
		/* Uma senha unica para compor a autenticação*/
		private static final String SECRET = "SenhaExtremamenteSecreta";
		
		/* Prefixo padrão de Token*/
		private static final String TOKEN_PREFIX = "Bearer";
		
		private static final String HEADER_STRING = "Autorization";
		
		/* Gerando token de autenticação e adicionando ao cabeçalho e resposta HTTP*/
		public void addAuthentication(HttpServletResponse response, String userName) throws IOException
		{
			/* Montagem do token*/
			String jwt = Jwts.builder() /* Chama o gerador de Token*/
					.setSubject(userName)  /* Adiciona o usuário*/
					.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) /* Tempo de expiração */
					.signWith(SignatureAlgorithm.HS512, SECRET).compact(); /* Compactação e chama o algoritmo de geração de senha */
			
			/* Junta o token com o prefix*/
			String token = TOKEN_PREFIX + " "+jwt;
			
			/* Adiciona o cabeçalho HTTP*/
			response.addHeader(HEADER_STRING, token);
			
			/* Escreve token como resposta no corpo do HTTP */
			response.getWriter().write("{\"Autorization\": \""+ token +"\"}");
			
		}
		
		/* Retorna o usuário validado com token ou caso não seja válido retorna null*/
		public Authentication getAuthentication(HttpServletRequest request)
		{
			/* Pega o token enviado no cabeçalho HTTP*/
			
			String token = request.getHeader(HEADER_STRING);
			if (token !=null && token.length()>0)
			{
				/* Faz a validação do token do usuário na requisição*/
				String user = Jwts.parser().setSigningKey(SECRET) /* Pega o token completo */
						.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))  /* Faz a retirada do prefixo */
						.getBody().getSubject();  /* Pega o usuário logado*/
				
				if (user != null && user.length()>0)
				{
					Usuario usuario = ApplicationContextLoaded.getApplicationContext()
							.getBean(UsuarioRepository.class).findUserByLogin(user);
					
					/* Retornar o usuário logado*/
					if (usuario != null)
					{
						return new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha(), usuario.getAuthorities());
					}
				}
			}
			return null; /* Não autorizado*/
		}
		
	
}
