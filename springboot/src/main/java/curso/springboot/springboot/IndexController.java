package curso.springboot.springboot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import curso.springboot.model.Usuario;
import curso.springboot.repository.UsuarioRepository;

// use @CrossOrigin with argument asterisk to allow all origin to call with jquery all end-Points
//@CrossOrigin(origins = "*")
//@Controller
@RestController /* Arquitetura REST*/
@RequestMapping(value = "/usuario")
public class IndexController {
	
//	@RequestMapping("/")
//	public String index() {
//	 return "index";	
//	}
	
	@Autowired  /* se fosse CDI seria @Inject*/
	private UsuarioRepository usuarioRepository;
	
	
	@GetMapping(value = "/{id}/relatoriopdf", produces = "application/json")
	public ResponseEntity<Usuario> relatorio(@PathVariable (value = "id") Long id)
	{
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		
		return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
	}
	
	/*Service REST*/
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Usuario> init(@PathVariable (value = "id") Long id)
	{
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		
		return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
		
	}
	
	@GetMapping (value = "/", produces = "application/json")
	public ResponseEntity<List<Usuario>> usuario()
	{
		List<Usuario> list = (List<Usuario>) usuarioRepository.findAll();
		
		return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
		
	}
	
	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario)
	{
		for (int i = 0; i < usuario.getTelefones().size(); i++)
		{
			usuario.getTelefones().get(i).setUsuario(usuario);
		}
		
		Usuario savedUser = usuarioRepository.save(usuario);
		
		return new ResponseEntity<Usuario>(savedUser, HttpStatus.OK);
	}
	
	@PostMapping(value = "/vendausuario", produces = "application/json")
	public ResponseEntity<Usuario> cadrastrarVenda(@RequestBody Usuario usuario)
	{
		/** 
		 * Here wil be selling user code rules
		 */
		Usuario savedUser = usuarioRepository.save(usuario);
		
		return new ResponseEntity<Usuario>(savedUser, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping(value = "/{idUser}/idvenda/{idSell}", produces = "application/json")
	public ResponseEntity cadastrarVenda(@PathVariable Long idUser, @PathVariable Long idSell)
	{
		return new ResponseEntity("id User: "+ idUser + " - Id Venda: "+ idSell, HttpStatus.OK);
	}
	
	@PutMapping(value = "/", produces = "application/json")
	public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario)
	{
		for (int i = 0; i < usuario.getTelefones().size(); i++)
		{
			usuario.getTelefones().get(i).setUsuario(usuario);
		}
		
		Usuario savedUser = usuarioRepository.save(usuario);
		
		return new ResponseEntity<Usuario>(savedUser, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping(value = "{id}", produces = "application/text")
	public ResponseEntity delete(@PathVariable (value = "id") Long id)
	{
		usuarioRepository.deleteById(id);
		
		return new ResponseEntity("OK", HttpStatus.OK);
	}
	
	

}
