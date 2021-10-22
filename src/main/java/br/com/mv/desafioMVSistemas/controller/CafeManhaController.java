package br.com.mv.desafioMVSistemas.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.mv.desafioMVSistemas.model.CafeDaManha;
import br.com.mv.desafioMVSistemas.model.Colaborador;
import br.com.mv.desafioMVSistemas.repository.ColaboradorRepository;
import br.com.mv.desafioMVSistemas.repository.ItemRepository;

@Controller @RequestMapping("/")
public class CafeManhaController {

	@Autowired
	ColaboradorRepository cr;
	@Autowired
	ItemRepository ir; 
	
	CafeDaManha cm = new CafeDaManha();
	
	@GetMapping("")
	public ModelAndView listar() {		
		
		ModelAndView mv = new ModelAndView("Index");
		mv.addObject("colaboradores", cr.findAll());
		mv.addObject("itens", ir.findAll());
		return mv;
	}
	
	@GetMapping("/selecionarColaborador/{id}")
	public ModelAndView selecionar(@PathVariable Long id, Model model) {
		
		ModelAndView mv = new ModelAndView("Index");
		Optional<Colaborador> c = cr.findById(id);
		
		
		return mv;
		
	}
}
