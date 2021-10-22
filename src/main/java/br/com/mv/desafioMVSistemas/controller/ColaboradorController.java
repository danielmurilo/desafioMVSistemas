package br.com.mv.desafioMVSistemas.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.mv.desafioMVSistemas.model.Colaborador;
import br.com.mv.desafioMVSistemas.repository.ColaboradorRepository;

@Controller @RequestMapping("/colaboradores")
public class ColaboradorController {
	
	@Autowired
	ColaboradorRepository cr;
	
	@GetMapping("")
	public ModelAndView listar(Colaborador c) {		
		
		ModelAndView mv = new ModelAndView("CadastroColaboradores");
		mv.addObject("colaborador", new Colaborador());
		mv.addObject("colaboradores", cr.findAll());
		return mv;
		
	}
	
	@GetMapping("/selecionar/{id}")
	public ModelAndView selecionar(@PathVariable Long id, Model model) {
		
		ModelAndView mv = new ModelAndView("CadastroColaboradores");
		mv.addObject("colaborador", cr.findById(id).orElseThrow(() -> new IllegalArgumentException("Id inválido")));
		mv.addObject("colaboradores", cr.findAll());
		return mv;
		
	}
	
	@PostMapping("")
	public ModelAndView salvar(@Valid Colaborador colaborador, BindingResult result, RedirectAttributes attributes) {
		
		//formatando CPF para formato 'xxx.xxx.xxx-xx
		if(colaborador.getCpf().trim().length() == 11) {
			String aux = colaborador.getCpf().trim();
			String bloco1 = aux.substring(0, 3);
			String bloco2 = aux.substring(3, 6);
			String bloco3 = aux.substring(6, 9);
			String bloco4 = aux.substring(9, 11);
			aux = bloco1+"."+ bloco2+"." + bloco3+"-" + bloco4;
			colaborador.setCpf(aux);
		}
		
		if (result.hasErrors()) {
			return listar(colaborador);
		} else if(colaborador.equals(cr.findByCpf(colaborador.getCpf())) && colaborador.getId() == null) {
			attributes.addFlashAttribute("mensagemSalvo", "Já existe colaborador com este CPF");
			
		} else {
			cr.save(colaborador);
			attributes.addFlashAttribute("mensagemSalvo", "colaborador salvo com sucesso.");
		}
		
		ModelAndView mv = new ModelAndView("redirect:/colaboradores");		
		return mv;
	}	
	
	@GetMapping("/deletar/{id}")
	public ModelAndView deletar(@PathVariable Long id) {
		
		cr.deleteById(id);
		ModelAndView mv = new ModelAndView("redirect:/colaboradores");
		return mv;
			
	}
	
	@GetMapping("/procurar")
	public ModelAndView procurar(HttpServletRequest request, Model model) {
		
		ModelAndView mv = new ModelAndView("CadastroColaboradores");
		model.addAttribute("colaborador", new Colaborador()); 
		mv.addObject("colaboradores", cr.findByNomeContainingIgnoreCase(request.getParameter("busca")));
		return mv;
		
	}

}
