package curso.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import curso.springboot.model.Usuario;
import curso.springboot.repository.UsuarioRepository;

@Service
public class ImplementacaoUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		/** Consultar no banco o usuario*/
		Usuario usuario = usuarioRepository.findUserByLogin(username);
		
		if (usuario == null)
		{
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		return new User(usuario.getLogin(), usuario.getPassword(), usuario.getAuthorities());
		
//		return null;
	}

}
