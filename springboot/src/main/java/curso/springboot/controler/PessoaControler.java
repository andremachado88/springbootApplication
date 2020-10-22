package curso.springboot.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
public class PessoaControler {

	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public String inicio()
	{
		return "cadastro/cadastropessoa";
	}
	
}
